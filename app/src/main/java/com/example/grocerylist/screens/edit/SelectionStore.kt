package com.example.grocerylist.screens.edit

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SelectionStore {
    private val _selectedIds: MutableStateFlow<Set<Int>> = MutableStateFlow(emptySet())
    val selectedIds: StateFlow<Set<Int>> = _selectedIds.asStateFlow()

    fun selectItem(id: Int, isSelected: Boolean) {
        _selectedIds.update { ids ->
            if (isSelected) {
                ids.plus(id)
            } else {
                ids.minus(id)
            }
        }
    }
}