package com.example.grocerylist.checkout

data class CheckoutItem(
    val itemName: String,
    val description: String,
    val isChecked: Boolean = false,
)
