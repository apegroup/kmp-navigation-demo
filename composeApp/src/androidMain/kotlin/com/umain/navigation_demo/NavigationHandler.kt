package com.umain.navigation_demo

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.navigation.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.umain.navigation_demo.models.NavigationEvent
import com.umain.navigation_demo.models.RouteName
import com.umain.navigation_demo.screens.AccountScreen
import com.umain.navigation_demo.screens.HomeScreen
import com.umain.navigation_demo.screens.LoginScreen
import com.umain.navigation_demo.screens.ModalScreen
import org.koin.compose.koinInject

@Composable
fun NavigationHandler() {
    // setup navigation infrastructure
    val bottomSheetNavigator = rememberCustomBottomSheetNavigator()
    val navigation: NavigationObservable = koinInject()
    val navController = rememberNavController()
    navController.navigatorProvider += bottomSheetNavigator

    // start observing KMP navigation events
    LaunchedEffect(key1 = LocalContext.current) {
        navigation.navigationObservable.collect {
            when (it) {
                is NavigationEvent.Pop -> navController.navigateUp()
                is NavigationEvent.PopToRoot -> navController.popBackStack()
                is NavigationEvent.Push -> navController.navigate(it.route.routeString)
                is NavigationEvent.PushAsModal -> navController.navigate(it.route.routeString)
            }
        }
    }

    // build navHost where KMP routes are mapped to Compose screens
    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        sheetShape = MaterialTheme.shapes.large,
        sheetBackgroundColor = MaterialTheme.colors.surface,
    ) {
        Scaffold { paddingValues ->
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                NavHost(
                    navController = navController,
                    startDestination = RouteName.Home.name,
                    enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
                    exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
                    popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) },
                    popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) }

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
        }
    }
}