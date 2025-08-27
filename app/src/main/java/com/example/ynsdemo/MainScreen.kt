package com.example.ynsdemo

import MainIntent
import MainViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ynsdemo.ui.theme.YnsDemoTheme

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel(), navController: NavController = rememberNavController()) {

    // Define routes as constants
    private object Routes {
        const val MAIN_SCREEN = "main_screen"
        const val CART_SCREEN = "cart_screen"
    }

    val items = listOf(
        "Home",
        "Search",
        "Cart",
        "Profile")
    val state by viewModel.state.collectAsState()

    NavHost(navController = navController, startDestination = Routes.MAIN_SCREEN) {
        composable(Routes.MAIN_SCREEN) {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Column(modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    TextField(
                        value = state.name,
                        onValueChange = { viewModel.processIntent(MainIntent.UpdateName(it)) },
                        label = { Text("Enter your name") },
                        modifier = Modifier.padding(16.dp)
                    )
                    Button(
                        onClick = { viewModel.processIntent(MainIntent.SayHello) },
                        modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
                    ) {
                        Text("Say Hello")
                    }
                    Text(text = state.greeting, modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp))
                }
            }
            Scaffold(
                bottomBar = {
                    NavigationBar {
                        items.forEachIndexed { index, item ->
                            NavigationBarItem(
                                icon = {
                                    // Replace with actual icons
                                    Icon(
                                        painterResource(id = R.drawable.ic_launcher_foreground),
                                        contentDescription = item,
                                        modifier = Modifier.height(24.dp)
                                    )
                                },
                                label = { Text(item, textAlign = TextAlign.Center, fontSize = 9.sp) },
                                selected = false, // Handle selected state based on current destination
                                onClick = {
                                    when (index) {
                                        2 -> navController.navigate(Routes.CART_SCREEN) // Navigate to CartScreen on third button click
                                        // Add navigation for other buttons here
                                    }
                                },
                                alwaysShowLabel = true
                            )
                        }
                    }
                }
            ) { innerPadding ->
                // Content of your screens will go here, using innerPadding
            }
        }
        composable(Routes.CART_SCREEN) {
            val viewModelStoreOwner = LocalViewModelStoreOwner.current
            requireNotNull(viewModelStoreOwner) { "ViewModelStoreOwner is null" }
            val cartViewModel: CartViewModel = viewModel(viewModelStoreOwner)
            CartScreen(cartViewModel = cartViewModel)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    YnsDemoTheme {
        MainScreen()
    }
}