package com.umain.navigation_demo.models

class NavigationEvent {


    /**
     * base class for all Navigation events
     */
    sealed class NavigationEvent {

        /**
         * Push the provided [route] onto the stack
         * @property route the route to push onto the stack
         */
        data class Push(
            val route: RouteInfo,
        ) : NavigationEvent()

        /**
         * Push the provided [route] onto the stack as a modal
         * @property route the route to push onto the stack
         * @property modalType what type of modal overlay should be used (iOS only)
         */
        data class PushAsModal(
            val route: RouteInfo,
            val modalType: ModalType = ModalType.Modal,
        ) : NavigationEvent() {
            enum class ModalType {
                Modal,
                FullScreenModal,
                Drawer,
                QuickAction,
            }
        }

        /**
         * set the provided [route] as the root screen for the stack
         * @property route the route to push onto the stack
         */
        data class SetRootScreen(
            val route: RouteInfo,
        ) : NavigationEvent()

        /**
         * pop the top route from the stack
         */
        data object Pop : NavigationEvent()

        /**
         * pop all routes from the stack
         */
        data object PopToRoot : NavigationEvent()

        /**
         * pop all modals from the stack
         */
        data object Dismiss : NavigationEvent()
    }


}