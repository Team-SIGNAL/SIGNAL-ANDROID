package com.signal.signal_android.designsystem.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.signal.signal_android.R
import com.signal.signal_android.navigation.NavigationRoute

sealed class NavigationItem(
    @DrawableRes val unselected: Int,
    @DrawableRes val selected: Int,
    @StringRes val label: Int,
    val route: String,
) {
    object Home : NavigationItem(
        unselected = R.drawable.ic_home_outlined,
        selected = R.drawable.ic_home_filled,
        label = R.string.home,
        route = NavigationRoute.Main.Home,
    )

    object Diary : NavigationItem(
        unselected = R.drawable.ic_diary_outlined,
        selected = R.drawable.ic_diary_filled,
        label = R.string.diary,
        route = NavigationRoute.Main.Diary,
    )

    object Feed : NavigationItem(
        unselected = R.drawable.ic_feed_outlined,
        selected = R.drawable.ic_feed_filled,
        label = R.string.feed,
        route = NavigationRoute.Main.Feed,
    )

    object Recommend : NavigationItem(
        unselected = R.drawable.ic_recommend_outlined,
        selected = R.drawable.ic_recommend_filled,
        label = R.string.recommend,
        route = NavigationRoute.Main.Recommend,
    )

    object MyPage : NavigationItem(
        unselected = R.drawable.ic_my_page_outlined,
        selected = R.drawable.ic_my_page_filled,
        label = R.string.my_page,
        route = NavigationRoute.Main.MyPage,
    )
}
