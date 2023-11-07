package com.signal.signal_android.feature.diagnosis

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.component.Header
import com.signal.signal_android.designsystem.foundation.BodyLarge2
import com.signal.signal_android.designsystem.foundation.BodyStrong
import com.signal.signal_android.designsystem.foundation.SignalColor
import com.signal.signal_android.designsystem.foundation.Title
import com.signal.signal_android.designsystem.util.signalClickable

@Composable
internal fun Diagnosis(
    moveToBack: () -> Unit,
    moveToDiagnosisComplete: () -> Unit,
) {
    // TODO: 더미
    var count by remember { mutableIntStateOf(1) }
    val max = 5
    var question by remember { mutableStateOf("더미더미더미더미더미?") }

    var selected: Int? by remember { mutableStateOf(null) }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Header(
                title = stringResource(id = R.string.diagnosis_title),
                onClick = moveToBack,
            )
            Question(
                count = { count },
                question = { question },
            )
            Options(
                onSelect = { selected = it + 1 },
                selected = { selected },
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Progress(
            count = { count },
            max = max,
        )
        Buttons(
            mainText = stringResource(
                id = if (count != max) {
                    R.string.next
                } else {
                    R.string.diagnosis_complete
                },
            ),
            subText = stringResource(id = R.string.home_previous),
            onMainButtonClicked = {
                if (count == max) {
                    moveToDiagnosisComplete()
                } else {
                    count += 1
                    selected = null
                }
            },
            onSubButtonClicked = {
                count -= 1
                selected = null
            },
            mainEnabled = { selected != null },
            subEnabled = { count != 1 },
        )
    }
}

@Composable
private fun ColumnScope.Progress(
    count: () -> Int,
    max: Int,
) {
    LinearProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(6.dp)
            .clip(CircleShape),
        progress = (count() / max.toFloat()),
        color = SignalColor.Primary100,
    )
    BodyStrong(
        modifier = Modifier
            .align(Alignment.Start)
            .padding(
                top = 4.dp,
                bottom = 6.dp,
                start = 16.dp,
            ),
        text = "${count()}/$max",
        color = SignalColor.Primary100,
    )
}

@Composable
private fun Question(
    count: () -> Int,
    question: () -> String,
) {
    Title(
        modifier = Modifier.padding(top = 14.dp),
        text = count().toString(),
        color = SignalColor.Primary100,
    )
    BodyLarge2(text = question())
}

val options = listOf(
    R.string.diagnosis_not_very_much,
    R.string.diagnosis_not_good,
    R.string.diagnosis_good,
    R.string.diagnosis_very_much,
)

@Composable
private fun Options(
    onSelect: (Int) -> Unit,
    selected: () -> Int?,
) {
    val selected = selected()

    LazyColumn(
        modifier = Modifier.padding(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 12.dp),
    ) {
        itemsIndexed(options) { index, option ->
            Option(
                text = stringResource(id = option),
                onClick = { onSelect(index) },
                isSelected = {
                    if (selected == null) {
                        null
                    } else {
                        selected == index + 1
                    }
                },
            )
        }
    }
}

@Composable
private fun Option(
    text: String,
    onClick: () -> Unit,
    isSelected: () -> Boolean?,
) {
    val textColor by animateColorAsState(
        targetValue = when (isSelected()) {
            true -> SignalColor.Primary100
            false -> SignalColor.Gray400
            else -> SignalColor.Gray600
        },
        label = stringResource(id = R.string.diagnosis_label_color_text_animation_option),
    )

    val outlineColor by animateColorAsState(
        targetValue = when (isSelected()) {
            true -> SignalColor.Primary100
            false -> SignalColor.Gray300
            else -> SignalColor.Gray400
        },
        label = stringResource(id = R.string.diagnosis_label_color_outline_animation_option),
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .signalClickable(
                rippleEnabled = true,
                onClick = onClick,
            )
            .border(
                width = 1.dp,
                color = outlineColor,
                shape = RoundedCornerShape(16.dp),
            )
            .background(
                color = SignalColor.White,
                shape = RoundedCornerShape(16.dp),
            )
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        BodyStrong(
            text = text,
            color = textColor,
        )
    }
}
