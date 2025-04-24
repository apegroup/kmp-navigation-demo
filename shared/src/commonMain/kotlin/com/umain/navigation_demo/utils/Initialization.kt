package com.umain.navigation_demo.utils

import com.umain.navigation_demo.Navigation
import com.umain.navigation_demo.NavigationImpl
import com.umain.navigation_demo.NavigationObservable
import com.umain.navigation_demo.guards.LoginRouteGuard
import com.umain.navigation_demo.viewmodels.AccountViewModel
import com.umain.navigation_demo.viewmodels.HomeViewModel
import com.umain.navigation_demo.viewmodels.LoginViewModel
import com.umain.navigation_demo.viewmodels.ModalViewModel
import org.koin.core.module.Module
import org.koin.dsl.module


private val sharedModule = module {
    single<Navigation> { NavigationImpl(get(), buildAllScreenRouteGuards()) }
    factory<NavigationObservable> { get<Navigation>() as NavigationObservable }
    factory<RouteSerializer> { RouteSerializerImpl() }
    factory<DeeplinkHandler> { DeeplinkHandlerImpl() }
    factory { LoginRouteGuard(get()) }
    factory { HomeViewModel(get()) }
    factory { AccountViewModel(get()) }
    factory { ModalViewModel(get(), get()) }
    factory { LoginViewModel(get()) }
}

internal expect fun initializeKoin(modules: List<Module>)


/**
 * main entrypoint to the shared library
 * to be called by clients on initialization
 */
fun initializeSharedLibrary() {
    initializeKoin(listOf(sharedModule))
}