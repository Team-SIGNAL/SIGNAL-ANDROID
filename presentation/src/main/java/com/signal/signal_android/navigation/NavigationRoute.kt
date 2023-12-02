package com.signal.signal_android.navigation

sealed class NavigationRoute(val route: String) {
    object Auth : NavigationRoute(route = "Auth") {
        const val SignIn = "signIn"
        const val SignUpUser = "signUpUser"
        const val SignUpAccount = "signUpAccount"
    }

    object User : NavigationRoute(route = "User") {
        const val Splash = "splash"
        const val Landing = "landing"
        const val DiagnosisLanding = "diagnosisLanding"
        const val Diagnosis = "diagnosis"
        const val DiagnosisComplete = "diagnosisComplete"
    }

    object Main : NavigationRoute(route = "Main") {
        const val Main = "main"
        const val Home = "home"
        const val Diary = "diary"
        const val Feed = "feed"
        const val FeedDetails = "feedDetails"
        const val Report = "report"
        const val CreatePost = "createPosts"
        const val Recommend = "recommend"
        const val MyPage = "myPage"
        const val CreateDiary = "createDiary"
        const val DiaryDetails = "diaryDetails"
        const val AllDiary = "allDiary"
        const val Reservation = "reservation"
        const val Hospital = "hospital"
        const val CreateReservation = "createReservation"
        const val MoreAchievement = "moreAchievement"
        const val Recommends = "recommends"
        const val RecommendDetails = "recommendDetails"
        const val CoinHistory = "coinHistory"
        const val EditProfile = "editProfile"
    }
}

object NavArgument {
    const val FeedId = "{feedId}"
    const val DiaryId = "{diaryId}"
    const val Category = "{category}"
    const val HospitalId = "{hospitalId}"
    const val ReservationId = "{reservationId}"
    const val RecommendType = "{recommendType}"
    const val RecommendId = "{recommendId}"
}
