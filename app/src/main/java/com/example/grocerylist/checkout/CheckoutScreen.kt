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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CheckoutScreenHolder(
    modifier: Modifier = Modifier,
    viewModel: CheckoutViewModel = viewModel(),
) {
    val items by viewModel.items.collectAsState()

    CheckoutScreen(
        items = items,
        onItemCheck = { idx, item, isChecked ->
            // TODO - use ID instead of idx
            viewModel.checkItemByIdx(idx, isChecked)
        },
        modifier = modifier,
    )
}

@Composable
fun CheckoutScreen(
    items: List<CheckoutItem>,
    onItemCheck: (Int, CheckoutItem, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val progress = if (items.isNotEmpty()) {
                items.count { it.isChecked }.toFloat() / items.size.toFloat()
            } else {
                1f
            }

            CheckoutProgressCircular(progress)
            CheckoutItems(items, onItemCheck = onItemCheck)
        }
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
    )
}

@Composable
fun CheckoutAddItemFab(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(Icons.Default.Add, "Add a new item")
    }
}
