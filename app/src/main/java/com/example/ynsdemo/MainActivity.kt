package com.example.ynsdemo

import MainIntent
import MainViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ynsdemo.ui.theme.YnsDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YnsDemoTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "main_screen") {
                    composable("main_screen") {
                        val viewModel: MainViewModel = viewModel()
                        MainScreen(viewModel = viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val state by viewModel.state.collectAsState()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        TextField(
            value = state.name,
            onValueChange = { viewModel.processIntent(MainIntent.UpdateName(it)) },
            label = { Text("Enter your name") },
            modifier = Modifier.padding(innerPadding).padding(16.dp)
        )
        Button(
            onClick = { viewModel.processIntent(MainIntent.SayHello) },
            modifier = Modifier.padding(innerPadding).padding(top = 80.dp, start = 16.dp)
        ) {
            Text("Say Hello")
        }
        Text(text = state.greeting, modifier = Modifier.padding(innerPadding).padding(top = 140.dp, start = 16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    YnsDemoTheme {
        Greeting("Android")
    }
}