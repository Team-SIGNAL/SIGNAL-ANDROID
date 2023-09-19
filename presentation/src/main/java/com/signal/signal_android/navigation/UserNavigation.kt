package com.signal.signal_android.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

internal fun NavGraphBuilder.userNavigation() {
    navigation(
        route = NavigationRoute.User.route,
        startDestination = NavigationRoute.User.Splash,
    ){
        composable(NavigationRoute.User.Splash){

        }
    }
}