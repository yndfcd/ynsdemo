package com.example.ynsdemo

import CartViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(viewModel: CartViewModel) {
    val cartItems by viewModel.cartItems.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Shopping Cart") })
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Total Items: ${cartItems.size}")
                    Button(onClick = { viewModel.clearCart() }) {
                        Text("Clear Cart")
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(cartItems) { item ->
                CartItemRow(item = item.name, onRemoveItem = { viewModel.removeItem(item) })
            }
        }
    }
}

@Composable
fun CartItemRow(item: String, onRemoveItem: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = item)
        Button(onClick = onRemoveItem) {
            Text("Remove")
        }
    }
    HorizontalDivider()
}