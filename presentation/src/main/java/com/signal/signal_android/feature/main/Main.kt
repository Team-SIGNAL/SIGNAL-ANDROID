package com.signal.signal_android.feature.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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
import com.signal.signal_android.feature.mypage.MyPage
import com.signal.signal_android.navigation.NavigationRoute
import java.util.UUID

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun Main(
    moveToSignIn: () -> Unit,
    moveToLanding: () -> Unit,
    moveToFeedDetails: (feedId: UUID) -> Unit,
    moveToCreatePost: (feedId: UUID?) -> Unit,
    moveToReport: () -> Unit,
    moveToDiagnosisLanding: () -> Unit,
    moveToCreateDiary: () -> Unit,
    moveToDiaryDetails: (diaryId: UUID) -> Unit,
    moveToAllDiary: () -> Unit,
    moveToReservation: () -> Unit,
    moveToMoreAchievement: () -> Unit,
) {
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
                },
            )
        },
    ) {
        NavHost(
            modifier = Modifier.padding(bottom = it.calculateBottomPadding()),
            navController = navController,
            startDestination = NavigationRoute.Main.Home,
        ) {
            composable(NavigationRoute.Main.Home) {
                Home(
                  moveToReservation = moveToReservation,
                  moveToDiagnosisLanding = moveToDiagnosisLanding,
                )
            }

            composable(NavigationRoute.Main.Diary) {
                Diary(
                    moveToCreateDiary = moveToCreateDiary,
                    moveToDiaryDetails = moveToDiaryDetails,
                    moveToAllDiary = moveToAllDiary,
                )
            }

            composable(NavigationRoute.Main.Feed) {
                Feed(
                    moveToFeedDetails = moveToFeedDetails,
                    moveToCreatePost = moveToCreatePost,
                    moveToReport = moveToReport,
                )
            }

            composable(NavigationRoute.Main.Recommend) {
                Recommend()
            }

            composable(NavigationRoute.Main.MyPage) {
                MyPage(
                    moveToSignIn = moveToSignIn,
                    moveToLanding = moveToLanding,
                    moveToMoreAchievement = moveToMoreAchievement
                )
            }
        }
    }
}
