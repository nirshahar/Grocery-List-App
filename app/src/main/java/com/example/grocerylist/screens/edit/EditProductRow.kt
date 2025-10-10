package com.example.grocerylist.screens.edit

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DragHandle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.grocerylist.ui.data.Product
import sh.calvin.reorderable.ReorderableCollectionItemScope
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@Composable
fun EditProductRows(
    items: List<Product>,
    swapItemsOrder: suspend (firstItem: Product, secondItem: Product) -> Unit,
    selectItem: (idx: Int, item: Product, isChecked: Boolean) -> Unit,
) {
    val hapticFeedback = LocalHapticFeedback.current

    val lazyListState = rememberLazyListState()
    val reorderableLazyListState = rememberReorderableLazyListState(lazyListState) { from, to ->
        swapItemsOrder(items[from.index], items[to.index])

        hapticFeedback.performHapticFeedback(HapticFeedbackType.SegmentFrequentTick)
    }

    LazyColumn(
        state = lazyListState,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        val isSelectionActive = items.any { it.isSelected }

        itemsIndexed(items, key = { _, item -> item.id }) { idx, item ->
            ReorderableItem(reorderableLazyListState, key = item.id) { isDragging ->
                EditProductRow(
                    item = item,
                    isSelectionActive = isSelectionActive,
                    isDragging = isDragging,
                    onSelect = { isSelected -> selectItem(idx, item, isSelected) },
                )
            }
        }
    }
}

@Composable
fun ReorderableCollectionItemScope.EditProductRow(
    item: Product,
    isSelectionActive: Boolean,
    isDragging: Boolean,
    onSelect: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val hapticFeedback = LocalHapticFeedback.current

    val isSelectionActive = isSelectionActive || item.isSelected
    val elevation by animateDpAsState(if (isDragging) 4.dp else 0.dp)


    Card(
        elevation = CardDefaults.cardElevation(elevation), modifier = modifier.padding(4.dp)
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
                Icon(
                    Icons.Default.DragHandle,
                    contentDescription = "Reorder",
                    modifier = Modifier.draggableHandle(
                        onDragStarted = {
                            hapticFeedback.performHapticFeedback(HapticFeedbackType.GestureThresholdActivate)
                        },
                        onDragStopped = {
                            hapticFeedback.performHapticFeedback(HapticFeedbackType.GestureEnd)
                        },
                    )
                )
            }

            Column(
                modifier = Modifier.defaultMinSize(minHeight = 32.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    item.name, style = MaterialTheme.typography.titleMedium
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
            Product("Chocolate", "Tnoova brand"),
            Product("Meat", ""),
        ),
        selectItem = { _, _, _ -> },
        swapItemsOrder = { _, _ -> },
    )
}

@Preview
@Composable
private fun EditProductRowsSelectedPreview() {
    EditProductRows(
        items = listOf(
            Product("Banana", "Yellowish ripe"),
            Product("Chocolate", "Tnoova brand", isSelected = true),
            Product("Meat", "", isSelected = true),
        ),
        selectItem = { _, _, _ -> },
        swapItemsOrder = { _, _ -> },
    )
}