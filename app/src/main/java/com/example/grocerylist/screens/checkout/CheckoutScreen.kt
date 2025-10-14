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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.grocerylist.KonfettiPresets
import com.example.grocerylist.Load
import com.example.grocerylist.LoadingState
import com.example.grocerylist.R
import com.example.grocerylist.screens.settings.checkout.CheckoutProgressCircular
import com.example.grocerylist.ui.data.Product
import nl.dionsegijn.konfetti.compose.KonfettiView
import org.koin.androidx.compose.koinViewModel

@Composable
fun CheckoutScreenHolder(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: CheckoutViewModel = koinViewModel(),
) {
    val loadingItems by viewModel.items.collectAsStateWithLifecycle()

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
    loadingItems: LoadingState<List<Product>>,
    onItemCheck: (Int, Product, Boolean) -> Unit,
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
                    items.count { it.isSelected }.toFloat() / items.size.toFloat()
                } else {
                    0f
                }

                CheckoutProgressCircular(progress)
                CheckoutProductRows(items, onItemCheck = onItemCheck)
            }

            // Show some nice confetti when completing the checkout list :)
            if (items.isNotEmpty() && items.all { it.isSelected }) {
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
                Text(stringResource(R.string.exit_checkout_dialog_leave_button))
            }
        },
        dismissButton = {
            TextButton(onDismiss) {
                Text(stringResource(R.string.exit_checkout_dialog_cancel_button))
            }
        },
        title = { Text(stringResource(R.string.exit_checkout_dialog_title)) },
        text = { Text(stringResource(R.string.exit_checkout_dialog_content)) },
    )
}

@Composable
@Preview
fun CheckoutScreenPreview() {
    val navController = rememberNavController()
    CheckoutScreen(
        loadingItems = LoadingState.Finished(
            listOf(
                Product("Banana", "Yellowish ripe"),
                Product("Chocolate", "Tnoova brand"),
                Product("Meat", "Without skin"),
            )
        ), onItemCheck = { _, _, _ -> }, navController = navController
    )
}

@Preview
@Composable
private fun CheckoutScreenExitDialogPreview() {
    CheckoutScreenExitDialog({}, {})
}