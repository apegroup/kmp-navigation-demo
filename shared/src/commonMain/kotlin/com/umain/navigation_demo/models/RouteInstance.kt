package com.umain.navigation_demo.models

/**
 * A class that bundles a route and its params together as one object.
 * Unlike [RouteInfo] the params in [RouteInstance] is not yet serialized to json.
 *
 * Not all routes have parameters so [params] need to be nullable. However, only
 * [Nothing] routes are allowed to have null params, and to ensure that remains the case
 * the constructor is hidden and on of the two factory methods must be used to create an instance
 * of [RouteInstance]
 *
 * @property route the destination to route to
 * @property params the parameters for the route. Only null for routes of the type [Nothing]
 */
@ConsistentCopyVisibility
internal data class RouteInstance<T : Any> private constructor(
    val route: Route<T>,
    val params: T?,
) {
    companion object {
        fun from(route: Route<Nothing>): RouteInstance<Nothing> {
            return RouteInstance(route, null)
        }

        fun <T : Any> from(
            route: Route<T>,
            params: T,
        ): RouteInstance<T> {
            return RouteInstance(route, params)
        }
    }
}