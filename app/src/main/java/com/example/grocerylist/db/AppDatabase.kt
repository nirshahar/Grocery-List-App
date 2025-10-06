package com.example.grocerylist.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ItemEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun itemsDao(): ItemsDao

    companion object {
        const val DB_NAME = "APP_DATABASE"
    }
}