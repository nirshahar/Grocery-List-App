package com.example.grocerylist.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemsDao {
    @Upsert
    suspend fun _upsert(item: ItemEntity)

    @Upsert
    suspend fun _upsert(items: List<ItemEntity>)

    @Query("SELECT MAX(`order`) FROM items")
    suspend fun getMaxOrder(): Int?

    @Transaction
    suspend fun upsert(item: ItemEntity) {
        val item =
            if (item.order != 0) {
                item
            } else {
                val maxOrder = getMaxOrder() ?: 0
                item.copy(order = maxOrder + 1)
            }

        _upsert(item)
    }

    @Query("UPDATE items SET `order` = :newOrder WHERE id = :id")
    suspend fun updateItemOrder(id: Int, newOrder: Int)

    @Transaction
    suspend fun swapItemsOrder(firstId: Int, secondId: Int) {
        val firstOrder = get(firstId)?.order ?: return
        val secondOrder = get(secondId)?.order ?: return

        updateItemOrder(firstId, secondOrder)
        updateItemOrder(secondId, firstOrder)
    }

    @Transaction
    suspend fun optimizeDb() {
        val items = getAllItemsSuspend()
        val itemsToFix = items.mapIndexedNotNull { idx, item ->
            if (item.order == idx + 1) return@mapIndexedNotNull null // No need to fix elements that are in the correct position
            item.copy(order = idx + 1)
        }

        _upsert(itemsToFix)
    }

    @Query("SELECT * FROM items WHERE id = :id LIMIT 1")
    suspend fun get(id: Int): ItemEntity?

    @Delete
    suspend fun delete(vararg items: ItemEntity)

    @Delete
    suspend fun delete(items: Iterable<ItemEntity>)

    @Query("SELECT * FROM items ORDER BY `order`")
    fun getAllItemsFlow(): Flow<List<ItemEntity>>

    @Query("SELECT * FROM items ORDER BY `order`")
    suspend fun getAllItemsSuspend(): List<ItemEntity>
}