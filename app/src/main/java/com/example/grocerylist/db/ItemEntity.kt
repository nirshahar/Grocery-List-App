package com.example.grocerylist.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "items",
    indices = [Index(value = ["order"])]
)
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val order: Int,
    val name: String,
    val description: String,
)
