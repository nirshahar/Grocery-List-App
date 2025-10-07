package com.example.grocerylist.screens.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grocerylist.db.ItemsDao
import com.example.grocerylist.loading
import com.example.grocerylist.screens.checkout.CheckoutItem
import com.example.grocerylist.screens.checkout.toEntity
import com.example.grocerylist.screens.checkout.toUI
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

class EditViewModel(
    val itemsRepository: ItemsDao
): ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val items = itemsRepository.getAllItemsFlow().mapLatest { currentItems -> currentItems.map { it.toUI() } }.loading(viewModelScope)

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
}