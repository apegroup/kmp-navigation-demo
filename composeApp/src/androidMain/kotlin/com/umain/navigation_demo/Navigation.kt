package com.umain.navigation_demo

import androidx.compose.material.navigation.bottomSheet
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import org.koin.compose.koinInject
import androidx.compose.material.navigation.rememberBottomSheetNavigator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.umain.navigation_demo.models.NavigationEvent
import com.umain.navigation_demo.models.RouteInfo
import com.umain.navigation_demo.models.RouteName
import com.umain.navigation_demo.screens.AccountScreen
import com.umain.navigation_demo.screens.HomeScreen
import com.umain.navigation_demo.screens.LoginScreen
import com.umain.navigation_demo.screens.ModalScreen
import java.net.URLEncoder


@Composable
fun Navigation() {
    val navigation: NavigationObservable = koinInject()
    val navController by rememberUpdatedState(newValue = rememberNavController())
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    // Add the bottom sheet navigator to the controller
    navController.navigatorProvider += bottomSheetNavigator

    LaunchedEffect(key1 = context) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            navigation.navigationObservable.collect {
                when (it) {
                    is NavigationEvent.Pop -> navController.navigateUp()
                    is NavigationEvent.PopToRoot -> navController.popBackStack()
                    is NavigationEvent.Push -> navController.navigate(it.route.toRouteString())
                    is NavigationEvent.PushAsModal -> navController.navigate(it.route.toRouteString())
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = RouteName.Home.name
    ) {

        composable(route = "${RouteName.Home.name}?params={params}", arguments = listOf(paramsArgument)) {
            HomeScreen(viewModel = koinInject(), params = it.routeParams)
        }

        composable(route = "${RouteName.Account.name}?params={params}", arguments = listOf(paramsArgument)) {
            AccountScreen(viewModel = koinInject(), params = it.routeParams)
        }

        bottomSheet(route = "${RouteName.Modal.name}?params={params}", arguments = listOf(paramsArgument)) {
            ModalScreen(viewModel = koinInject(), params = it.routeParams)
        }

        composable(route = "${RouteName.Login.name}?params={params}", arguments = listOf(paramsArgument)) {
            LoginScreen(viewModel = koinInject(), params = it.routeParams) {
                navController.navigateUp() // android can still manually modify backstack
            }
        }
    }
}

// helper functions to clean up navHost
private fun RouteInfo.toRouteString(params: String = "") = "$name?params=${URLEncoder.encode(params, "UTF-8")}"
private val NavBackStackEntry.routeParams get(): String = arguments?.getString("params") ?: ""
private val paramsArgument = navArgument("params") {
    type = NavType.StringType
    defaultValue = ""
    nullable = false
}