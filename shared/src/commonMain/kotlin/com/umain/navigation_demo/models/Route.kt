package com.umain.navigation_demo.models

import com.umain.navigation_demo.guards.RouteGuards
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

internal sealed class Route<T>(
    val name: RouteName,
    val serializer: KSerializer<T>? = null,
    val guards: List<RouteGuards>,
) {

    data object Home : Route<Nothing>(
        name = RouteName.Home,
        guards = emptyList()
    )

    data object Modal : Route<Modal.Params>(
        name = RouteName.Modal,
        guards = emptyList(),
        serializer = Params.serializer()
    ) {
        @Serializable
        data class Params(
            val foo: String
        )
    }

    data object Account : Route<Nothing>(
        name = RouteName.Account,
        guards = listOf(RouteGuards.Login)
    )

    data object Login : Route<Nothing>(
        name = RouteName.Login,
        guards = emptyList()
    )



    companion object
}