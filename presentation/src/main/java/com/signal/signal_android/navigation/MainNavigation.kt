package com.signal.signal_android.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.signal.domain.enums.Category
import com.signal.signal_android.feature.achievement.MoreAchievements
import com.signal.signal_android.feature.coin.CoinHistory
import com.signal.signal_android.feature.main.Main
import com.signal.signal_android.feature.main.diary.AllDiary
import com.signal.signal_android.feature.main.diary.CreateDiary
import com.signal.signal_android.feature.main.diary.DiaryDetail
import com.signal.signal_android.feature.main.feed.CreatePost
import com.signal.signal_android.feature.main.feed.FeedDetails
import com.signal.signal_android.feature.main.feed.Report
import com.signal.signal_android.feature.main.mypage.EditProfile
import com.signal.signal_android.feature.main.recommend.RecommendDetails
import com.signal.signal_android.feature.main.recommend.Recommends
import com.signal.signal_android.feature.reservation.CreateReservation
import com.signal.signal_android.feature.reservation.Hospital
import com.signal.signal_android.feature.reservation.Reservation
import java.util.UUID

internal fun NavGraphBuilder.mainNavigation(
    moveToSignIn: () -> Unit,
    moveToLanding: () -> Unit,
    moveToFeedDetails: (feedId: UUID) -> Unit,
    moveToBack: () -> Unit,
    moveToCreatePost: (feedId: UUID?) -> Unit,
    moveToDiagnosisLanding: () -> Unit,
    moveToCreateDiary: () -> Unit,
    moveToDiaryDetails: (diaryId: UUID) -> Unit,
    moveToAllDiary: () -> Unit,
    moveToReservation: () -> Unit,
    moveToHospital: () -> Unit,
    moveToCreateReservation: (hospitalId: UUID) -> Unit,
    moveToMoreAchievement: () -> Unit,
    moveToRecommends: (recommendType: String) -> Unit,
    moveToRecommendDetails: (recommendId: UUID) -> Unit,
    moveToCoinHistory: () -> Unit,
    moveToEditProfile: () -> Unit,
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
                moveToDiagnosisLanding = moveToDiagnosisLanding,
                moveToCreateDiary = moveToCreateDiary,
                moveToDiaryDetails = moveToDiaryDetails,
                moveToAllDiary = moveToAllDiary,
                moveToReservation = moveToReservation,
                moveToMoreAchievement = moveToMoreAchievement,
                moveToRecommends = moveToRecommends,
                moveToCoinHistory = moveToCoinHistory,
                moveToEditProfile = moveToEditProfile,
            )
        }

        composable(
            route = "${NavigationRoute.Main.FeedDetails}/${NavArgument.FeedId}",
            arguments = listOf(
                navArgument("feedId") { type = NavType.StringType },
            ),
        ) {
            FeedDetails(
                feedId = UUID.fromString(it.arguments?.getString("feedId")),
                moveToBack = moveToBack,
                moveToCreatePost = moveToCreatePost,
            )
        }

        composable(
            route = "${NavigationRoute.Main.DiaryDetails}/${NavArgument.DiaryId}",
            arguments = listOf(
                navArgument("diaryId") { type = NavType.StringType },
            ),
        ) {
            DiaryDetail(
                diaryId = (UUID.fromString(it.arguments?.getString("diaryId"))
                    ?: UUID.randomUUID()),
                moveToBack = moveToBack,
            )
        }

        composable(NavigationRoute.Main.AllDiary) {
            AllDiary(
                moveToDiaryDetails = moveToDiaryDetails,
                moveToBack = moveToBack,
            )
        }

        composable(
            route = "${NavigationRoute.Main.CreatePost}/${NavArgument.FeedId}",
            arguments = listOf(
                navArgument("feedId") { type = NavType.StringType },
            ),
        ) {
            CreatePost(
                moveToBack = moveToBack,
                feedId = if (!it.arguments?.getString("feedId").isNullOrBlank()) UUID.fromString(
                    it.arguments?.getString(
                        "feedId",
                    )
                )
                else null,
            )
        }

        composable(NavigationRoute.Main.Report) {
            Report(moveToBack = moveToBack)
        }

        composable(NavigationRoute.Main.CreateDiary) {
            CreateDiary(moveToBack = moveToBack)
        }

        composable(NavigationRoute.Main.Reservation) {
            Reservation(
                moveToBack = moveToBack,
                moveToCreateReservation = moveToHospital,
            )
        }

        composable(NavigationRoute.Main.Hospital) {
            Hospital(
                moveToBack = moveToBack,
                moveToCreateReservation = moveToCreateReservation,
            )
        }

        composable(
            route = "${NavigationRoute.Main.CreateReservation}/${NavArgument.HospitalId}",
            arguments = listOf(navArgument("hospitalId") { type = NavType.StringType })
        ) {
            CreateReservation(
                moveToBack = moveToBack,
                moveToReservation = moveToReservation,
                hospitalId = UUID.fromString(it.arguments?.getString("hospitalId"))
                    ?: UUID.randomUUID(),
            )
        }

        composable(NavigationRoute.Main.MoreAchievement) {
            MoreAchievements(moveToBack = moveToBack)
        }

        composable(
            route = "${NavigationRoute.Main.Recommends}/${NavArgument.RecommendType}",
            arguments = listOf(navArgument("recommendType") { NavType.StringType }),
        ) {
            val category = it.arguments?.getString("category") ?: Category.MUSIC.toString()

            Recommends(
                moveToRecommendDetails = moveToRecommendDetails,
                moveToBack = moveToBack,
                category = Category.valueOf(category),
            )
        }

        composable(
            route = "${NavigationRoute.Main.RecommendDetails}/${NavArgument.RecommendId}",
            arguments = listOf(
                navArgument("recommendId") { NavType.StringType },
            ),
        ) {
            val recommendId = it.arguments?.getString("recommendId")

            RecommendDetails(
                moveToBack = moveToBack,
                recommendId = UUID.fromString(recommendId),
            )
        }

        composable(NavigationRoute.Main.CoinHistory) {
            CoinHistory(moveToBack = moveToBack)
        }

        composable(NavigationRoute.Main.EditProfile) {
            EditProfile(moveToBack = moveToBack)
        }
    }
}
