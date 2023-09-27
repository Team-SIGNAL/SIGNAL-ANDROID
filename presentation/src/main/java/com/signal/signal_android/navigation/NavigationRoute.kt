package com.signal.signal_android.navigation

sealed class NavigationRoute(val route: String) {
    object Auth : NavigationRoute(route = "Auth") {
        const val SignIn = "signIn"
        const val SignUpUser = "signUpUser"
    }

    object User : NavigationRoute(route = "User") {
        const val Splash = "splash"
        const val Landing = "landing"
    }
}