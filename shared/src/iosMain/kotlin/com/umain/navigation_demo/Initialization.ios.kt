package com.umain.navigation_demo

import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.module.Module

internal actual fun initializeKoin(modules: List<Module>) {
    startKoin { loadKoinModules(modules) }
}
