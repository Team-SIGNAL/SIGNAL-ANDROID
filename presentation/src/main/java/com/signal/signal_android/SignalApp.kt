package com.signal.signal_android

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.signal.signal_android.feature.signup.SignUpViewModel
import com.signal.signal_android.navigation.NavigationRoute
import com.signal.signal_android.navigation.authNavigation
import com.signal.signal_android.navigation.mainNavigation
import com.signal.signal_android.navigation.userNavigation
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun SignalApp() {
    val navController = rememberNavController()

    val moveToBack: () -> Unit = {
        navController.popBackStack()
    }

    val signUpViewModel: SignUpViewModel = koinViewModel()

    NavHost(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
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
            moveToSignIn = {
                navController.navigate(NavigationRoute.Auth.SignIn) {
                    popUpTo(NavigationRoute.User.Landing) {
                        inclusive = true
                    }
                }
            },
            moveToSignUp = { navController.navigate(NavigationRoute.Auth.SignUpUser) },
            moveToDiagnosis = { navController.navigate(NavigationRoute.User.Diagnosis) },
            moveToBack = { navController.popBackStack() },
            moveToDiagnosisComplete = {
                navController.navigate(NavigationRoute.User.DiagnosisComplete) {
                    popUpTo(NavigationRoute.User.DiagnosisLanding) {
                        inclusive = true
                    }
                }
            },
            moveToMain = {
                navController.navigate(NavigationRoute.Main.Main) {
                    popUpTo(NavigationRoute.User.DiagnosisComplete) {
                        inclusive = true
                    }
                }
            }
        )
        authNavigation(
            signUpViewModel = signUpViewModel,
            moveToSignUp = { navController.navigate(NavigationRoute.Auth.SignUpUser) },
            moveToSignIn = { navController.navigate(NavigationRoute.Auth.SignIn) },
            moveToSignUpAccount = { navController.navigate(NavigationRoute.Auth.SignUpAccount) },
            moveToBack = { navController.popBackStack() },
            moveToMain = {
                navController.navigate(NavigationRoute.Main.Main) {
                    popUpTo(NavigationRoute.Auth.SignIn) {
                        inclusive = true
                    }
                }
            },
        )
        mainNavigation(
            moveToSignIn = {
                navController.navigate(NavigationRoute.Auth.SignIn) {
                    popUpTo(NavigationRoute.Main.MyPage) {
                        inclusive = true
                    }
                }
            },
            moveToLanding = {
                navController.navigate(NavigationRoute.User.Landing) {
                    popUpTo(NavigationRoute.Main.MyPage) {
                        inclusive = true
                    }
                }
            },
            moveToFeedDetails = {
                navController.navigate("${NavigationRoute.Main.FeedDetails}/${it}")
            },
            moveToBack = moveToBack,
            moveToCreatePost = {
                navController.navigate("${NavigationRoute.Main.CreatePost}/${it ?: " "}")
            },
            moveToReport = {
                navController.navigate(NavigationRoute.Main.Report)
            },
            moveToDiagnosisLanding = {
                navController.navigate(NavigationRoute.User.DiagnosisLanding)
            },
            moveToCreateDiary = {
                navController.navigate(NavigationRoute.Main.CreateDiary)
            },
            moveToDiaryDetails = {
                navController.navigate("${NavigationRoute.Main.DiaryDetails}/${it}")
            },
            moveToAllDiary = {
                navController.navigate(NavigationRoute.Main.AllDiary)
            },
            moveToReservation = {
                navController.navigate(NavigationRoute.Main.Reservation)
            },
            moveToHospital = {
                navController.navigate(NavigationRoute.Main.Hospital)
            },
            moveToCreateReservation = {
                navController.navigate("${NavigationRoute.Main.CreateReservation}/${it}")
            },
            moveToMoreAchievement = {
                navController.navigate(NavigationRoute.Main.MoreAchievement)
            },
            moveToRecommends = {
                navController.navigate("${NavigationRoute.Main.Recommends}/${it}")
            },
            moveToRecommendDetails = {
                navController.navigate("${NavigationRoute.Main.RecommendDetails}/${it}")
            },
            moveToCoinHistory = {
                navController.navigate(NavigationRoute.Main.CoinHistory)
            },
            moveToEditProfile = {
                navController.navigate(NavigationRoute.Main.EditProfile)
            },
        )
    }
}
