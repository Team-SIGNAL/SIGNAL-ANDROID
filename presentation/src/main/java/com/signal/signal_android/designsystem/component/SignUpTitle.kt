package com.signal.signal_android.designsystem.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.foundation.Title

@Composable
internal fun ColumnScope.SignUpTitle() {
    Spacer(modifier = Modifier.height(66.dp))
    Title(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.Start),
        text = stringResource(id = R.string.sign_up),
    )
    Spacer(modifier = Modifier.height(60.dp))
}
