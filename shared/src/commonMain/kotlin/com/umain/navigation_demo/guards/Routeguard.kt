package com.umain.navigation_demo.guards

import com.umain.navigation_demo.models.NavigationEvent
import com.umain.navigation_demo.models.RouteInfo
import com.umain.navigation_demo.models.RouteInstance

internal interface RouteGuard {
    /**
     * evaluate the condition of this guard, will either return null if validated or
     * an alternative [RouteInstance] when failing
     */
    suspend fun validate(
        origin: RouteInstance<*>,
        destination: RouteInfo,
    ): NavigationEvent?
}
