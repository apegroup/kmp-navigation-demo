package com.umain.navigation_demo.models

/**
 * base class for all Navigation events
 */
sealed class NavigationEvent {

    data class Push(
        val route: RouteInfo,
    ) : NavigationEvent()

    data class PushAsModal(
        val route: RouteInfo,
        val modalType: ModalType = ModalType.Modal,
    ) : NavigationEvent() {
        enum class ModalType {
            Modal,
            // FullScreenModal,
            // Drawer,
            // QuickAction,
        }
    }

    /**
     * pop the top route from the stack
     */
    data object Pop : NavigationEvent()

    /**
     * pop all routes from the stack
     */
    data object PopToRoot : NavigationEvent()
}
