package com.example.a212022_emma_nazatul_lab5.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a212022_emma_nazatul_lab5.R

@Composable
fun EcoTipsScreen(
    onListsClicked: () -> Unit,
    onAnalyticsClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.60f)) // Slightly more transparent
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(vertical = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Eco-Friendly Tips",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            // SCROLLABLE LIST
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Sustainability Guide",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                // 1. Reusable Bags
                EcoTipWhiteCard(
                    title = "Bring Your Own Bag",
                    desc = "Plastic bags take 500 years to decompose. Keeping a cloth bag in your car helps reduce waste and saves you money on plastic bag charges.",
                    icon = Icons.Default.ShoppingBag
                )

                // 2. Meal Planning
                EcoTipWhiteCard(
                    title = "Plan Your Meals",
                    desc = "Check your 'Grocerlyst' before you shop. Planning prevents overbuying food that might end up in the trash.",
                    icon = Icons.Default.Restaurant
                )

                // 3. Local Produce
                EcoTipWhiteCard(
                    title = "Support Local Farmers",
                    desc = "Buying local fruits and vegetables reduces 'food miles', lowering the carbon footprint caused by long-distance shipping.",
                    icon = Icons.Default.Home
                )

                // 4. Bulk Buying
                EcoTipWhiteCard(
                    title = "Buy in Bulk",
                    desc = "Items like rice, grains, and detergents in larger packs use less plastic packaging than multiple small packets.",
                    icon = Icons.Default.AllInbox
                )

                // 5. Ugly Produce
                EcoTipWhiteCard(
                    title = "Love 'Ugly' Food",
                    desc = "Misshapen fruits are perfectly healthy! Buying them prevents supermarkets from throwing away perfectly good food.",
                    icon = Icons.Default.Check
                )

                // 6. FIFO Method
                EcoTipWhiteCard(
                    title = "First In, First Out",
                    desc = "Organize your pantry so older items are at the front. This ensures you use them before they expire.",
                    icon = Icons.Default.Update
                )

                // 7. Energy Saving
                EcoTipWhiteCard(
                    title = "Eco-Cooling",
                    desc = "Don't put hot food directly in the fridge. Let it cool down first to save electricity and keep your fridge efficient.",
                    icon = Icons.Default.Eco
                )

                Spacer(modifier = Modifier.height(100.dp)) // Padding for Nav Bar
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
                isSelected = true,
                onClick = {}
            )

            BottomNavItem(
                title = "Analytics",
                icon = Icons.Default.BarChart,
                isSelected = false,
                onClick = onAnalyticsClicked
            )

        }
    }
}

@Composable
fun EcoTipWhiteCard(title: String, desc: String, icon: ImageVector) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .animateContentSize(
                animationSpec = spring(dampingRatio = Spring.DampingRatioNoBouncy)
            ),

        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null
                )
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    lineHeight = 22.sp
                )
            }
        }
    }
}