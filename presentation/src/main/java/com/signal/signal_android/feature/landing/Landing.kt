package com.signal.signal_android.feature.landing

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.button.SignalFilledButton
import com.signal.signal_android.designsystem.button.SignalOutlinedButton
import com.signal.signal_android.designsystem.component.Indicator
import com.signal.signal_android.designsystem.foundation.BodyLarge2

private const val PAGE_COUNT = 5

private val landingImages = listOf(
    R.drawable.ic_landing_diary,
    R.drawable.ic_landing_self_diagnosis,
    R.drawable.ic_landing_community,
    R.drawable.ic_landing_recommend_contents,
    R.drawable.ic_landing_reservation_clinic,
)

private val landingDescriptions = listOf(
    R.string.landing_description_diary,
    R.string.landing_description_self_diagnosis,
    R.string.landing_description_community,
    R.string.landing_description_recommend_contents,
    R.string.landing_description_reservation_clinic,
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun Landing() {

    val pagerState = rememberPagerState {
        landingImages.size
    }

    val onSignInClick: () -> Unit = {

    }

    val onSignUpClick: () -> Unit = {

    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        LandingPager(pagerState = pagerState)
        Spacer(modifier = Modifier.height(20.dp))
        PagerIndicator(currentPage = pagerState.currentPage)
        Spacer(modifier = Modifier.height(20.dp))
        BodyLarge2(text = stringResource(id = landingDescriptions[pagerState.currentPage]))
        Spacer(modifier = Modifier.weight(1f))
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            SignalFilledButton(
                text = stringResource(id = R.string.landing_sign_in),
                onClick = onSignInClick,
            )
            Spacer(modifier = Modifier.height(14.dp))
            SignalOutlinedButton(
                text = stringResource(id = R.string.landing_sign_up),
                onClick = onSignUpClick,
            )
        }
        Spacer(modifier = Modifier.height(44.dp))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LandingPager(pagerState: PagerState) {
    HorizontalPager(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        state = pagerState,
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(landingImages[it]),
                contentDescription = stringResource(id = R.string.landing_image),
            )
        }
    }
}

@Composable
private fun PagerIndicator(currentPage: Int) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items(PAGE_COUNT) {
            Indicator(it == currentPage)
        }
    }
}