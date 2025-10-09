package com.example.grocerylist.screens.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grocerylist.db.ItemsDao
import com.example.grocerylist.loading
import com.example.grocerylist.screens.checkout.CheckoutItem
import com.example.grocerylist.screens.checkout.toEntity
import com.example.grocerylist.screens.checkout.toUI
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class EditViewModel(
    private val itemsRepository: ItemsDao, val selectionStore: SelectionStore
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val items = itemsRepository.getAllItemsFlow()
        .combine(selectionStore.selectedIds) { currentItems, selectedIds ->
            currentItems.map { item ->
                item.toUI(item.id in selectedIds)
            }
        }
        .loading(viewModelScope)

    fun addItem(item: CheckoutItem) {
        viewModelScope.launch {
            itemsRepository.upsert(item.toEntity())
        }
    }

    fun removeItem(item: CheckoutItem) {
        viewModelScope.launch {
            itemsRepository.delete(item.toEntity())
        }
    }

    fun selectItem(id: Int, isSelected: Boolean) {
        selectionStore.selectItem(id, isSelected)
    }

}