package com.umain.navigation_demo.utils

import com.umain.navigation_demo.models.Route
import com.umain.navigation_demo.models.RouteInstance
import com.umain.navigation_demo.models.RouteName
import com.umain.navigation_demo.models.RouteName.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.serialization.json.Json

internal interface DeeplinkHandler {
    fun parseDeeplink(deeplink: String): RouteInstance<*>?
}

internal class DeeplinkHandlerImpl : DeeplinkHandler {

    override fun parseDeeplink(deeplink: String): RouteInstance<*>? {
        val url = runCatching { Url(deeplink) }.getOrNull() ?: return null
        val routeName = RouteName.entries.firstOrNull { it.name == url.host } ?: return null

        return when (routeName) {
            Home -> RouteInstance.from(Route.Home)
            Account -> RouteInstance.from(Route.Account)
            Login -> RouteInstance.from(Route.Login)
            Modal -> deserializeParameters<Route.Modal.Params>(url.parameters)?.let {
                RouteInstance.from(Route.Modal, it)
            }

    }

    private inline fun <reified T> deserializeParameters(parameters: Parameters): T? {
        val jsonString = Json.encodeToString(parameters.toMap())
        return Json.decodeFromString(jsonString)
    }
}

