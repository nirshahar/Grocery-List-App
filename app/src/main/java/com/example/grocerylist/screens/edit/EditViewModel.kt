package com.example.grocerylist.screens.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grocerylist.db.ItemsDao
import com.example.grocerylist.loading
import com.example.grocerylist.ui.SelectionStore
import com.example.grocerylist.ui.data.Product
import com.example.grocerylist.ui.data.toEntity
import com.example.grocerylist.ui.data.toUI
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class EditViewModel(
    private val itemsRepository: ItemsDao,
) : ViewModel() {
    val selectionStore: SelectionStore<Int> = SelectionStore()

    @OptIn(ExperimentalCoroutinesApi::class)
    val items = itemsRepository.getAllItemsFlow()
        .combine(selectionStore.selectedItems) { currentItems, selectedIds ->
            currentItems.map { item ->
                item.toUI(item.id in selectedIds)
            }
        }
        .loading(viewModelScope)

    var editItemBottomSheetData: Product? by mutableStateOf(null)

    suspend fun swapItemsOrderSuspend(firstItem: Product, secondItem: Product) {
        itemsRepository.swapItemsOrder(firstItem.id, secondItem.id)
    }

    fun addItem(item: Product) {
        viewModelScope.launch {
            itemsRepository.upsert(item.toEntity())
        }
    }

    fun removeItems(items: Iterable<Product>) {
        viewModelScope.launch {
            itemsRepository.delete(items.map { it.toEntity() })
        }
    }

    fun selectItem(id: Int, isSelected: Boolean) {
        selectionStore.selectItem(id, isSelected)
    }

    fun showEditItemBottomSheet(item: Product? = null) {
        editItemBottomSheetData = item ?: Product("", "")
    }

    fun dismissEditItemBottomSheet() {
        editItemBottomSheetData = null
    }

    fun unselectAllItems() {
        selectionStore.unselectAll()
    }
}