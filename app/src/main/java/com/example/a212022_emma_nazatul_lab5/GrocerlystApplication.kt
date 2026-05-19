package com.example.a212022_emma_nazatul_lab5

import android.app.Application
import com.example.a212022_emma_nazatul_lab5.data.GroceryDatabase
import com.example.a212022_emma_nazatul_lab5.data.GroceryRepository

class GrocerlystApplication : Application() { // Named GrocerlystApplication
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}

class AppContainer(private val context: android.content.Context) {
    val groceryRepository: GroceryRepository by lazy {
        GroceryRepository(GroceryDatabase.getDatabase(context).groceryDao())
    }
}