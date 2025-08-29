package com.example.ynsdemo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = viewModel()
    val sharedInput by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = sharedInput.inputValue,
            onValueChange = { viewModel.onInputValueChange(it) },
            label = { Text("Input Field 1") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = sharedInput.inputValue,
            onValueChange = { viewModel.onInputValueChange(it) },
            label = { Text("Input Field 2") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}