package com.example.a212022_emma_nazatul_lab5.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.a212022_emma_nazatul_lab5.GrocerlystApplication // Updated import

/**
 * Provides Factory to create instance of ViewModel for the entire Grocerlyst app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            GrocerlystViewModel(
                application = grocerlystApplication()
            )
        }
    }
}

/**
 * Extension function to query for the [Application] object and return an instance of
 * [GrocerlystApplication].
 */
fun CreationExtras.grocerlystApplication(): GrocerlystApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as GrocerlystApplication)