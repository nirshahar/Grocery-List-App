package com.example.grocerylist.screens.edit

import com.example.grocerylist.ui.data.Product


sealed interface EditItemBottomSheetData {
    object Hidden : EditItemBottomSheetData
    data class Shown(val item: Product) : EditItemBottomSheetData
}