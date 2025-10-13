package com.example.grocerylist.screens.edit

import com.example.grocerylist.ui.data.Product

sealed interface DeleteDialogData {
    object Hidden : DeleteDialogData
    data class Shown(val items: List<Product>) : DeleteDialogData
}

