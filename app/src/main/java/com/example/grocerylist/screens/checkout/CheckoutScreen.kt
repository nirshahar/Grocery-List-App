package com.example.grocerylist.screens.checkout

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.grocerylist.KonfettiPresets
import com.example.grocerylist.Load
import com.example.grocerylist.LoadingState
import com.example.grocerylist.screens.settings.checkout.CheckoutProgressCircular
import nl.dionsegijn.konfetti.compose.KonfettiView
import org.koin.androidx.compose.koinViewModel

@Composable
fun CheckoutScreenHolder(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: CheckoutViewModel = koinViewModel(),
) {
    val loadingItems by viewModel.items.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.loadItems()
    }

    CheckoutScreen(
        loadingItems = loadingItems,
        onItemCheck = { idx, item, isChecked ->
            // TODO - use ID instead of idx
            viewModel.checkItemByIdx(idx, isChecked)
        },
        navController = navController,
        modifier = modifier,
    )
}

@Composable
fun CheckoutScreen(
    loadingItems: LoadingState<List<CheckoutItem>>,
    onItemCheck: (Int, CheckoutItem, Boolean) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Load(
            loadingItems,
            loadingContent = { Text("Loading :)") },
        ) { items ->
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val progress = if (items.isNotEmpty()) {
                    items.count { it.isChecked }.toFloat() / items.size.toFloat()
                } else {
                    0f
                }

                CheckoutProgressCircular(progress)
                CheckoutItems(items, onItemCheck = onItemCheck)
            }

            // Show some nice confetti when completing the checkout list :)
            if (items.isNotEmpty() && items.all { it.isChecked }) {
                KonfettiView(
                    parties = KonfettiPresets.festive()
                )
            }
        }
    }

    CheckoutScreenBackHandler(navController)
}

@Composable
fun CheckoutScreenBackHandler(navController: NavController) {
    var exitDialogShown by remember { mutableStateOf(false) }

    BackHandler {
        exitDialogShown = true
    }

    if (exitDialogShown) {
        CheckoutScreenExitDialog(
            onDismiss = {
                exitDialogShown = false
            },
            onConfirm = {
                exitDialogShown = false
                navController.popBackStack()
            }
        )
    }
}

@Composable
fun CheckoutScreenExitDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Leave")
            }
        },
        dismissButton = {
            TextButton(onDismiss) {
                Text("Cancel")
            }
        },
        title = { Text("Leave checkout?") },
        text = { Text("Changes you made will not be saved.") },
    )
}

@Composable
@Preview
fun CheckoutScreenPreview() {
    val navController = rememberNavController()
    CheckoutScreen(
        loadingItems = LoadingState.Finished(
            listOf(
                CheckoutItem("Banana", "Yellowish ripe"),
                CheckoutItem("Chocolate", "Tnoova brand"),
                CheckoutItem("Meat", "Without skin"),
            )
        ), onItemCheck = { _, _, _ -> }, navController = navController
    )
}

@Preview
@Composable
private fun CheckoutScreenExitDialogPreview() {
    CheckoutScreenExitDialog({}, {})
}