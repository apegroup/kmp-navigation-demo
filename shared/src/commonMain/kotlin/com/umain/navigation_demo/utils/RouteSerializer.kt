package com.umain.navigation_demo.utils

import com.umain.navigation_demo.models.Route
import com.umain.navigation_demo.models.RouteInfo
import com.umain.navigation_demo.models.RouteInstance
import io.ktor.http.decodeURLQueryComponent
import io.ktor.http.encodeURLQueryComponent
import kotlinx.serialization.json.Json

internal interface RouteSerializer {

    fun <T : Any> buildRouteInfo(routeInstance: RouteInstance<T>): RouteInfo
    fun <T : Any> deserializeRouteParams(route: Route<T>, params: String): T
}

internal class RouteSerializerImpl : RouteSerializer {

    private val navigationJsonHandler = Json {
        prettyPrint = false
    }

    override fun <T : Any> buildRouteInfo(routeInstance: RouteInstance<T>): RouteInfo {
        return RouteInfo(
            name = routeInstance.route.name,
            params = serializeRouteParams(routeInstance)
        )
    }

    private fun <T : Any> serializeRouteParams(routeInstance: RouteInstance<T>): String {
        val serializer = routeInstance.route.serializer ?: return RouteInfo.DEFAULT_PARAMS
        val params = routeInstance.params ?: return RouteInfo.DEFAULT_PARAMS


        return navigationJsonHandler.encodeToString(serializer, params).encodeURLQueryComponent()
    }

    override fun <T : Any> deserializeRouteParams(route: Route<T>, params: String): T {
        val serializer = route.serializer
            ?: throw IllegalArgumentException("Cannot deserialize params for route with a null serializer")
        return navigationJsonHandler.decodeFromString(serializer, params.decodeURLQueryComponent())
    }
}