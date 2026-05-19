package com.example.a212022_emma_nazatul_lab5.data

import kotlinx.coroutines.flow.Flow

class GroceryRepository(private val groceryDao: GroceryDao) {

    val allItems: Flow<List<GroceryEntity>> = groceryDao.getAllItems()

    suspend fun insertItem(item: GroceryEntity) {
        groceryDao.insert(item)
    }

    suspend fun deleteItem(item: GroceryEntity) = groceryDao.deleteItem(item)

    suspend fun updateItem(item: GroceryEntity) = groceryDao.updateItem(item)
}