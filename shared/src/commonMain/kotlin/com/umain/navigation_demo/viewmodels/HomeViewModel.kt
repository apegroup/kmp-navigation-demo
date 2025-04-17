package com.umain.navigation_demo.viewmodels

import com.umain.navigation_demo.Navigation
import com.umain.navigation_demo.models.Route
import com.umain.navigation_demo.models.RouteInstance
import com.umain.revolver.Emitter
import com.umain.revolver.RevolverEffect
import com.umain.revolver.RevolverEvent
import com.umain.revolver.RevolverState
import com.umain.revolver.RevolverViewModel


sealed class HomeState : RevolverState {
    data object HomeScreen : HomeState()
}

sealed class HomeEvent : RevolverEvent {
    data object GoToModal : HomeEvent()
    data object GoToAccount : HomeEvent()
}

private typealias HomeEmitter = Emitter<HomeState, RevolverEffect>

class HomeViewModel internal constructor(
    private val navigation: Navigation
) : RevolverViewModel<HomeEvent, HomeState, RevolverEffect>(HomeState.HomeScreen) {

    init {
        addEventHandler<HomeEvent.GoToModal>(::onGoToModal)
        addEventHandler<HomeEvent.GoToAccount>(::onGoToAccount)
        navigation.setCurrentRoute(route = RouteInstance.from(Route.Home))
    }

    private suspend fun onGoToModal(event: HomeEvent.GoToModal, emit: HomeEmitter) {
        navigation.pushAsModal(RouteInstance.from(Route.Modal, Route.Modal.Params("bar")))
    }

    private suspend fun onGoToAccount(event: HomeEvent.GoToAccount, emit: HomeEmitter) {
        navigation.push(RouteInstance.from(Route.Account))
    }
}