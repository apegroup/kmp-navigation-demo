package com.umain.navigation_demo.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.umain.navigation_demo.viewmodels.AccountEvent
import com.umain.navigation_demo.viewmodels.AccountViewModel
import com.umain.navigation_demo.viewmodels.HomeEvent
import com.umain.navigation_demo.viewmodels.HomeViewModel

@Composable
fun AccountScreen(
    viewModel: AccountViewModel,
    params: String,
) {
    Column {
        Text(text = "my account")
        Button(onClick = { viewModel.emit(AccountEvent.CloseAccountScreen) }) {
            Text(text = "close")
        }
    }
}

