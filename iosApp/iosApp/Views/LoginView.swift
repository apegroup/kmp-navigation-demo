//
//  LoginView.swift
//  iosApp
//
//  Created by Cas van Luijtelaar on 2025-04-17.
//  Copyright © 2025 orgName. All rights reserved.
//
import SwiftUI
import Shared

struct LoginView: View {
    private var viewModel = KoinHelper().loginViewModel
    private let routeParams: String

    init(params: String) {
        self.routeParams = params
    }
    
    var body: some View {
        VStack {
            Text("please login")
            
            Button(action: {
                viewModel.emit(event: .LoginComplete())
            }) {
                Text("login")
            }
        }
    }
}

