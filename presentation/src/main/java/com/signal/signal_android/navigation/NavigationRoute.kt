package com.signal.signal_android.navigation

sealed class NavigationRoute(val route: String) {
    object Auth : NavigationRoute(route = "Auth") {
        const val SignIn = "signIn"
        const val SignUpUser = "signUpUser"
        const val SignUpAccount = "signUpAccount"
    }

    object User : NavigationRoute(route = "User") {
        const val Splash = "splash"
        const val Landing = "landing"
        const val MyPage = "myPage"
    }
}