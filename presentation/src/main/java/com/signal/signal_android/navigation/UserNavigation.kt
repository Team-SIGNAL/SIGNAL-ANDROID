package com.signal.signal_android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.signal.signal_android.feature.landing.Landing
import com.signal.signal_android.feature.mypage.MyPage
import com.signal.signal_android.feature.mypage.MyPageViewModel
import com.signal.signal_android.feature.signup.SignUpViewModel
import com.signal.signal_android.feature.splash.Splash
import org.koin.androidx.compose.koinViewModel

private val myPageViewModel: MyPageViewModel
    @Composable get() = koinViewModel()

internal fun NavGraphBuilder.userNavigation(
    moveToLanding: () -> Unit,
    moveToSignIn: () -> Unit,
    moveToSignUp: () -> Unit,
) {
    navigation(
        route = NavigationRoute.User.route,
        startDestination = NavigationRoute.User.Splash,
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
    }
}
