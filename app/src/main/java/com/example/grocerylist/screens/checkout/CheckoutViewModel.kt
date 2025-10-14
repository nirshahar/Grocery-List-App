package com.example.grocerylist.screens.checkout

import androidx.lifecycle.ViewModel
import com.example.grocerylist.LoadingState
import com.example.grocerylist.db.ItemsDao
import com.example.grocerylist.reloadFromSupplier
import com.example.grocerylist.ui.data.Product
import com.example.grocerylist.ui.data.toUI
import com.example.grocerylist.updateLoaded
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CheckoutViewModel(
    val itemsRepository: ItemsDao
) : ViewModel() {
    private val _items: MutableStateFlow<LoadingState<List<Product>>> =
        MutableStateFlow(LoadingState.Loading())
    val items: StateFlow<LoadingState<List<Product>>> = _items.asStateFlow()

    suspend fun loadItems() {
        _items.reloadFromSupplier {
            itemsRepository.getAllItemsSuspend().map { it.toUI() }
        }
    }

    fun checkItemByIdx(idx: Int, isChecked: Boolean) {
        _items.updateLoaded {
            val list = it.toMutableList()

            if (idx in list.indices) {
                list[idx] = list[idx].copy(isSelected = isChecked)
            }

            return@updateLoaded list
        }
    }
}