package com.example.ynsdemo

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen() {
 Scaffold(
 bottomBar =
 {
 BottomNavigation {
 BottomNavigationItem(
 icon = { Icon(Icons.Filled.Home, contentDescription = null) },
 label = { Text("Home") },
 selected = true, // Placeholder
 onClick = { /*TODO*/ }
 )
 BottomNavigationItem(
 icon = { Icon(Icons.Filled.Home, contentDescription = null) },
 label = { Text("Search") },
 selected = false, // Placeholder
 onClick = { /*TODO*/ }
 )
 BottomNavigationItem(
 icon = { Icon(Icons.Filled.Home, contentDescription = null) },
 label = { Text("Cart") },
 selected = false, // Placeholder
 onClick = { /*TODO*/ }
 )
                BottomNavigationItem(
 icon = { Icon(Icons.Filled.Home, contentDescription = null) },
 label = { Text("Item 4") },
 selected = false, // Placeholder
 onClick = { /*TODO*/ }
 )
                BottomNavigationItem(
 icon = { Icon(Icons.Filled.Home, contentDescription = null) },
 label = { Text("Item 5") },
 selected = false, // Placeholder
 onClick = { /*TODO*/ }
 )
 }
 }
 ) { paddingValues ->
 val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "home", ) {
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
 composable("item4") {
 // Content for Item 4 screen
                Text("Item 4 Screen")
 }
 composable("item5") {
 // Content for Item 5 screen
                Text("Item 5 Screen")
 }
 }
 }
}
package com.example.ynsdemo
