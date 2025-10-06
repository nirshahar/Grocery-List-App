package com.example.grocerylist.screens.checkout

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CheckoutViewModel() : ViewModel() {
    private val _items: MutableStateFlow<List<CheckoutItem>> =
        MutableStateFlow(listOf())
    val items: StateFlow<List<CheckoutItem>> = _items.asStateFlow()

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