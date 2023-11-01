package com.signal.signal_android.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.signal.signal_android.feature.signin.SignIn
import com.signal.signal_android.feature.signup.SignUpAccount
import com.signal.signal_android.feature.signup.SignUpUser
import com.signal.signal_android.feature.signup.SignUpViewModel

internal fun NavGraphBuilder.authNavigation(
    signUpViewModel: SignUpViewModel,
    moveToSignUp: () -> Unit,
    moveToSignIn: () -> Unit,
    moveToSignUpAccount: () -> Unit,
    moveToBack: () -> Unit,
    moveToMain: () -> Unit,
) {
    navigation(
        route = NavigationRoute.Auth.route,
        startDestination = NavigationRoute.Auth.SignUpUser,
    ) {
        composable(NavigationRoute.Auth.SignIn) {
            SignIn(
                moveToSignUp = moveToSignUp,
                moveToMain = moveToMain,
            )
        }

        composable(NavigationRoute.Auth.SignUpUser) {
            SignUpUser(
                moveToSignIn = moveToSignIn,
                moveToSignUpAccount = moveToSignUpAccount,
                signUpViewModel = signUpViewModel,
            )
        }

        composable(NavigationRoute.Auth.SignUpAccount) {
            SignUpAccount(
                moveToSignIn = moveToSignIn,
                moveToBack = moveToBack,
                signUpViewModel = signUpViewModel,
            )
        }
    }
}
