package com.example.a212022_emma_nazatul_lab5.ui

import androidx.compose.animation.animateColorAsState
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a212022_emma_nazatul_lab5.R
import com.example.a212022_emma_nazatul_lab5.data.GroceryEntity
import com.example.a212022_emma_nazatul_lab5.data.GroceryUiState

@Composable
fun StartScreen(
    uiState: GroceryUiState,
    databaseItems: List<GroceryEntity>,
    onNextButtonClicked: () -> Unit,
    onEcoTipsClicked: () -> Unit,
    onAnalyticsClicked: () -> Unit,
    onItemDeleteRequested: (GroceryEntity) -> Unit,
    onItemClick: (GroceryEntity) -> Unit, // Re-used for single-tap Edit routing
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
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.70f))
                .padding(16.dp)
                .padding(top = 40.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Grocerlyst",
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Welcome, A212022",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "March 2026 Budget",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            ExpandableWeekCard(
                title = "Week 1 (Mar 1-7)",
                amountSpent = 95.0,
                isPastWeek = true,
                uiState = uiState,
                databaseItems = emptyList()
            )

            ExpandableWeekCard(
                title = "Week 2 (Mar 8-14)",
                amountSpent = 40.0,
                isPastWeek = true,
                uiState = uiState,
                databaseItems = emptyList()
            )

            ExpandableWeekCard(
                title = "Week 3 (Mar 15-21)",
                amountSpent = 60.0,
                isPastWeek = true,
                uiState = uiState,
                databaseItems = emptyList()
            )

            ExpandableWeekCard(
                title = "Week 4 (Mar 22-28)",
                amountSpent = databaseItems.sumOf { it.price },
                isPastWeek = false,
                uiState = uiState,
                databaseItems = databaseItems,
                onItemLongClick = { onItemDeleteRequested(it) }, // Kept for safety matching
                onItemClick = onItemClick
            )

            Spacer(modifier = Modifier.height(120.dp))
        }

        //--FLOATING ACTION BUTTON--
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 24.dp, bottom = 100.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.primary)
                .clickable { onNextButtonClicked() }
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "+ ",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "ADD ITEM",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        //--BOTTOM NAVIGATION--
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BottomNavItem(title = "Lists", icon = Icons.Default.List, isSelected = true, onClick = {})

            BottomNavItem(
                title = "Eco Tips",
                icon = Icons.Default.Info,
                isSelected = false,
                onClick = onEcoTipsClicked
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
fun ExpandableWeekCard(
    title: String,
    amountSpent: Double,
    isPastWeek: Boolean,
    addedItem: String = "",
    uiState: GroceryUiState,
    databaseItems: List<GroceryEntity>,
    onItemLongClick: (GroceryEntity) -> Unit = {},
    onItemClick: (GroceryEntity) -> Unit = {}
) {
    var isExpanded by remember { mutableStateOf(false) }
    val budgetLimit = 100.0
    val isOverBudget = amountSpent > budgetLimit && !isPastWeek
    val progressFraction = (amountSpent / budgetLimit).toFloat().coerceIn(0f, 1f)
    val formattedAmount = String.format("%.2f", amountSpent)

    val cardBackgroundColor by animateColorAsState(
        targetValue = if (isOverBudget) {
            Color(0xFFFFEBEE)
        } else if (isExpanded) {
            MaterialTheme.colorScheme.primaryContainer
        } else if (isPastWeek) {
            MaterialTheme.colorScheme.surfaceVariant
        } else {
            MaterialTheme.colorScheme.secondaryContainer
        }
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isExpanded) 6.dp else 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
            .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.displayMedium,
                    color = if (isPastWeek) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.weight(1f))

                ExpandWeekButton(
                    expanded = isExpanded,
                    isPastWeek = isPastWeek,
                    onClick = { isExpanded = !isExpanded }
                )
            }

            if (isExpanded) {
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f).height(8.dp).clip(RoundedCornerShape(4.dp)).background(Color.LightGray)) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(progressFraction)
                                .height(8.dp)
                                .background(if (isOverBudget) Color.Red else MaterialTheme.colorScheme.primary)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "RM $formattedAmount / 100",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isOverBudget) Color.Red else if (isPastWeek) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.primary
                    )
                }

                if (isOverBudget) {
                    Text(
                        text = "⚠️ Warning: You are over budget!",
                        color = Color.Red,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Grocery List:",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                if (isPastWeek) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Check, null, tint = MaterialTheme.colorScheme.outline, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Weekly Essentials", color = MaterialTheme.colorScheme.outline)
                    }
                } else if (databaseItems.isEmpty()) {
                    Text(
                        text = "No items yet",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                } else {
                    databaseItems.forEach { item ->
                        // Tracks whether the unified action chooser dialog should show up
                        var showActionMenu by remember { mutableStateOf(false) }

                        // CHANGED: The single cohesive popup dialogue containing Edit, Delete, or Cancel!
                        if (showActionMenu) {
                            AlertDialog(
                                onDismissRequest = { showActionMenu = false },
                                title = { Text(text = "Manage ${item.name}") },
                                text = { Text(text = "Choose an action for this item. Editing will take you to the modification form.") },
                                confirmButton = {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        // CANCEL OPTION
                                        TextButton(onClick = { showActionMenu = false }) {
                                            Text("Cancel", color = Color.Gray)
                                        }

                                        Spacer(modifier = Modifier.width(8.dp))

                                        // DELETE OPTION
                                        Button(
                                            onClick = {
                                                onItemLongClick(item) // Triggers ViewModel delete row command
                                                showActionMenu = false
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                                        ) {
                                            Text("Delete", color = Color.White)
                                        }

                                        Spacer(modifier = Modifier.width(8.dp))

                                        // EDIT OPTION
                                        Button(
                                            onClick = {
                                                onItemClick(item) // Routes back to form pre-filled
                                                showActionMenu = false
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                        ) {
                                            Text("Edit", color = Color.White)
                                        }
                                    }
                                }
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.3f))
                                // FIXED: Simple, intuitive tap action launches the manage menu instantly!
                                .clickable { showActionMenu = true }
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Check, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = item.name, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium)
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "RM ${String.format("%.2f", item.price)}",
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    imageVector = Icons.Default.MoreVert, // Changed indicator icon to reflect an available menu action
                                    contentDescription = "Options Menu Indicator",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavItem(title: String, icon: ImageVector, isSelected: Boolean, onClick: () -> Unit) {
    val color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
    Column(
        modifier = Modifier.clickable { onClick() }.padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = color,
            modifier = Modifier.size(28.dp)
        )
        Text(
            text = title,
            fontSize = 12.sp,
            color = color,
            fontWeight = if(isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
private fun ExpandWeekButton(
    expanded: Boolean,
    isPastWeek: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = if (expanded) "Collapse" else "Expand",
            tint = if (isPastWeek) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.primary
        )
    }
}