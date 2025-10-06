package com.example.grocerylist.checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.grocerylist.AddItemBottomSheet
import com.example.grocerylist.FabActionSetter
import com.example.grocerylist.KonfettiPresets
import nl.dionsegijn.konfetti.compose.KonfettiView
import org.koin.androidx.compose.koinViewModel

@Composable
fun CheckoutScreenHolder(
    setFabAction: FabActionSetter,
    modifier: Modifier = Modifier,
    viewModel: CheckoutViewModel = koinViewModel(),
) {
    val items by viewModel.items.collectAsState()

    CheckoutScreen(
        items = items,
        onItemCheck = { idx, item, isChecked ->
            // TODO - use ID instead of idx
            viewModel.checkItemByIdx(idx, isChecked)
        },
        onAddItem = viewModel::addItem,
        setFabAction,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    items: List<CheckoutItem>,
    onItemCheck: (Int, CheckoutItem, Boolean) -> Unit,
    onAddItem: (CheckoutItem) -> Unit,
    setFabAction: FabActionSetter,
    modifier: Modifier = Modifier,
) {
    var showAddItemBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val bottomSheetScope = rememberCoroutineScope()

    LaunchedEffect(true) {
        setFabAction {
            showAddItemBottomSheet = true
        }
    }

    Surface(modifier = modifier.fillMaxSize()) {
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

    if (showAddItemBottomSheet) {
        AddItemBottomSheet(
            scope = bottomSheetScope,
            sheetState = sheetState,
            onDismiss = { showAddItemBottomSheet = false },
            onSubmit = onAddItem
        )
    }
}

@Composable
@Preview
fun CheckoutScreenPreview() {
    CheckoutScreen(
        items = listOf(
            CheckoutItem("Banana", "Yellowish ripe"),
            CheckoutItem("Chocolate", "Tnoova brand"),
            CheckoutItem("Meat", "Without skin"),
        ),
        onItemCheck =  { _, _, _ -> },
        onAddItem = {},
        setFabAction = {}
    )
}

@Composable
fun CheckoutAddItemFab(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(Icons.Default.Add, "Add a new item")
    }
}
