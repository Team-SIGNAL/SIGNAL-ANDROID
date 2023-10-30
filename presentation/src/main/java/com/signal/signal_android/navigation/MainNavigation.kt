package com.signal.signal_android.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.signal.signal_android.feature.main.Main
import com.signal.signal_android.feature.main.feed.CreatePost
import com.signal.signal_android.feature.main.feed.FeedDetails

internal fun NavGraphBuilder.mainNavigation(
    moveToSignIn: () -> Unit,
    moveToLanding: () -> Unit,
    moveToFeedDetails: (feedId: Long) -> Unit,
    moveToBack: () -> Unit,
    moveToCreatePost: () -> Unit,
) {
    navigation(
        startDestination = NavigationRoute.Main.Main,
        route = NavigationRoute.Main.route,
    ) {
        composable(NavigationRoute.Main.Main) {
            Main(
                moveToSignIn = moveToSignIn,
                moveToLanding = moveToLanding,
                moveToFeedDetails = moveToFeedDetails,
                moveToCreatePost = moveToCreatePost,
            )
        }

        composable(
            route = "${NavigationRoute.Main.FeedDetails}/${NavArgument.FeedId}",
            arguments = listOf(
                navArgument("feedId") { type = NavType.LongType },
            )
        ) {
            FeedDetails(
                feedId = it.arguments?.getLong("feedId") ?: 0L,
                moveToFeedDetails = moveToFeedDetails,
                moveToBack = moveToBack,
            )
        }

        composable(NavigationRoute.Main.CreatePost) {
            CreatePost(moveToBack = moveToBack)
        }
    }
}