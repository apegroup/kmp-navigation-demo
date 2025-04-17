package com.umain.navigation_demo

import com.umain.navigation_demo.viewmodels.AccountViewModel
import com.umain.navigation_demo.viewmodels.HomeViewModel
import com.umain.navigation_demo.viewmodels.LoginViewModel
import com.umain.navigation_demo.viewmodels.ModalViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class KoinHelper : KoinComponent {
    val navigationObservable: NavigationObservable by inject()
    val homeViewModel: HomeViewModel by inject()
    val accountViewModel: AccountViewModel by inject()
    val modalViewModel: ModalViewModel by inject()
    val loginViewModel: LoginViewModel by inject()
}