package com.umain.navigation_demo.guards

internal interface RouteGuard {
    /**
     * evaluate the condition of this guard, will either return null if validated or
     * an alternative [RouteInstance] when failing
     */
    suspend fun validate(
        origin: RouteInstance<*>,
        destination: RouteInstance<*>,
    ): UpdatedNavigationEvent?
}
