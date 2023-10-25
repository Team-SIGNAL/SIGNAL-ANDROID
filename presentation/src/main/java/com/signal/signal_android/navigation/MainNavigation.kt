package com.signal.signal_android.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.signal.signal_android.feature.main.Main

internal fun NavGraphBuilder.mainNavigation(
    moveToSignIn: () -> Unit,
){
    navigation(
        startDestination = NavigationRoute.Main.Main,
        route = NavigationRoute.Main.route,
    ){
        composable(NavigationRoute.Main.Main){
            Main(
                moveToSignIn = moveToSignIn,
            )
        }
    }
}