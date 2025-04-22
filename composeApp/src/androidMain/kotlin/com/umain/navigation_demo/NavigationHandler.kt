package com.umain.navigation_demo

import androidx.compose.material.navigation.bottomSheet
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import org.koin.compose.koinInject
import androidx.compose.material.navigation.rememberBottomSheetNavigator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.umain.navigation_demo.models.NavigationEvent
import com.umain.navigation_demo.models.RouteName
import com.umain.navigation_demo.screens.AccountScreen
import com.umain.navigation_demo.screens.HomeScreen
import com.umain.navigation_demo.screens.LoginScreen
import com.umain.navigation_demo.screens.ModalScreen


@Composable
fun NavigationHandler() {
    // setup navigation infrastructure
    val navigation: NavigationObservable = koinInject()
    val navController = rememberNavController()
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    navController.navigatorProvider += bottomSheetNavigator

    // start observing KMP navigation events
    LaunchedEffect(key1 = LocalContext.current) {
        LocalLifecycleOwner.current.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            navigation.navigationObservable.collect {
                when (it) {
                    is NavigationEvent.Pop -> navController.navigateUp()
                    is NavigationEvent.PopToRoot -> navController.popBackStack()
                    is NavigationEvent.Push -> navController.navigate(it.route.routeString)
                    is NavigationEvent.PushAsModal -> navController.navigate(it.route.routeString)
                }
            }
        }
    }

    // build navHost where KMP routes are mapped to Compose screens
    NavHost(
        navController = navController,
        startDestination = RouteName.Home.name
    ) {
        composable(route = RouteName.Home.routeTemplate, arguments = navArguments) {
            HomeScreen(viewModel = koinInject(), params = it.routeParams)
        }

        composable(route = RouteName.Account.routeTemplate, arguments = navArguments) {
            AccountScreen(viewModel = koinInject(), params = it.routeParams)
        }

        bottomSheet(route = RouteName.Modal.routeTemplate, arguments = navArguments) {
            ModalScreen(viewModel = koinInject(), params = it.routeParams)
        }

        composable(route = RouteName.Login.routeTemplate, arguments = navArguments) {
            LoginScreen(viewModel = koinInject(), params = it.routeParams) {
                navController.navigateUp() // android can still manually modify backstack
            }
        }
    }
}