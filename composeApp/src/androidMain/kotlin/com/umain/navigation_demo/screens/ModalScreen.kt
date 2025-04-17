package com.umain.navigation_demo.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        viewModel.emit(ModalEvent.ViewAppeared(params))
        viewModel.state.collect { newState ->
            uiState.value = newState
        }
    }

    when (uiState.value) {
        ModalState.Loading -> Text(text = "Loading")
        is ModalState.ModalScreen ->  Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )  {
            val state = uiState.value as ModalState.ModalScreen

            Text(text = state.data)
            Button(onClick = { viewModel.emit(ModalEvent.CloseModal) }) {
                Text(text = "go back")
            }
        }
    }
}

