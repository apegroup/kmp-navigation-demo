package com.umain.navigation_demo.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.umain.navigation_demo.viewmodels.AccountEvent
import com.umain.navigation_demo.viewmodels.AccountViewModel

@Composable
fun AccountScreen(
    viewModel: AccountViewModel,
    params: String,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "my account")
        Button(onClick = { viewModel.emit(AccountEvent.CloseAccountScreen) }) {
            Text(text = "close")
        }
    }
}
