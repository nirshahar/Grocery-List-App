package com.example.grocerylist.checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CheckoutScreen(items: List<CheckoutItem>, modifier: Modifier = Modifier) {
    Surface(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items.size) { idx ->
                CheckoutRow(items[idx])
            }
        }
    }
}


@Composable
fun CheckoutRow(item: CheckoutItem, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Checkbox(false, onCheckedChange = null)
        Column {
            Text(item.itemName, style = MaterialTheme.typography.titleMedium)
            Text(item.subtext, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
@Preview
fun CheckoutScreenPreview() {
    CheckoutScreen(listOf(
        CheckoutItem("Banana", "Yellowish ripe"),
        CheckoutItem("Chocolate", "Tnoova brand"),
        CheckoutItem("Meat", "Without skin"),
    ))
}

@Composable
@Preview
fun CheckoutRowPreview() {
    Surface {
        CheckoutRow(
        CheckoutItem("Banana", "Yellowish ripe"),
        )
    }
}