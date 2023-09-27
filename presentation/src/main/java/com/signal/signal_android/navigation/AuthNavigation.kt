package com.signal.signal_android.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.signal.signal_android.feature.signin.SignIn
import com.signal.signal_android.feature.signup.SignUpUser

internal fun NavGraphBuilder.authNavigation(
    moveToSignUp: () -> Unit,
    moveToSignIn: () -> Unit,
) {
    navigation(
        route = NavigationRoute.Auth.route,
        startDestination = NavigationRoute.Auth.SignUpUser,
    ) {
        composable(NavigationRoute.Auth.SignIn) {
            SignIn(
                moveToSignUp = moveToSignUp,
            )
        }

        composable(NavigationRoute.Auth.SignUpUser) {
            SignUpUser(
                moveToSignIn = moveToSignIn,
            )
        }
    }
}