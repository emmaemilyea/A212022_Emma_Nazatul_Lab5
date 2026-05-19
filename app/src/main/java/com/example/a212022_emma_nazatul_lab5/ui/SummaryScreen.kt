package com.example.a212022_emma_nazatul_lab5.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a212022_emma_nazatul_lab5.data.GroceryEntity
import com.example.a212022_emma_nazatul_lab5.data.GroceryUiState
import com.example.a212022_emma_nazatul_lab5.ui.theme.A212022_Emma_Nazatul_Lab5Theme

@Composable
fun SummaryScreen(
    uiState: GroceryUiState,
    databaseItems: List<GroceryEntity>,
    onStartOverButtonClicked: () -> Unit,
    onDismissMessage: () -> Unit,
    modifier: Modifier = Modifier
) {
    val liveTotalSpent = databaseItems.sumOf { it.price }

    // DYNAMIC CHECK: If currentItemId is NOT 0, the user was EDITING an item!
    val isEditing = uiState.currentItemId != 0

    if (uiState.showToast) {
        AlertDialog(
            onDismissRequest = { onDismissMessage() },
            confirmButton = {
                Button(
                    onClick = { onDismissMessage() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text("OK")
                }
            },
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
            textContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            icon = {
                Icon(
                    imageVector = if (isEditing) Icons.Default.Edit else Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            title = {
                Text(
                    text = if (isEditing) "Item Updated" else "Item Added",
                    fontWeight = FontWeight.ExtraBold
                )
            },
            text = {
                Text(
                    text = uiState.toastMessage,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            shape = RoundedCornerShape(28.dp)
        )
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = if (isEditing) Icons.Default.Edit else Icons.Default.CheckCircle,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = if (isEditing) MaterialTheme.colorScheme.primary else Color(0xFF4CAF50)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (isEditing) "Successfully Updated!" else "Successfully Added!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(text = "ITEM: ${uiState.currentItemName}", fontSize = 18.sp)
                val formattedPrice = uiState.currentItemPrice.toDoubleOrNull() ?: 0.0
                Text(
                    text = "PRICE: RM ${String.format("%.2f", formattedPrice)}",
                    fontSize = 18.sp
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                Text(
                    text = "TOTAL BUDGET SPENT: RM ${String.format("%.2f", liveTotalSpent)}",
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onStartOverButtonClicked,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("DONE & RETURN HOME")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SummaryScreenPreview() {
    A212022_Emma_Nazatul_Lab5Theme {
        SummaryScreen(
            uiState = GroceryUiState(
                currentItemId = 0,
                currentItemName = "Chicken",
                currentItemPrice = "15.00",
                showToast = true,
                toastMessage = "Chicken added to Week 4 list!"
            ),
            databaseItems = listOf(
                GroceryEntity(id = 1, name = "Chicken", price = 15.00)
            ),
            onStartOverButtonClicked = {},
            onDismissMessage = {}
        )
    }
}