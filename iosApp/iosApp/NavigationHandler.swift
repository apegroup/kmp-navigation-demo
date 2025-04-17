//
//  NavigationHandler.swift
//  iosApp
//
//  Created by Cas van Luijtelaar on 25/03/2025.
//  Copyright © 2025 Umain. All rights reserved.
//
import SwiftUI
import Shared

class NavigationHandler: ObservableObject {
    @Published var path: [RouteInfo] = []
    @Published var root: RouteInfo
    @Published var navigationEvent: NavigationEventKs?
    @Published var sheetData: (active: Bool, destination: RouteInfo?, onDismiss: (() -> Void)?) = (false, nil, nil)

    private let navigation: NavigationObservable = KoinHelper().navigationObservable
    
    init(path: [RouteInfo] = [], root: RouteInfo) {
        self.path = path
        self.root = root
        
        self.sheetData.onDismiss = onSheetDismiss
        
        navigation.navigationObservable.watch { [weak self] event in
            self?.navigationEvent = NavigationEventKs(event)
        }
    }
    
    func handleNavigationEvent(navigationEvent: NavigationEventKs) {
        switch navigationEvent {
            
            // pop the top screen off the stack, if the top route is a modal
            // close the modal
            case .pop:
                if sheetData.destination != nil { onSheetDismiss() }
                guard path.count >= 1 else { return }
                path.removeLast()
            
            // pop all screens from the stack and close all modals
            case .popToRoot:
                path = []
                onSheetDismiss()
            
            // add new route to the stack
            case .push(let event):
                path.append(event.route)
            
            // push a modal onto the stack
            case .pushAsModal(let event):
                self.sheetData.destination = event.route
                self.sheetData.active = true
        }
    }
    
    func onSheetDismiss() {
        sheetData.active = false
        sheetData.destination = nil
    }
}



public enum NavigationEventKs {
    case pop
    case popToRoot
    case push(NavigationEvent.Push)
    case pushAsModal(NavigationEvent.PushAsModal)
    
    public init(_ obj: NavigationEvent) {
        if obj is NavigationEvent.Pop {
        self = .pop
      } else if obj is NavigationEvent.PopToRoot {
        self = .popToRoot
      } else if let obj = obj as? NavigationEvent.Push {
        self = .push(obj)
      } else if let obj = obj as? NavigationEvent.PushAsModal {
        self = .pushAsModal(obj)
      } else {
        fatalError("NavigationEventKs not synchronized with NavigationEvent class")
      }
    }
}
