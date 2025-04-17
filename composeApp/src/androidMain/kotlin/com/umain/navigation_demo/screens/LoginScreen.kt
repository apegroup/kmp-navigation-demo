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
import com.umain.navigation_demo.viewmodels.LoginEvent
import com.umain.navigation_demo.viewmodels.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    params: String,
    onBackPressed: () -> Unit,
) {
    Column {
        Text(text = "login")

        Button(onClick = onBackPressed) {
            Text(text = "Cancel login")
        }

        Button(onClick = { viewModel.emit(LoginEvent.LoginComplete) }) {
            Text(text = "login")
        }
    }
}

