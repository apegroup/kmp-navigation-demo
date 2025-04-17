package com.umain.navigation_demo

import com.umain.navigation_demo.guards.LoginRouteGuard
import com.umain.navigation_demo.utils.RouteSerializer
import com.umain.navigation_demo.utils.RouteSerializerImpl
import com.umain.navigation_demo.utils.buildAllScreenRouteGuards
import com.umain.navigation_demo.viewmodels.AccountViewModel
import com.umain.navigation_demo.viewmodels.HomeViewModel
import com.umain.navigation_demo.viewmodels.LoginViewModel
import com.umain.navigation_demo.viewmodels.ModalViewModel
import org.koin.core.module.Module
import org.koin.dsl.module


private val sharedModule = module {
    single<Navigation> { NavigationImpl(get(), buildAllScreenRouteGuards()) }
    single<NavigationObservable> { get<Navigation>() }
    factory<RouteSerializer> { RouteSerializerImpl() }
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