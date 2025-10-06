package com.example.grocerylist.screens.checkout

import com.example.grocerylist.db.ItemEntity

data class CheckoutItem(
    val name: String,
    val description: String,
    val isChecked: Boolean = false,
    val id: Int = 0,
)

fun ItemEntity.toUI(): CheckoutItem =
    CheckoutItem(
        id = id,
        name = name,
        description = description,
        isChecked = false,
    )

fun CheckoutItem.toEntity(): ItemEntity =
    ItemEntity(
        id = id,
        name = name,
        description = description,
    )