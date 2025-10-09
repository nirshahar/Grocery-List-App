package com.example.grocerylist.ui

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SelectionStore<T> {
    private val _selectedItems: MutableStateFlow<Set<T>> = MutableStateFlow(emptySet())
    val selectedItems: StateFlow<Set<T>> = _selectedItems.asStateFlow()

    fun selectItem(item: T, isSelected: Boolean) {
        _selectedItems.update { items ->
            if (isSelected) {
                items.plus(item)
            } else {
                items.minus(item)
            }
        }
    }
}