package com.example.grocerylist.topappbar

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.grocerylist.Load
import com.example.grocerylist.screens.edit.EditViewModel
import com.example.grocerylist.ui.data.Product
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditTopBarActionsHolder(viewModel: EditViewModel = koinViewModel()) {
    val loadingItems by viewModel.items.collectAsState()

    Load(loadingItems, loadingContent = {}) { items ->
        val selectedItems = items.filter { it.isSelected }

        EditTopBarActions(
            selectedItems,
            onDeleteClick = viewModel::showDeleteDialog,
            onEditClick = { item ->
                viewModel.unselectAllItems()
                viewModel.showEditItemBottomSheet(item)
            }
        )
    }
}

@Composable
fun EditTopBarActions(
    selectedItems: List<Product>,
    onDeleteClick: (itemsToDelete: List<Product>) -> Unit,
    onEditClick: (editItem: Product) -> Unit
) {
    if (selectedItems.size == 1) {
        IconButton(onClick = { onEditClick(selectedItems.single()) }) {
            Icon(Icons.Default.Edit, "Edit selected item")
        }
    }

    if (selectedItems.isNotEmpty()) {
        IconButton(onClick = { onDeleteClick(selectedItems) }) {
            Icon(Icons.Default.Delete, "Delete selected items")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EditTopBarPreview() {
    Row {
        EditTopBarActions(listOf(Product("", "")), {}, {})
    }
}