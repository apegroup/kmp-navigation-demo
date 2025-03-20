package com.umain.navigation_demo.guards

import com.umain.navigation_demo.models.NavigationEvent
import com.umain.navigation_demo.models.RouteInfo
import com.umain.navigation_demo.models.RouteInstance
import com.umain.navigation_demo.models.RouteName

internal class AccountRouteGuard : RouteGuard {

    override suspend fun validate(
        origin: RouteInstance<*>,
        destination: RouteInstance<*>
    ): NavigationEvent? {
        if (destination.route.name != RouteName.Account) return null
        return NavigationEvent.Push(
            route = RouteInfo(RouteName.Login)
        )
    }
}