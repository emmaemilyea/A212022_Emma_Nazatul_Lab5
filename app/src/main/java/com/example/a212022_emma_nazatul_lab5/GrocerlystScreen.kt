package com.example.a212022_emma_nazatul_lab5

import android.app.Application
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a212022_emma_nazatul_lab5.ui.StartScreen
import com.example.a212022_emma_nazatul_lab5.ui.EcoTipsScreen
import com.example.a212022_emma_nazatul_lab5.ui.AddItemScreen
import com.example.a212022_emma_nazatul_lab5.ui.AnalyticsScreen
import com.example.a212022_emma_nazatul_lab5.ui.SummaryScreen
import com.example.a212022_emma_nazatul_lab5.ui.GrocerlystViewModel
import com.example.a212022_emma_nazatul_lab5.ui.AppViewModelProvider

//Define the Route Names
enum class GrocerlystScreen {
    Start,
    AddItem,
    Summary,
    EcoTips,
    Analytics
}

@Composable
fun GrocerlystApp(
    navController: NavHostController = rememberNavController()
) {
    val viewModel: GrocerlystViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    )

    //Collect the state from ViewModel so the UI stays in sync
    val uiState by viewModel.uiState.collectAsState()

    //Collect database entries stream live from Room SQLite
    val databaseItems by viewModel.groceryItemsStream.collectAsState()

    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = GrocerlystScreen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {

            // -- SCREEN 1: DASHBOARD --
            composable(route = GrocerlystScreen.Start.name) {
                StartScreen(
                    uiState = uiState,
                    databaseItems = databaseItems,
                    onNextButtonClicked = {
                        // Make sure we clear stale edit states when explicitly adding a fresh item
                        viewModel.resetForNextItem()
                        navController.navigate(GrocerlystScreen.AddItem.name)
                    },
                    onEcoTipsClicked = {
                        navController.navigate(GrocerlystScreen.EcoTips.name)
                    },
                    onAnalyticsClicked = {
                        navController.navigate(GrocerlystScreen.Analytics.name)
                    },
                    onItemDeleteRequested = { viewModel.deleteItem(it) },
                    // FIXED ERROR: Connected the item click block to trigger the editing view layout!
                    onItemClick = { item ->
                        viewModel.startEditing(item) // Loads item name/price/id into the input state
                        navController.navigate(GrocerlystScreen.AddItem.name) // Routes to the form
                    }
                )
            }

            // -- SCREEN 2: ADD ITEM FORM --
            composable(route = GrocerlystScreen.AddItem.name) {
                AddItemScreen(
                    uiState = uiState,
                    onNameChange = { viewModel.updateItemName(it) },
                    onPriceChange = { viewModel.updateItemPrice(it) },
                    onNextButtonClicked = {
                        viewModel.saveItem()
                        navController.navigate(GrocerlystScreen.Summary.name)
                    },
                    onCancelButtonClicked = {
                        navController.navigateUp()
                    }
                )
            }

            // -- SCREEN 3: SUMMARY --
            composable(route = GrocerlystScreen.Summary.name) {
                SummaryScreen(
                    uiState = uiState,
                    databaseItems = databaseItems,
                    onStartOverButtonClicked = {
                        viewModel.resetForNextItem()
                        navController.popBackStack(GrocerlystScreen.Start.name, inclusive = false)
                    },
                    onDismissMessage = { viewModel.dismissToast() }
                )
            }

            // -- SCREEN 4: ECO TIPS --
            composable(route = GrocerlystScreen.EcoTips.name) {
                EcoTipsScreen(
                    onListsClicked = { navController.navigate(GrocerlystScreen.Start.name) },
                    onAnalyticsClicked = { navController.navigate(GrocerlystScreen.Analytics.name) }
                )
            }

            // -- SCREEN 5: ANALYTICS (CHARTS) --
            composable(route = GrocerlystScreen.Analytics.name) {
                AnalyticsScreen(
                    uiState = uiState,
                    databaseItems = databaseItems,
                    onListsClicked = { navController.navigate(GrocerlystScreen.Start.name) },
                    onEcoTipsClicked = { navController.navigate(GrocerlystScreen.EcoTips.name) }
                )
            }
        }
    }
}