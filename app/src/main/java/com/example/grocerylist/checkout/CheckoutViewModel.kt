package com.example.grocerylist.checkout

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CheckoutViewModel : ViewModel() {
    private val _items: MutableStateFlow<List<CheckoutItem>> =
        MutableStateFlow(listOf())
    val items: StateFlow<List<CheckoutItem>> = _items.asStateFlow()

    fun addItem(item: CheckoutItem) {
        _items.update {
            val list = it.toMutableList()

            list.add(item)

            return@update list
        }
    }

    fun checkItemByIdx(idx: Int, isChecked: Boolean) {
        _items.update {
            val list = it.toMutableList()

            if (idx in list.indices) {
                list[idx] = list[idx].copy(isChecked = isChecked)
            }

            return@update list
        }
    }
}