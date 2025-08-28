package com.example.ynsdemo.cart

import CartViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CartScreen() {
    val cartViewModel =  CartViewModel()
    val cartItems by cartViewModel.cartItems.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Cart Screen")
        Text("Items:")
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.0f)
        ) { items(cartItems) { item -> Text("${item.name} - ${item.price}") } }
        Row(
            modifier = Modifier.fillMaxWidth().height(64.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Total: $0.00") // Placeholder for total price
            Button(onClick = { /* TODO: Implement checkout logic */ }) { Text("Checkout") }
        }
    }
}