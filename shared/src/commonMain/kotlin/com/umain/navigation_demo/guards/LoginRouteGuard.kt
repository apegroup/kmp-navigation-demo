package com.umain.navigation_demo.guards

import com.umain.navigation_demo.models.NavigationEvent
import com.umain.navigation_demo.models.Route
import com.umain.navigation_demo.models.RouteInfo
import com.umain.navigation_demo.models.RouteInstance
import com.umain.navigation_demo.models.RouteName
import com.umain.navigation_demo.utils.RouteSerializer

internal class LoginRouteGuard(
    private val routeSerializer: RouteSerializer
) : RouteGuard {
    override suspend fun validate(
        origin: RouteInstance<*>,
        destination: RouteInfo
    ): NavigationEvent? {
        if (origin.route.name == RouteName.Login) return null
        // todo do an actual authentication check here

        val loginRoute = routeSerializer.buildRouteInfo(RouteInstance.from(Route.Login))
        return NavigationEvent.Push(loginRoute)
    }
}