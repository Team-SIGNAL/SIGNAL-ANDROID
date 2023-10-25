package com.signal.signal_android.feature.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.foundation.Body
import com.signal.signal_android.designsystem.foundation.Body2
import com.signal.signal_android.designsystem.foundation.BodyLarge
import com.signal.signal_android.designsystem.foundation.BodyStrong
import com.signal.signal_android.designsystem.foundation.SignalColor
import com.signal.signal_android.designsystem.foundation.SubTitle
import com.signal.signal_android.designsystem.util.signalClickable
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun MyPage(
    moveToSignIn: () -> Unit,
    myPageViewModel: MyPageViewModel = koinViewModel(),
) {
    var showSecessionDialog by remember { mutableStateOf(false) }
    val onSecessionCancelClick: () -> Unit = { showSecessionDialog = false }

    if (showSecessionDialog) {
        Dialog(onDismissRequest = { showSecessionDialog = false }) {
            SecessionDialog(
                title = stringResource(id = R.string.my_page_confirm_secession),
                onCancelBtnClick = onSecessionCancelClick,
                onCheckBtnClick = myPageViewModel::secession,
            )
        }
    }

    LaunchedEffect(Unit) {
        myPageViewModel.sideEffect.collect {
            when (it) {
                is MyPageSideEffect.Success -> moveToSignIn()
                else -> {}
            }
        }
    }

    // TODO 더미 제거
    var name = ""
    var phoneNumber = ""
    var birth = ""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = SignalColor.White)
            .padding(
                horizontal = 16.dp,
                vertical = 30.dp,
            ),
    ) {
        SubTitle(
            text = stringResource(id = R.string.my_page),
            color = SignalColor.Black,
        )
        Spacer(modifier = Modifier.height(24.dp))
        ProfileCard(
            name = name,
            phoneNumber = phoneNumber,
            birth = birth,
        )
        Achievement()
        Spacer(modifier = Modifier.height(30.dp))

        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            CardUserTool(
                text = stringResource(id = R.string.my_page_bug_report),
                textColor = SignalColor.Black,
                icon = painterResource(id = R.drawable.ic_bug),
                tint = SignalColor.Black,
                onClick = {  /* TODO */ },
            )
            CardUserTool(
                text = stringResource(id = R.string.my_page_logout),
                textColor = SignalColor.Black,
                icon = painterResource(id = R.drawable.ic_logout),
                tint = SignalColor.Black,
                onClick = myPageViewModel::signOut,
            )
            CardUserTool(
                text = stringResource(id = R.string.my_page_delete_account),
                textColor = SignalColor.Error,
                icon = painterResource(id = R.drawable.ic_delete_account),
                tint = SignalColor.Error,
                onClick = { showSecessionDialog = true }
            )
        }
    }
}

@Composable
private fun Achievement() {
    Spacer(modifier = Modifier.height(20.dp))
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Body2(
                text = stringResource(id = R.string.my_page_received_achievement),
                color = SignalColor.Black,
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Body(
                    text = stringResource(id = R.string.more_achievement),
                    color = SignalColor.Gray500,
                )
                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.ic_next_gray),
                    contentDescription = null,
                )
            }
        }
        LazyRow(modifier = Modifier.height(66.dp)) {
            // Add a single item
            // TODO 업적 구현
        }
    }
}

@Composable
private fun ProfileCard(
    name: String,
    phoneNumber: String,
    birth: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card(
            modifier = Modifier
                .height(118.dp)
                .fillMaxSize(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp,
            ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = SignalColor.White),
            ) {
                ProfileImage(onClick = {})
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                ) {
                    BodyStrong(
                        color = SignalColor.Primary200,
                        text = name,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Body(
                        color = SignalColor.Gray600,
                        text = phoneNumber,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Body(
                        color = SignalColor.Gray500,
                        text = birth,
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileImage(
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier.padding(19.dp),
    ) {
        Image(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            painter = painterResource(id = R.drawable.ic_profile_image),
            contentDescription = stringResource(R.string.my_page_profile_image),
        )
        Image(
            modifier = Modifier
                .size(18.dp)
                .align(Alignment.BottomEnd),
            painter = painterResource(id = R.drawable.ic_add_image),
            contentDescription = null,
        )
    }
}

@Composable
private fun CardUserTool(
    text: String,
    textColor: Color,
    icon: Painter,
    tint: Color,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .signalClickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(SignalColor.Gray200)
                .padding(start = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                tint = tint,
                painter = icon,
                contentDescription = null,
            )
            BodyLarge(
                modifier = Modifier.padding(start = 8.dp),
                text = text,
                color = textColor,
            )
        }
    }
}

@Composable
private fun SecessionDialog(
    title: String,
    onCancelBtnClick: () -> Unit,
    onCheckBtnClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(6.dp))
            .background(SignalColor.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Body2(
            text = title,
            color = SignalColor.Black,
        )
        Spacer(modifier = Modifier.height(26.dp))
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.4.dp)
                .padding(horizontal = 12.dp),
            color = SignalColor.Gray500,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .height(40.dp)
                    .clickable(
                        onClick = onCancelBtnClick,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Body2(
                    text = stringResource(id = R.string.my_page_secession_cancel),
                    color = SignalColor.Gray500
                )
            }
            Divider(
                modifier = Modifier
                    .width(0.4.dp)
                    .height(40.dp)
                    .padding(vertical = 4.dp),
            )
            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .height(40.dp)
                    .clickable(
                        onClick = onCheckBtnClick,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Body2(
                    text = stringResource(id = R.string.my_page_secession_check),
                    color = SignalColor.Error,
                )
            }
        }
    }
}
