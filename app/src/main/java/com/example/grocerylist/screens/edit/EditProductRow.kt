package com.example.grocerylist.screens.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DragHandle
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.grocerylist.ui.data.Product

@Composable
fun EditProductRows(
    items: List<Product>,
    modifier: Modifier = Modifier,
    selectItem: (idx: Int, item: Product, isChecked: Boolean) -> Unit
) {
    val isSelectionActive = items.any { it.isSelected }

    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(items) { idx, item ->
            EditProductRow(item, isSelectionActive) { isSelected ->
                selectItem(idx, item, isSelected)
            }
        }
    }
}

@Composable
fun EditProductRow(
    item: Product,
    isSelectionActive: Boolean,
    modifier: Modifier = Modifier,
    onSelect: (Boolean) -> Unit
) {
    val isSelectionActive = isSelectionActive || item.isSelected

    Card(
        modifier = modifier.padding(4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .toggleable(
                    value = item.isSelected, role = Role.Checkbox
                ) {
                    onSelect(!item.isSelected)
                }
                .padding(4.dp)) {

            if (isSelectionActive) {
                Checkbox(item.isSelected, onCheckedChange = null)
            } else {
                Icon(Icons.Default.DragHandle, null)
            }

            Column(
                modifier = Modifier.defaultMinSize(minHeight = 32.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    item.name,
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

@Preview
@Composable
private fun EditProductRowsUnselectedPreview() {
    EditProductRows(
        items = listOf(
            Product("Banana", "Yellowish ripe"),
            Product("Banana", "Yellowish ripe"),
            Product("Banana", ""),
        ),
        selectItem = { _, _, _ -> },
    )
}

@Preview
@Composable
private fun EditProductRowsSelectedPreview() {
    EditProductRows(
        items = listOf(
            Product("Banana", "Yellowish ripe"),
            Product("Banana", "Yellowish ripe", isSelected = true),
            Product("Banana", "", isSelected = true),
        ),
        selectItem = { _, _, _ -> },
    )
}

