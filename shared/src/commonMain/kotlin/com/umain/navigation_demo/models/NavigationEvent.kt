package com.umain.navigation_demo.models

/** base class for all Navigation event */
sealed class NavigationEvent {

    /** push normal screen on the stack */
    data class Push(
        val route: RouteInfo,
    ) : NavigationEvent()

    /** push this screen, but as an overlayed modal */
    data class PushAsModal(
        val route: RouteInfo,
        val modalType: ModalType = ModalType.Modal,
    ) : NavigationEvent() {
        enum class ModalType { Modal, HalfModal }
    }

    /** pop the top route from the stack */
    data object Pop : NavigationEvent()

    /** pop all routes from the stack */
    data object PopToRoot : NavigationEvent()
}
