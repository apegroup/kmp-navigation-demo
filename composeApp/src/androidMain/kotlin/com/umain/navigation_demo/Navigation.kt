package com.umain.navigation_demo

import androidx.compose.material.navigation.ModalBottomSheetLayout
import androidx.compose.material.navigation.bottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
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
import java.net.URLEncoder

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Account : Screen("Account")
    data object Login : Screen("Login")
    data object Modal : Screen("Modal")

    fun asRoute(params: String = "") = "$route?&params=${URLEncoder.encode(params, "UTF-8")}"
}

private fun buildRoute(routeInfo: RouteInfo) = when (routeInfo.name) {
    RouteName.Home -> Screen.Home.asRoute(routeInfo.params)
    RouteName.Account -> Screen.Account.asRoute(routeInfo.params)
    RouteName.Login -> Screen.Login.asRoute(routeInfo.params)
    RouteName.Modal -> Screen.Modal.asRoute(routeInfo.params)
}


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
                    is NavigationEvent.Push -> navController.navigate(buildRoute(it.route))
                    is NavigationEvent.PushAsModal -> navController.navigate(buildRoute(it.route))
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        Screen(Screen.Home.route) {
            HomeScreen(viewModel = koinInject(), params = it)
        }

        Screen(Screen.Account.route) {
            AccountScreen(viewModel = koinInject(), params = it)
        }

        Screen(Screen.Login.route) {
            LoginScreen(viewModel = koinInject(), params = it) {
                navController.navigateUp() // android can still manually modify backstack
            }
        }

        Modal(route = Screen.Modal.route) {
            HomeScreen(viewModel = koinInject(), params = it)
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
