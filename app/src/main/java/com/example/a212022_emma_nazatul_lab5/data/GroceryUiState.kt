package com.example.a212022_emma_nazatul_lab5.data

data class GroceryUiState(
    val currentItemId: Int = 0,
    val currentItemName: String = "",
    val currentItemPrice: String = "",
    val totalSpent: Double = 0.00,
    val savedItemName: String = "",
    val savedItems: List<String> = emptyList(),
    val showToast: Boolean = false,
    val toastMessage: String = ""
)