package com.example.a212022_emma_nazatul_lab5.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a212022_emma_nazatul_lab5.R
import com.example.a212022_emma_nazatul_lab5.data.GroceryUiState
import com.example.a212022_emma_nazatul_lab5.ui.theme.A212022_Emma_Nazatul_Lab5Theme

@Composable
fun AddItemScreen(
    uiState: GroceryUiState,
    onNameChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onNextButtonClicked: () -> Unit,
    onCancelButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.grocerlystbg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.85f))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Add Item",
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            //--ITEM NAME FIELD--
            TextField(
                value = uiState.currentItemName, // Value comes from ViewModel
                onValueChange = onNameChange,    // Sends update to ViewModel
                label = { Text("What did you buy?") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            Spacer(modifier = Modifier.height(16.dp))

            //--ITEM PRICE FIELD--
            TextField(
                value = uiState.currentItemPrice, // Value comes from ViewModel
                onValueChange = onPriceChange,    // Sends update to ViewModel
                label = { Text("Price (RM)") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
            )

            Spacer(modifier = Modifier.height(40.dp))

            //--NAVIGATION BUTTONS--
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = onCancelButtonClicked,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("CANCEL")
                }
                Button(
                    onClick = onNextButtonClicked,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    enabled = uiState.currentItemName.isNotEmpty() && uiState.currentItemPrice.isNotEmpty()
                ) {
                    Text("NEXT")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddItemScreenPreview() {
    A212022_Emma_Nazatul_Lab5Theme {
        AddItemScreen(
            uiState = GroceryUiState(currentItemName = "Apples", currentItemPrice = "5.50"),
            onNameChange = {},
            onPriceChange = {},
            onNextButtonClicked = {},
            onCancelButtonClicked = {}
        )
    }
}