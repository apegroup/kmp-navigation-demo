import SwiftUI
import Shared

@main
struct iOSApp: App {
    @StateObject var navigationHandler: NavigationHandler

    init() {
        InitializationKt.initializeSharedLibrary()
        _navigationHandler = StateObject(wrappedValue: .init())
    }

    var body: some Scene {
        WindowGroup {
            NavigationStack(path: $navigationHandler.path) {
                getView(route: navigationHandler.root)
                    .navigationDestination(for: RouteInfo.self) { route in
                        getView(route: route)
                    }
            }
            .sheet(
                isPresented: $navigationHandler.sheetData.active,
                onDismiss: navigationHandler.sheetData.onDismiss,
                content: {
                    if let destination = navigationHandler.sheetData.destination {
                        getView(route: destination)
                    }
                }
            )
            .onReceive(navigationHandler.$navigationEvent) {
                guard let navigationEvent = $0 else { return }
                navigationHandler.handleNavigationEvent(navigationEvent: navigationEvent)
            }
        }
    }
    
    @ViewBuilder
    func getView(route: RouteInfo) -> some View {
        switch route.name {
        case .home:
            HomeView(params: route.params)
        case .account:
            AccountView(params: route.params)
        case .login:
            LoginView(params: route.params)
        case .modal:
            ModalView(params: route.params)
        default:
            VStack {}
        }
    }
}
