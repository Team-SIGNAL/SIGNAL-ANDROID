package com.signal.signal_android

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.signal.signal_android.navigation.NavigationRoute
import com.signal.signal_android.navigation.authNavigation
import com.signal.signal_android.navigation.mainNavigation
import com.signal.signal_android.navigation.userNavigation

@Composable
internal fun SignalApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavigationRoute.User.route,
    ) {
        userNavigation(
            moveToLanding = {
                navController.navigate(NavigationRoute.User.Landing) {
                    popUpTo(NavigationRoute.User.Splash) {
                        inclusive = true
                    }
                }
            },
            moveToSignIn = { navController.navigate(NavigationRoute.Auth.SignIn) },
            moveToSignUp = { navController.navigate(NavigationRoute.Auth.SignUpUser) },
            moveToMyPage = { navController.navigate(NavigationRoute.User.MyPage) },
        )
        authNavigation(
            moveToSignUp = { navController.navigate(NavigationRoute.Auth.SignUpUser) },
            moveToSignIn = { navController.navigate(NavigationRoute.Auth.SignIn) },
            moveToSignUpAccount = { navController.navigate(NavigationRoute.Auth.SignUpAccount) },
        )
        mainNavigation()
    }
}
