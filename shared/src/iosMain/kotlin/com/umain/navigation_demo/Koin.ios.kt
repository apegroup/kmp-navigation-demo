package com.umain.navigation_demo

internal actual fun initializeKoin(modules: List<Module>) {
    startKoin { loadKoinModules(modules) }
}
