package com.umain.navigation_demo.utils

import com.umain.navigation_demo.guards.LoginRouteGuard
import com.umain.navigation_demo.guards.RouteGuard
import com.umain.navigation_demo.guards.RouteGuards
import com.umain.navigation_demo.models.Route
import com.umain.navigation_demo.models.RouteName
import org.koin.core.scope.Scope


/**
 * Build a map of all screens to their respective route guards
 */
internal fun Scope.buildAllScreenRouteGuards(): Map<RouteName, List<RouteGuard>> = buildMap {
    RouteName.entries.map { routeName ->
        val route = Route.getByRouteName(routeName)
        put(route.name, route.guards.map { mapGuardsToInstance(it) })
    }
}

private fun Scope.mapGuardsToInstance(guard: RouteGuards): RouteGuard {
    return when (guard) {
        RouteGuards.Login -> get<LoginRouteGuard>()
    }
}

private fun Route.Companion.getByRouteName(name: RouteName) = when(name) {
    RouteName.Home -> Route.Home
    RouteName.Account -> Route.Account
    RouteName.Login -> Route.Login
    RouteName.Modal -> Route.Modal
}