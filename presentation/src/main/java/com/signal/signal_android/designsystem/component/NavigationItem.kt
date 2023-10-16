package com.signal.signal_android.designsystem.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.signal.signal_android.R
import com.signal.signal_android.navigation.NavigationRoute

sealed class NavigationItem(
    @DrawableRes val drawable: Int,
    @StringRes val label: Int,
    val route: String,
) {
    object Home: NavigationItem(
        drawable = R.drawable.ic_home,
        label = R.string.home,
        route = NavigationRoute.Main.Home,
    )

    object Diary: NavigationItem(
        drawable = R.drawable.ic_diary,
        label = R.string.diary,
        route = NavigationRoute.Main.Diary,
    )

    object Feed: NavigationItem(
        drawable = R.drawable.ic_feed,
        label = R.string.feed,
        route = NavigationRoute.Main.Feed,
    )

    object Recommend: NavigationItem(
        drawable = R.drawable.ic_recommend,
        label = R.string.recommend,
        route = NavigationRoute.Main.Recommend,
    )

    object MyPage: NavigationItem(
        drawable = R.drawable.ic_my_page,
        label = R.string.my_page,
        route = NavigationRoute.Main.MyPage,
    )
}