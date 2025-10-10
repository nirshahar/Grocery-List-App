package com.example.grocerylist.ui.data

import com.example.grocerylist.db.ItemEntity

data class Product(
    val name: String,
    val description: String,
    val isSelected: Boolean = false,
    val id: Int = 0,
)

fun ItemEntity.toUI(isChecked: Boolean = false): Product =
    Product(
        id = id,
        name = name,
        description = description,
        isSelected = isChecked,
    )

fun Product.toEntity(): ItemEntity =
    ItemEntity(
        id = id,
        name = name,
        description = description,
    )