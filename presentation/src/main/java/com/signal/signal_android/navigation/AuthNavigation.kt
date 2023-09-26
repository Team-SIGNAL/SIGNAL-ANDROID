package com.signal.signal_android.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.signal.signal_android.feature.signin.SignIn

internal fun NavGraphBuilder.authNavigation() {
    navigation(
        route = NavigationRoute.Auth.route,
        startDestination = NavigationRoute.Auth.SignIn,
    ) {
        composable(NavigationRoute.Auth.SignIn) {
            SignIn()
        }

        composable(NavigationRoute.Auth.SignUp) {

        }
    }
}