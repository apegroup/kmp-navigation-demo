package com.umain.navigation_demo

import com.umain.navigation_demo.guards.RouteGuard
import com.umain.navigation_demo.models.NavigationEvent
import com.umain.navigation_demo.models.Route
import com.umain.navigation_demo.models.RouteInfo
import com.umain.navigation_demo.models.RouteInstance
import com.umain.navigation_demo.models.RouteName
import com.umain.navigation_demo.utils.DeeplinkHandler
import com.umain.navigation_demo.utils.RouteSerializer
import com.umain.revolver.flow.CFlow
import com.umain.revolver.flow.CSharedFlow
import com.umain.revolver.flow.cFlow
import com.umain.revolver.flow.cSharedFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.runBlocking

interface NavigationObservable {
    /** subscribe to this flow to receive KMP [NavigationEvent] */
    val navigationObservable: CFlow<NavigationEvent>

    /** handles incoming deeplink string like "myApp://home" as a navigation event */
    suspend fun handleDeeplink(deeplink: String)
}

internal interface Navigation {

    fun setCurrentRoute(route: RouteInstance<*>)

    suspend fun <T : Any> push(
        destination: RouteInstance<T>,
    )

    suspend fun <T : Any> pushAsModal(
        destination: RouteInstance<T>,
    )

    suspend fun pop()

    suspend fun popToRoot()
}

/** internal KMP class handling all navigation events to be send to clients */
internal class NavigationImpl(
    private val routeSerializer: RouteSerializer,
    private val deeplinkHandler: DeeplinkHandler,
    private val routeGuards: Map<RouteName, List<RouteGuard>>
) : Navigation, NavigationObservable {

    /** exposed to the clients for receiving navigation events */
    private val _navigationObservable: MutableSharedFlow<NavigationEvent> = MutableSharedFlow()
    override val navigationObservable: CFlow<NavigationEvent>
        get() = CSharedFlow(_navigationObservable, MainScope()).cFlow()

    override suspend fun handleDeeplink(deeplink: String) {
        deeplinkHandler.parseDeeplink(deeplink)?.let { push(it) }
    }

    /** how KMP keeps track of what the current route is without knowing the stack */
    private var _currentRoute: MutableStateFlow<RouteInstance<*>> = MutableStateFlow(RouteInstance.from(Route.Home))
    override fun setCurrentRoute(route: RouteInstance<*>) = runBlocking { _currentRoute.emit(route) }

    /** push the [destination] or a potential intercept on the stack */
    override suspend fun <T : Any> push(destination: RouteInstance<T>) {
        val route = routeSerializer.buildRouteInfo(destination)
        val event = evaluateRouteGuards(route) ?: NavigationEvent.Push(route)
        _navigationObservable.emit(event)
    }

    /** push the [destination] as a modal or a potential intercept on the stack */
    override suspend fun <T : Any> pushAsModal(destination: RouteInstance<T>) {
        val route = routeSerializer.buildRouteInfo(destination)
        val event = evaluateRouteGuards(route) ?: NavigationEvent.PushAsModal(route)
        _navigationObservable.emit(event)
    }

    /** pop top most route from the stack */
    override suspend fun pop() {
        val event = NavigationEvent.Pop
        _navigationObservable.emit(event)
    }

    /** pop the entire stack */
    override suspend fun popToRoot() {
        val event = NavigationEvent.PopToRoot
        _navigationObservable.emit(event)
    }

    /** check to see if any routeguards trigger for the current navigation event */
    private suspend fun evaluateRouteGuards(destination: RouteInfo): NavigationEvent? {
        val routeGuards = routeGuards[destination.name].orEmpty()
        return routeGuards.firstNotNullOfOrNull {
            it.validate(origin = _currentRoute.value, destination = destination)
        }
    }
}