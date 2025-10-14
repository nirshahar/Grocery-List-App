package com.example.grocerylist.screens.checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.toggleable
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
import com.example.grocerylist.ui.data.Product

@Composable
fun CheckoutProductRows(
    items: List<Product>,
    modifier: Modifier = Modifier,
    onItemCheck: (idx: Int, item: Product, isChecked: Boolean) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(items) { idx, item ->
            CheckoutProductRow(item) { isChecked ->
                onItemCheck(idx, item, isChecked)
            }
        }
    }
}

@Composable
fun CheckoutProductRow(item: Product, modifier: Modifier = Modifier, onCheck: (Boolean) -> Unit) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .toggleable(
                    value = item.isSelected, role = Role.Checkbox
                ) {
                    onCheck(!item.isSelected)
                }
                .padding(8.dp)) {
            Checkbox(
                item.isSelected,
                onCheckedChange = null,
                modifier = Modifier.size(32.dp),
            )
            Column(
                modifier = Modifier.defaultMinSize(minHeight = 32.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    item.name,
                    textDecoration = if (item.isSelected) TextDecoration.LineThrough else TextDecoration.None,
                    style = MaterialTheme.typography.titleMedium
                )
                if (item.description.isNotBlank()) {
                    Text(
                        item.description,
                        textDecoration = if (item.isSelected) TextDecoration.LineThrough else TextDecoration.None,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
}

@Composable
@Preview(showBackground = true)
fun CheckoutRowPreview() {
    CheckoutProductRows(
        items = listOf(
            Product("Banana", "Yellowish ripe"),
            Product("Banana", "Yellowish ripe", isSelected = true),
            Product("Banana", "", isSelected = true),
        ), onItemCheck = { _, _, _ -> }
    )
}
