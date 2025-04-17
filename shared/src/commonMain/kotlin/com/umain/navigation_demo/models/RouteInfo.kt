package com.umain.navigation_demo.models

import kotlinx.serialization.Serializable

@Serializable
data class RouteInfo(
    val name: RouteName,
    val params: String = DEFAULT_PARAMS,
) {
    companion object {
        internal const val DEFAULT_PARAMS = "{}"
    }
}