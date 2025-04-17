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
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
    val navController = rememberNavController()
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    // Add the bottom sheet navigator to the controller
    navController.navigatorProvider.addNavigator(bottomSheetNavigator)

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

        Screen(RouteName.Home.name) {
            HomeScreen(viewModel = koinInject(), params = it)
        }

        Screen(RouteName.Account.name) {
            AccountScreen(viewModel = koinInject(), params = it)
        }

        Screen(RouteName.Login.name) {
            LoginScreen(viewModel = koinInject(), params = it) {
                navController.navigateUp() // android can still manually modify backstack
            }
        }

        Modal(route = RouteName.Modal.name) {
            ModalScreen(viewModel = koinInject(), params = it)
        }
    }
}

fun NavGraphBuilder.Screen(
    route: String,
    content: @Composable (params: String) -> Unit,
) {
    composable(
        route = "$route?params={params}",
        arguments = listOf(
            navArgument("params") {
                type = NavType.StringType
                defaultValue = ""
                nullable = false
            },
        )
    ) {
        content(it.arguments?.getString("params") ?: "")
    }
}

fun NavGraphBuilder.Modal(
    route: String,
    content: @Composable (params: String) -> Unit,
) {
    bottomSheet(
        route = "$route?params={params}",
        arguments = listOf(
            navArgument("params") {
                type = NavType.StringType
                defaultValue = ""
                nullable = false
            },
        )
    ) {
        content(it.arguments?.getString("params") ?: "")
    }
}

// convert KMP RouteInfo to android string URL
fun RouteInfo.toRouteString(params: String = "") = "$name?&params=${URLEncoder.encode(params, "UTF-8")}"
