package com.umain.navigation_demo.viewmodels

import com.umain.navigation_demo.Navigation
import com.umain.navigation_demo.models.Route
import com.umain.navigation_demo.models.RouteInstance
import com.umain.revolver.Emitter
import com.umain.revolver.RevolverEffect
import com.umain.revolver.RevolverEvent
import com.umain.revolver.RevolverState
import com.umain.revolver.RevolverViewModel


sealed class LoginState : RevolverState {
    data object LoginScreen : LoginState()
}

sealed class LoginEvent : RevolverEvent {
    data object LoginComplete : LoginEvent()
}

private typealias LoginEmitter = Emitter<LoginState, RevolverEffect>

class LoginViewModel internal constructor(
    private val navigation: Navigation
) : RevolverViewModel<LoginEvent, LoginState, RevolverEffect>(LoginState.LoginScreen) {

    init {
        addEventHandler<LoginEvent.LoginComplete>(::onLoginComplete)
    }

    private suspend fun onLoginComplete(event: LoginEvent.LoginComplete, emit: LoginEmitter) {
        navigation.push(RouteInstance.from(Route.Account))
    }
}