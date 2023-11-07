package com.signal.signal_android.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.signal.signal_android.feature.diagnosis.DiagnosisLanding
import com.signal.signal_android.feature.landing.Landing
import com.signal.signal_android.feature.splash.Splash


internal fun NavGraphBuilder.userNavigation(
    moveToLanding: () -> Unit,
    moveToSignIn: () -> Unit,
    moveToSignUp: () -> Unit,
) {
    navigation(
        route = NavigationRoute.User.route,
        startDestination = NavigationRoute.User.DiagnosisLanding,
    ) {
        composable(NavigationRoute.User.Splash) {
            Splash(moveToLanding = moveToLanding)
        }

        composable(NavigationRoute.User.Landing) {
            Landing(
                moveToSignIn = moveToSignIn,
                moveToSignUp = moveToSignUp,
            )
        }

        composable(NavigationRoute.User.DiagnosisLanding) {
            DiagnosisLanding()
        }
    }
}
