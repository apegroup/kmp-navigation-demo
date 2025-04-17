package com.umain.navigation_demo

import com.umain.navigation_demo.guards.RouteGuard
import com.umain.navigation_demo.models.NavigationEvent
import com.umain.navigation_demo.models.Route
import com.umain.navigation_demo.models.RouteInfo
import com.umain.navigation_demo.models.RouteInstance
import com.umain.navigation_demo.models.RouteName
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
    val navigationObservable: CFlow<NavigationEvent>
}

internal interface Navigation : NavigationObservable {

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


internal class NavigationImpl(
    private val routeSerializer: RouteSerializer,
    private val routeGuards: Map<RouteName, List<RouteGuard>>
) : Navigation {

    private val _navigationObservable: MutableSharedFlow<NavigationEvent> = MutableSharedFlow()
    override val navigationObservable: CFlow<NavigationEvent>
        get() = CSharedFlow(_navigationObservable, MainScope()).cFlow()

    private var _currentRoute: MutableStateFlow<RouteInstance<*>> = MutableStateFlow(
        RouteInstance.from(Route.Home)
    )

    override fun setCurrentRoute(route: RouteInstance<*>) = runBlocking {
        _currentRoute.emit(route)
    }

    override suspend fun <T : Any> push(destination: RouteInstance<T>) {
        val route = routeSerializer.buildRouteInfo(destination)
        val potentialRouteIntercept = evaluateRouteGuards(route)

        val event = potentialRouteIntercept ?: NavigationEvent.Push(route)
        _navigationObservable.emit(event)
    }

    override suspend fun <T : Any> pushAsModal(destination: RouteInstance<T>) {
        val route = routeSerializer.buildRouteInfo(destination)
        val potentialRouteIntercept = evaluateRouteGuards(route)

        val event = potentialRouteIntercept ?: NavigationEvent.PushAsModal(route)
        _navigationObservable.emit(event)
    }

    override suspend fun pop() {
        val event = NavigationEvent.Pop
        _navigationObservable.emit(event)
    }

    override suspend fun popToRoot() {
        val event = NavigationEvent.PopToRoot
        _navigationObservable.emit(event)
    }

    private suspend fun evaluateRouteGuards(destination: RouteInfo): NavigationEvent? {
        val routeGuards = routeGuards[destination.name].orEmpty()
        return routeGuards.firstNotNullOfOrNull {
            it.validate(origin = _currentRoute.value, destination = destination)
        }
    }
}