package com.example.grocerylist.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemsDao {
    @Upsert
    suspend fun upsert(item: ItemEntity)

    @Delete
    suspend fun delete(item: ItemEntity)

    @Query("SELECT * FROM items")
    fun getAllItemsFlow(): Flow<List<ItemEntity>>

    @Query("SELECT * FROM items")
    suspend fun getAllItemsSuspend(): List<ItemEntity>
}