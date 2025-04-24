package com.umain.navigation_demo

import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.navigation.BottomSheetNavigator
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.umain.navigation_demo.models.RouteInfo
import com.umain.navigation_demo.models.RouteName
import java.net.URLEncoder

/** builds the NavHost name and params template */
val RouteName.routeTemplate get() = "$name?params={params}"

/** fills the NavHost [routeTemplate] with the [RouteInfo] data*/
val RouteInfo.routeString get() = "$name?params=$params"

/** extract KMP route params from the [NavBackStackEntry] */
val NavBackStackEntry.routeParams get(): String = arguments?.getString("params") ?: ""

/** default list of arguments for every route */
val navArguments = listOf(
    navArgument("params") {
        type = NavType.StringType
        defaultValue = ""
        nullable = false
    },
)

/** custom bottom sheet navigator to skip half state */
@Composable
fun rememberCustomBottomSheetNavigator(): BottomSheetNavigator {
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
    )
    return remember(sheetState) {
        BottomSheetNavigator(sheetState = sheetState)
    }
}
