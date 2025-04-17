package com.umain.navigation_demo.viewmodels

import com.umain.navigation_demo.Navigation
import com.umain.navigation_demo.models.Route
import com.umain.navigation_demo.models.RouteInstance
import com.umain.navigation_demo.utils.RouteSerializer
import com.umain.revolver.Emitter
import com.umain.revolver.RevolverEffect
import com.umain.revolver.RevolverEvent
import com.umain.revolver.RevolverState
import com.umain.revolver.RevolverViewModel


sealed class ModalState : RevolverState {
    data object Loading : ModalState()
    data class ModalScreen(val data: String) : ModalState()
}

sealed class ModalEvent : RevolverEvent {
    data class ViewAppeared(val params: String) : ModalEvent()
    data object CloseModal : ModalEvent()
}

private typealias ModalEmitter = Emitter<ModalState, RevolverEffect>

class ModalViewModel internal constructor(
    private val navigation: Navigation,
    private val routeSerializer: RouteSerializer
) : RevolverViewModel<ModalEvent, ModalState, RevolverEffect>(ModalState.Loading) {

    private lateinit var params: Route.Modal.Params

    init {
        addEventHandler<ModalEvent.ViewAppeared>(::onViewAppeared)
        addEventHandler<ModalEvent.CloseModal>(::onCloseModal)
    }

    private fun onViewAppeared(event: ModalEvent.ViewAppeared, emit: ModalEmitter) {
        params = routeSerializer.deserializeRouteParams(Route.Modal, event.params)
        navigation.setCurrentRoute(route = RouteInstance.from(Route.Modal, params))

        emit.state(ModalState.ModalScreen(params.foo))
    }

    private suspend fun onCloseModal(event: ModalEvent.CloseModal, emit: ModalEmitter) {
        navigation.pop()
    }
}