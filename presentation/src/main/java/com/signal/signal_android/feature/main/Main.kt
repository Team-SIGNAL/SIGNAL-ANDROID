package com.signal.signal_android.feature.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.signal.signal_android.designsystem.component.SignalBottomBar
import com.signal.signal_android.feature.main.diary.Diary
import com.signal.signal_android.feature.main.feed.Feed
import com.signal.signal_android.feature.main.home.Home
import com.signal.signal_android.feature.main.recommend.Recommend
import com.signal.signal_android.navigation.NavigationRoute

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun Main() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            SignalBottomBar(
                currentRoute = navBackStackEntry?.destination?.route,
                navigateToTab = {
                    navController.navigate(it) {
                        popUpTo(it) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = NavigationRoute.Main.Home,
        ) {
            composable(NavigationRoute.Main.Home){
                Home()
            }

            composable(NavigationRoute.Main.Diary){
                Diary()
            }

            composable(NavigationRoute.Main.Feed){
                Feed()
            }

            composable(NavigationRoute.Main.Recommend){
                Recommend()
            }

            composable(NavigationRoute.Main.MyPage){
                // TODO mypage
            }
        }
    }
}