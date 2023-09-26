package com.signal.signal_android.feature.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.signal.signal_android.R
import kotlinx.coroutines.delay

@Composable
internal fun Splash(
    moveToLanding: () -> Unit,
) {
    // TODO 자동 로그인 로직 반영
    LaunchedEffect(Unit) {
        delay(2000)
        moveToLanding()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_signal_logo),
            contentDescription = stringResource(id = R.string.app_logo),
        )
    }
}