package com.umain.navigation_demo.models

import kotlinx.serialization.KSerializer

internal sealed class Route<T>(
    val name: RouteName,
    val serializer: KSerializer<T>? = null,
    val guards: List<RouteGuard>,
) {
    data object Home : Route<Nothing>(
        name = RouteName.Home,
        guards = RouteGuards.baseGuards
    )
}