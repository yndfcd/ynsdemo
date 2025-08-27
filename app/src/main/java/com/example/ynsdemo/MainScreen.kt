package com.example.ynsdemo

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

data class BottomNavigationItemData(
    val icon: ImageVector,
    val label: String,
    val route: String
)

val tabs = listOf(
    BottomNavigationItemData(Icons.Filled.Home, "Home", "home"),
    BottomNavigationItemData(Icons.Filled.Search, "Search", "search"),
    BottomNavigationItemData(Icons.AutoMirrored.Filled.List, "Category", "category"),
    BottomNavigationItemData(Icons.Filled.ShoppingCart, "Cart", "cart"),
    BottomNavigationItemData(Icons.Filled.Person, "Me", "me")
)

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar =
            {
                NavigationBar {
                    tabs.forEach {
                        NavigationBarItem(
                            icon = { Icon(it.icon, contentDescription = null) },
                            label = { Text(it.label, maxLines = 1) },
                            selected = navController.currentDestination?.route == it.route,
                            onClick = { navController.navigate(it.route) }
                        )
                    }
                }
            }
    ) { paddingValues ->

        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            startDestination = "home"
        ) {
            composable("home") {
                // Content for the Home screen
                Text("Home Screen")
            }
            composable("search") {
                // Content for the Search screen
                Text("Search Screen")
            }
            composable("cart") {
                // Content for the Cart screen
                Text("Cart Screen")
            }
            composable("category") {
                // Content for Item 4 screen
                Text("Item 4 Screen")
            }
            composable("me") {
                // Content for Item 5 screen
                Text("Item 5 Screen")
            }
        }
    }
}
