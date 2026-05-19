package com.example.a212022_emma_nazatul_lab5.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GroceryDao {
    @Query("SELECT * FROM grocery_table ORDER BY id ASC")
    fun getAllItems(): Flow<List<GroceryEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: GroceryEntity)

    @Delete
    suspend fun deleteItem(item: GroceryEntity)

    @Update
    suspend fun updateItem(item: GroceryEntity)
}