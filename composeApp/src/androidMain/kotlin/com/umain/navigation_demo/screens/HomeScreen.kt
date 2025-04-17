package com.umain.navigation_demo.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.umain.navigation_demo.viewmodels.HomeEvent
import com.umain.navigation_demo.viewmodels.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    params: String,
) {
    val uiState = remember { mutableStateOf(viewModel.state.value) }

    LaunchedEffect(key1 = true) {
        viewModel.state.collect { newState ->
            uiState.value = newState
        }
    }

    Column {
        Text(text = "Home")

        Button(onClick = { viewModel.emit(HomeEvent.GoToModal) }) {
            Text(text = "Account")
        }

        Button(onClick = { viewModel.emit(HomeEvent.GoToModal) }) {
            Text(text = "Modal")
        }
    }
}

