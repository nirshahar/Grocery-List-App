package com.example.grocerylist.screens.checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CheckoutItems(
    items: List<CheckoutItem>,
    modifier: Modifier = Modifier,
    onItemCheck: (idx: Int, item: CheckoutItem, isChecked: Boolean) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(items.size) { idx ->
            CheckoutRow(items[idx]) { isChecked ->
                onItemCheck(idx, items[idx], isChecked)
            }
        }
    }
}

@Composable
fun CheckoutRow(item: CheckoutItem, modifier: Modifier = Modifier, onCheck: (Boolean) -> Unit) {
    Card(
        modifier = modifier
            .padding(4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .toggleable(
                    value = item.isChecked,
                    role = Role.Checkbox
                ) {
                    onCheck(!item.isChecked)
                }
                .padding(4.dp)
        ) {
            Checkbox(item.isChecked, onCheckedChange = null)
            Column(
                modifier = Modifier.defaultMinSize(minHeight = 32.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    item.name,
                    textDecoration = if (item.isChecked) TextDecoration.LineThrough else TextDecoration.None,
                    style = MaterialTheme.typography.titleMedium
                )
                if (item.description.isNotBlank()) {
                    Text(
                        item.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CheckoutRowPreview() {
    Column {
        CheckoutRow(
            CheckoutItem("Banana", "Yellowish ripe"),
        ) { }
        CheckoutRow(
            CheckoutItem("Banana", "Yellowish ripe", isChecked = true),
        ) { }
        CheckoutRow(
            CheckoutItem("Banana", "", isChecked = true),
        ) { }
    }
}
