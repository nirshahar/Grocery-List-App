package com.example.grocerylist

import androidx.room.Room
import com.example.grocerylist.screens.checkout.CheckoutViewModel
import com.example.grocerylist.db.AppDatabase
import com.example.grocerylist.db.ItemsDao
import com.example.grocerylist.screens.edit.EditViewModel
import com.example.grocerylist.screens.edit.SelectionStore
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appKoinModule = module {
    // DB
    single<AppDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        ).build()
    }
    single<ItemsDao> { get<AppDatabase>().itemsDao() }

    // View Models
    viewModelOf(::CheckoutViewModel)
    viewModelOf(::EditViewModel)

    factoryOf(::SelectionStore)
}
