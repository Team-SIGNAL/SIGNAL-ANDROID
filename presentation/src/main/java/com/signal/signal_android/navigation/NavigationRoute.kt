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
        const val MyPage = "myPage"
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
    }
}

object NavArgument {
    const val FeedId = "{feedId}"
}
