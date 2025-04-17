package com.umain.navigation_demo.viewmodels

import com.umain.navigation_demo.Navigation
import com.umain.navigation_demo.models.Route
import com.umain.navigation_demo.models.RouteInstance
import com.umain.revolver.Emitter
import com.umain.revolver.RevolverEffect
import com.umain.revolver.RevolverEvent
import com.umain.revolver.RevolverState
import com.umain.revolver.RevolverViewModel


sealed class AccountState : RevolverState {
    data object AccountScreen : AccountState()
}

sealed class AccountEvent : RevolverEvent {
    data object CloseAccountScreen : AccountEvent()
}

private typealias AccountEmitter = Emitter<AccountState, RevolverEffect>

class AccountViewModel internal constructor(
    private val navigation: Navigation
) : RevolverViewModel<AccountEvent, AccountState, RevolverEffect>(AccountState.AccountScreen) {

    init {
        addEventHandler<AccountEvent.CloseAccountScreen>(::onCloseAccountScreen)
        navigation.setCurrentRoute(RouteInstance.from(Route.Account))
    }

    private suspend fun onCloseAccountScreen(event: AccountEvent.CloseAccountScreen, emit: AccountEmitter) {
        navigation.popToRoot()
    }
}