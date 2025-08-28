package com.example.ynsdemo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ynsdemo.cart.CartScreen

data class HomeTab(
    val icon: ImageVector,
    val label: String,
    val route: String
)

val tabs = listOf(
    HomeTab(Icons.Filled.Home, "Home", "home"),
    HomeTab(Icons.Filled.Search, "Search", "search"),
    HomeTab(Icons.AutoMirrored.Filled.List, "Category", "category"),
    HomeTab(Icons.Filled.ShoppingCart, "Cart", "cart"),
    HomeTab(Icons.Filled.Person, "Me", "me")
)

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val state by navController
        .currentBackStackEntryFlow
        .collectAsState(navController.currentBackStackEntry)

    Scaffold(
        topBar = {
            tabs.find { it.route == state?.destination?.route }?.let {
                TopBarCommon(it.label)
            }

        },
        bottomBar =
            {
                NavigationBar {
                    tabs.forEach {
                        NavigationBarItem(
                            icon = { Icon(it.icon, contentDescription = null) },
                            label = { Text(it.label, maxLines = 1) },
                            selected = state?.destination?.route == it.route,
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
                CartScreen()
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

@Preview
@Composable
fun TopBarCommon(title: String = "IHerb",
                 buttons: @Composable ()-> Unit = {
                 }
){
    Box (
        Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(colorResource(R.color.iherb_green))
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(colorResource(R.color.iherb_green))
        ) {
            Box(
                Modifier
                    .align(Alignment.Center)
                    .padding(start = 0.dp, end = 8.dp, top = 10.dp, bottom = 10.dp)
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .background(Color.Transparent)
                        .align(Alignment.Center)
                        .fillMaxWidth()
                )
                Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                    buttons()
                }

            }
        }
    }
}
