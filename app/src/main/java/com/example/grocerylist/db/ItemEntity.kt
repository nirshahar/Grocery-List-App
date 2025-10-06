package com.example.grocerylist.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,
    val description: String,
    val isChecked: Boolean = false,
)
