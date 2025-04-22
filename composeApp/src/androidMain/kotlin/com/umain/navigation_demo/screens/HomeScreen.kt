package com.umain.navigation_demo.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.umain.navigation_demo.viewmodels.HomeEvent
import com.umain.navigation_demo.viewmodels.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    params: String,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Home")

        Button(onClick = { viewModel.emit(HomeEvent.GoToAccount) }) {
            Text(text = "Account")
        }

        Button(onClick = {
            viewModel.emit(HomeEvent.GoToModal)
            Log.v("Home", "GoToModal")
        }) {
            Text(text = "Modal")
        }
    }
}
