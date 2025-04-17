//
//  AccountView.swift
//  iosApp
//
//  Created by Cas van Luijtelaar on 2025-04-17.
//  Copyright © 2025 orgName. All rights reserved.
//
import SwiftUI
import Shared

struct AccountView: View {
    private var viewModel = KoinHelper().accountViewModel
    private let routeParams: String

    init(params: String) {
        self.routeParams = params
    }
    
    var body: some View {
        VStack {
            Text("account view")
            
            Button(action: {
                viewModel.emit(event: .CloseAccountScreen())
            }) {
                Text("back to home")
            }
        }
    }
}

