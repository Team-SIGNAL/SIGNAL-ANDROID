package com.signal.signal_android.feature.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import coil.compose.AsyncImage
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.component.SignalDialog
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
    moveToLanding: () -> Unit,
    myPageViewModel: MyPageViewModel = koinViewModel(),
) {
    var showSecessionDialog by remember { mutableStateOf(false) }
    val onSecessionCancelClick: () -> Unit = { showSecessionDialog = false }
    val state by myPageViewModel.state.collectAsState()

    if (showSecessionDialog) {
        Dialog(onDismissRequest = { showSecessionDialog = false }) {
            SignalDialog(
                title = stringResource(id = R.string.my_page_confirm_secession),
                onCancelBtnClick = onSecessionCancelClick,
                onCheckBtnClick = myPageViewModel::secession,
            )
        }
    }

    LaunchedEffect(Unit) {
        myPageViewModel.sideEffect.collect {
            when (it) {
                is MyPageSideEffect.SecessionSuccess -> moveToLanding()
                is MyPageSideEffect.SignOutSuccess -> moveToSignIn()
            }
        }
    }

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
            name = state.name,
            phoneNumber = state.phone,
            birth = state.birth,
            profileImageUrl = state.profile,
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
                onClick = { /* TODO */ },
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
                onClick = { showSecessionDialog = true },
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
    profileImageUrl: String?,
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
                ProfileImage(
                    profileImageUrl = profileImageUrl,
                )
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
private fun ProfileImage(profileImageUrl: String?) {
    Box(
        modifier = Modifier.padding(19.dp),
    ) {
        if (profileImageUrl != null) {
            AsyncImage(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                model = profileImageUrl,
                contentDescription = stringResource(id = R.string.my_page_profile_image),
            )
            Image(
                modifier = Modifier
                    .size(18.dp)
                    .align(Alignment.BottomEnd),
                painter = painterResource(id = R.drawable.ic_add_image),
                contentDescription = stringResource(id = R.string.my_page_image_edit_icon),
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.ic_profile_image),
                contentDescription = stringResource(
                    id = R.string.my_page_profile_image
                )
            )
            Image(
                modifier = Modifier
                    .size(18.dp)
                    .align(Alignment.BottomEnd),
                painter = painterResource(id = R.drawable.ic_add_image),
                contentDescription = stringResource(id = R.string.my_page_image_edit_icon),
            )
        }
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
                contentDescription = stringResource(id = R.string.card_user_tool_icon),
            )
            BodyLarge(
                modifier = Modifier.padding(start = 8.dp),
                text = text,
                color = textColor,
            )
        }
    }
}
