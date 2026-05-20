package com.example.a212022_emma_nazatul_lab5.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.a212022_emma_nazatul_lab5.data.GroceryDatabase
import com.example.a212022_emma_nazatul_lab5.data.GroceryEntity
import com.example.a212022_emma_nazatul_lab5.data.GroceryRepository
import com.example.a212022_emma_nazatul_lab5.data.GroceryUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Changed to AndroidViewModel to safely pass database context
class GrocerlystViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GroceryRepository

    // Dynamic stream reading the SQLite database values live
    val groceryItemsStream: StateFlow<List<GroceryEntity>>

    init {
        val groceryDao = GroceryDatabase.getDatabase(application).groceryDao()
        repository = GroceryRepository(groceryDao)

        // Converts Database Flow stream directly into a life-cycle aware StateFlow
        groceryItemsStream = repository.allItems.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    //Internal state of the grocery app
    private val _uiState = MutableStateFlow(GroceryUiState())

    //Exposed state for the UI screens to observe
    val uiState: StateFlow<GroceryUiState> = _uiState.asStateFlow()

    //Updates the item name in the state
    fun updateItemName(itemName: String) {
        _uiState.update { currentState ->
            currentState.copy(currentItemName = itemName) //AddItemScreen, SummaryScreen
        }
    }

    //Updates the item price in the state
    fun updateItemPrice(itemPrice: String) {
        _uiState.update { currentState ->
            currentState.copy(currentItemPrice = itemPrice) //AddItemScreen, SummaryScreen
        }
    }

    //Logic to save the item:
    //It adds the price to the totalSpent budget and writes to Room database
    fun saveItem() {
        val priceInput = _uiState.value.currentItemPrice.toDoubleOrNull() ?: 0.0
        val nameInput = _uiState.value.currentItemName
        val currentId = _uiState.value.currentItemId

        if (nameInput.isNotEmpty() && priceInput >= 0.0) {
            viewModelScope.launch {
                // Re-create the entity with the currentId
                val entityItem = GroceryEntity(id = currentId, name = nameInput, price = priceInput)

                if (currentId == 0) {
                    repository.insertItem(entityItem) // Inserts brand new row if ID is 0
                } else {
                    repository.updateItem(entityItem) // Modifies the existing row if ID matches!
                }

                _uiState.update { currentState ->
                    currentState.copy(
                        showToast = true,
                        toastMessage = if (currentId == 0) "$nameInput added to list!" else "$nameInput details updated!"
                    )
                }
            }
        }
    }

    fun dismissToast() { //SummaryScreen
        _uiState.update { it.copy(showToast = false) } //for popup message
    }

    //Clears all data to start fresh
    fun resetForNextItem() {
        _uiState.update { currentState ->
            currentState.copy(
                currentItemId = 0, // CLEAR THE ID back to 0 so the next item added is NEW
                currentItemName = "",
                currentItemPrice = "",
                toastMessage = ""
            )
        }
    }

    fun deleteItem(item: GroceryEntity) {
        viewModelScope.launch {
            repository.deleteItem(item)
        }
    }

    fun startEditing(item: GroceryEntity) {
        _uiState.update { currentState ->
            currentState.copy(
                currentItemId = item.id, // KEEP THE ORIGINAL ID so Room knows which row to update!
                currentItemName = item.name,
                currentItemPrice = item.price.toString(),
                toastMessage = "${item.name} details updated successfully!" // Dynamic message
            )
        }
    }
}