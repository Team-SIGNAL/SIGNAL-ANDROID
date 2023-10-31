package com.signal.signal_android.designsystem.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.signal.signal_android.designsystem.foundation.Body2
import com.signal.signal_android.designsystem.foundation.SignalColor

@Composable
internal fun SignalBottomBar(
    currentRoute: String?,
    navigateToTab: (route: String) -> Unit,
) {
    val tabs = listOf(
        NavigationItem.Home,
        NavigationItem.Diary,
        NavigationItem.Feed,
        NavigationItem.Recommend,
        NavigationItem.MyPage,
    )

    BottomNavigation(
        modifier = Modifier
            .shadow(elevation = 24.dp)
            .graphicsLayer(
                clip = true,
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                ),
            ),
        backgroundColor = SignalColor.Gray100,
    ) {
        tabs.forEach {
            val selected = currentRoute == it.route

            val tint by animateColorAsState(
                targetValue = if (selected) {
                    SignalColor.Gray700
                } else {
                    SignalColor.Gray500
                },
                label = "",
            )

            BottomNavigationItem(
                selected = selected,
                onClick = {
                    navigateToTab(it.route)
                },
                icon = {
                    Icon(
                        modifier = Modifier.padding(bottom = 4.dp),
                        painter = painterResource(
                            id = if (selected) {
                                it.selected
                            } else {
                                it.unselected
                            },
                        ),
                        contentDescription = null,
                        tint = tint,
                    )
                },
                label = {
                    Body2(
                        text = stringResource(id = it.label),
                        color = tint,
                    )
                },
            )
        }
    }
}
