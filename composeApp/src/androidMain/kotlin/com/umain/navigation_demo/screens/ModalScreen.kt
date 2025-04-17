package com.umain.navigation_demo.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.umain.navigation_demo.Screen
import com.umain.navigation_demo.viewmodels.HomeEvent
import com.umain.navigation_demo.viewmodels.HomeViewModel
import com.umain.navigation_demo.viewmodels.ModalEvent
import com.umain.navigation_demo.viewmodels.ModalState
import com.umain.navigation_demo.viewmodels.ModalViewModel

@Composable
fun ModalScreen(
    viewModel: ModalViewModel,
    params: String,
) {
    val uiState = remember { mutableStateOf(viewModel.state.value) }

    LaunchedEffect(key1 = true) {
        viewModel.state.collect { newState ->
            uiState.value = newState
        }

        viewModel.emit(ModalEvent.ViewAppeared(params))
    }

    when (uiState.value) {
        ModalState.Loading -> Text(text = "Loading")
        is ModalState.ModalScreen -> Column {
            val state = uiState.value as ModalState.ModalScreen

            Text(text = state.data)
            Button(onClick = { viewModel.emit(ModalEvent.CloseModal) }) {
                Text(text = "go back")
            }
        }
    }
}

