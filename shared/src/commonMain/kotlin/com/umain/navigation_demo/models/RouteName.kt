package com.umain.navigation_demo.models

enum class RouteName {
    Home,
    Account,
    Login,
    Modal,
    ;

    /** get the route this [RouteName] belongs to */
    internal fun toRoute() = when (this) {
        Home -> Route.Home
        Account -> Route.Account
        Login -> Route.Login
        Modal -> Route.Modal
    }
}