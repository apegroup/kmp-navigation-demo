package com.umain.navigation_demo.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.umain.navigation_demo.viewmodels.LoginEvent
import com.umain.navigation_demo.viewmodels.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    params: String,
    onBackPressed: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )  {
        Text(text = "login")

        Button(onClick = onBackPressed) {
            Text(text = "Cancel login")
        }

        Button(onClick = { viewModel.emit(LoginEvent.LoginComplete) }) {
            Text(text = "login")
        }
    }
}

