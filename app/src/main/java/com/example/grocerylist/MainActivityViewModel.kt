package com.example.grocerylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grocerylist.db.ItemsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(
    val itemsDao: ItemsDao
): ViewModel() {

    fun optimizeDb() {
        viewModelScope.launch(Dispatchers.IO) {
            itemsDao.optimizeDb()
        }
    }
}