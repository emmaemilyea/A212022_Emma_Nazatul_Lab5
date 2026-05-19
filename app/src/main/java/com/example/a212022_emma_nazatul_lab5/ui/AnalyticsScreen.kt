package com.example.a212022_emma_nazatul_lab5.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a212022_emma_nazatul_lab5.R
import com.example.a212022_emma_nazatul_lab5.data.GroceryEntity
import com.example.a212022_emma_nazatul_lab5.data.GroceryUiState

@Composable
fun AnalyticsScreen(
    uiState: GroceryUiState,
    databaseItems: List<GroceryEntity>, // Continuous database listener array
    onListsClicked: () -> Unit,
    onEcoTipsClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    // FIXED: Calculate March Week 4 budget dynamic spending straight from your Room Database table rows!
    val totalMarchWeek4Spent = databaseItems.sumOf { it.price }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.grocerlystbg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.65f))
        ) {
            // FULL WIDTH HEADER
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(vertical = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Weekly Spending Charts",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // --- JANUARY (Mock data with one week overbudget) ---
                MonthlyBarChartCard(
                    monthName = "January 2026",
                    week1 = 45.0,
                    week2 = 120.0, // RED BAR
                    week3 = 35.0,
                    week4 = 70.0
                )

                // --- FEBRUARY (Mock data) ---
                MonthlyBarChartCard(
                    monthName = "February 2026",
                    week1 = 80.0,
                    week2 = 60.0,
                    week3 = 95.0,
                    week4 = 40.0
                )

                // --- MARCH (Live Data for Week 4 linked to SQLite) ---
                MonthlyBarChartCard(
                    monthName = "March 2026",
                    week1 = 95.0,
                    week2 = 40.0,
                    week3 = 60.0,
                    week4 = totalMarchWeek4Spent // CHANGED: Connected to the persistent DB calculation variable
                )

                // Label for Empty Months
                Text(
                    "Upcoming Months",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                // April Mockup (Empty)
                MonthlyBarChartCard("April 2026", 0.0, 0.0, 0.0, 0.0)

                Spacer(modifier = Modifier.height(100.dp))
            }
        }

        // BOTTOM NAVIGATION
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BottomNavItem(
                title = "Lists",
                icon = Icons.Default.List,
                isSelected = false,
                onClick = onListsClicked
            )

            BottomNavItem(
                title = "Eco Tips",
                icon = Icons.Default.Info,
                isSelected = false,
                onClick = onEcoTipsClicked
            )

            BottomNavItem(
                title = "Analytics",
                icon = Icons.Default.BarChart,
                isSelected = true,
                onClick = {}
            )
        }
    }
}

@Composable
fun MonthlyBarChartCard(
    monthName: String,
    week1: Double,
    week2: Double,
    week3: Double,
    week4: Double
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = monthName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                BarIndicator("W1", week1)
                BarIndicator("W2", week2)
                BarIndicator("W3", week3)
                BarIndicator("W4", week4)
            }
        }
    }
}

@Composable
fun BarIndicator(label: String, amount: Double) {
    val budgetLimit = 100.0
    val isOverBudget = amount > budgetLimit
    val barHeightFraction = (amount / 150.0).coerceIn(0.01, 1.0).toFloat()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxHeight()
    ) {
        if (amount > 0) {
            Text(
                text = "RM${String.format("%.2f", amount)}",
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = if (isOverBudget) Color.Red else Color.DarkGray
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .width(32.dp)
                .fillMaxHeight(barHeightFraction)
                .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                .background(if (isOverBudget) Color.Red else MaterialTheme.colorScheme.primary)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = label, fontSize = 11.sp, fontWeight = FontWeight.Medium)
    }
}