package com.example.grocerylist.ui.data

import com.example.grocerylist.db.ItemEntity

data class Product(
    val name: String,
    val description: String,
    val isSelected: Boolean = false,
    val order: Int = 0,
    val id: Int = 0,
)

fun ItemEntity.toUI(isSelected: Boolean = false): Product =
    Product(
        id = id,
        name = name,
        description = description,
        order = order,
        isSelected = isSelected,
    )

fun Product.toEntity(): ItemEntity =
    ItemEntity(
        id = id,
        order = order,
        name = name,
        description = description,
    )