package com.example.grocerylist.checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastCoerceIn
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CheckoutScreenHolder(
    modifier: Modifier = Modifier, viewModel: CheckoutViewModel = viewModel()
) {
    val items by viewModel.items.collectAsState()

    LaunchedEffect(viewModel) {
        // TEMPORARY TODO
        viewModel.addItem(CheckoutItem("Banana", "Yellowish ripe"))
        viewModel.addItem(CheckoutItem("Chocolate", "Tnoova brand"))
        viewModel.addItem(CheckoutItem("Meat", "Without skin"))
    }

    CheckoutScreen(items, modifier) { idx, item, isChecked ->
        viewModel.checkItem(idx, isChecked)
    }
}

@Composable
fun CheckoutScreen(
    items: List<CheckoutItem>,
    modifier: Modifier = Modifier,
    onItemCheck: (Int, CheckoutItem, Boolean) -> Unit
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val progress = if (items.isNotEmpty()) {
                items.count { it.isChecked }.toFloat() / items.size.toFloat()
            } else {
                1f
            }.fastCoerceIn(0f, 1f)

            CheckoutProgress(progress)
            CheckoutItems(items, onItemCheck = onItemCheck)
        }
    }
}


@Composable
@Preview
fun CheckoutScreenPreview() {
    CheckoutScreen(
        listOf(
            CheckoutItem("Banana", "Yellowish ripe"),
            CheckoutItem("Chocolate", "Tnoova brand"),
            CheckoutItem("Meat", "Without skin"),
        ),
    ) { _, _, _ -> }
}

@Composable
fun CheckoutAddItemFab(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(Icons.Filled.Add, "Add a new item")
    }
}
