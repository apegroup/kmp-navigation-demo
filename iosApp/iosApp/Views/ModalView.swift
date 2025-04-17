//
//  ModalView.swift
//  iosApp
//
//  Created by Cas van Luijtelaar on 2025-04-17.
//  Copyright © 2025 orgName. All rights reserved.
//
import SwiftUI
import Shared

struct ModalView: View {
    private var viewModel = KoinHelper().modalViewModel
    private let routeParams: String

    init(params: String) {
        self.routeParams = params
        viewModel.emit(event: .ViewAppeared(params: params))
    }
    
    var body: some View {
        VStack {
            Text("ModalView")
            
            Button(action: {
                viewModel.emit(event: .CloseModal())
            }) {
                Text("back to home")
            }
        }
    }
}

