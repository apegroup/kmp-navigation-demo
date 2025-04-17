//
//  HomeView.swift
//  iosApp
//
//  Created by Cas van Luijtelaar on 25/03/2025.
//  Copyright © 2025 orgName. All rights reserved.
//
import SwiftUI
import Shared

struct HomeView: View {
    private var viewModel = KoinHelper().homeViewModel
    private let routeParams: String

    init(params: String) {
        self.routeParams = params
    }
    
    var body: some View {
        VStack {
            Text("Home view ")
            
            Button(action: {
                viewModel.emit(event: .GoToAccount())
            }) {
                Text("Account")
            }
            
            Button(action: {
                viewModel.emit(event: .GoToModal())
            }) {
                Text("Modal")
            }
        }
    }
}
