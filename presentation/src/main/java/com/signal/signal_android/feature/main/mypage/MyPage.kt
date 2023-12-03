package com.signal.signal_android.feature.main.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.shadow
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
    moveToMoreAchievement: () -> Unit,
    moveToCoinHistory: () -> Unit,
    moveToEditProfile: () -> Unit,
    myPageViewModel: MyPageViewModel = koinViewModel(),
) {
    var showSecessionDialog by remember { mutableStateOf(false) }
    var showSignOutDialog by remember { mutableStateOf(false) }
    val state by myPageViewModel.state.collectAsState()

    if (showSecessionDialog) {
        Dialog(onDismissRequest = { showSecessionDialog = false }) {
            SignalDialog(
                title = stringResource(id = R.string.my_page_confirm_secession),
                onCancelBtnClick = { showSecessionDialog = false },
                onCheckBtnClick = myPageViewModel::secession,
            )
        }
    }

    if (showSignOutDialog) {
        Dialog(onDismissRequest = { showSignOutDialog = false }) {
            SignalDialog(
                title = stringResource(id = R.string.my_page_confirm_sign_out),
                onCancelBtnClick = { showSignOutDialog = false },
                onCheckBtnClick = myPageViewModel::signOut,
            )
        }
    }

    LaunchedEffect(Unit) {
        myPageViewModel.sideEffect.collect {
            when (it) {
                is MyPageSideEffect.SecessionSuccess -> moveToLanding()
                is MyPageSideEffect.SignOutSuccess -> moveToSignIn()
                else -> {}
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            SubTitle(
                text = stringResource(id = R.string.my_page),
                color = SignalColor.Black,
            )
            Row(
                modifier = Modifier.signalClickable { moveToCoinHistory() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.ic_my_page_coin),
                    contentDescription = stringResource(
                        id = R.string.coin_image
                    ),
                )
                Body(
                    text = state.coinCount.toString(),
                    color = SignalColor.Black,
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        ProfileCard(
            name = state.name,
            phoneNumber = state.phone,
            birth = state.birth,
            profileImageUrl = state.profile,
            famousSaying = { state.famousSaying },
            onClick = { moveToEditProfile() },
        )
        Achievement(
            moveToMoreAchievement = moveToMoreAchievement,
            coin = state.coinCount,
            showAchievement = { state.coinCount > achievements.first().coin },
        )
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            /*CardUserTool(
                text = stringResource(id = R.string.my_page_bug_report),
                textColor = SignalColor.Black,
                icon = painterResource(id = R.drawable.ic_bug),
                tint = SignalColor.Black,
                onClick = { *//* TODO *//* },
            )*/
            CardUserTool(
                text = stringResource(id = R.string.my_page_logout),
                textColor = SignalColor.Black,
                icon = painterResource(id = R.drawable.ic_logout),
                tint = SignalColor.Black,
                onClick = { showSignOutDialog = true },
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
private fun AchievementEmptyNotify() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        BodyStrong(text = stringResource(id = R.string.achievement_empty))
        Spacer(modifier = Modifier.height(4.dp))
        Body(
            text = stringResource(id = R.string.achievement_empty_description),
            color = SignalColor.Primary100,
        )
    }
}

internal data class _Achievement(
    val message: String,
    val coin: Long,
)

private val achievements = listOf(
    _Achievement(
        message = "10 코인 획득",
        coin = 10,
    ),
    _Achievement(
        message = "50 코인 획득",
        coin = 50,
    ),
    _Achievement(
        message = "100 코인 획득",
        coin = 100,
    ),
)

@Composable
private fun Achievement(
    moveToMoreAchievement: () -> Unit,
    showAchievement: () -> Boolean,
    coin: Long,
) {
    Spacer(modifier = Modifier.height(20.dp))

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
                modifier = Modifier.signalClickable(
                    enabled = showAchievement(),
                    onClick = moveToMoreAchievement,
                ),
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
    Spacer(modifier = Modifier.height(6.dp))
    if (true) {
        LazyRow(
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 4.dp),
        ) {
            items(achievements) {
                if (it.coin <= coin) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .shadow(
                                spotColor = SignalColor.Primary100,
                                elevation = 4.dp,
                                shape = RoundedCornerShape(10.dp),
                            )
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                color = SignalColor.Gray100,
                                shape = RoundedCornerShape(10.dp),
                            )
                            .padding(
                                vertical = 23.dp,
                                horizontal = 21.dp,
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(SignalColor.White),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            BodyLarge(text = it.message)
                            Spacer(modifier = Modifier.width(10.dp))
                            Image(
                                modifier = Modifier.size(60.dp),
                                painter = painterResource(id = R.drawable.ic_coin_1k),
                                contentDescription = stringResource(id = R.string.achievement_image),
                            )
                        }
                    }
                }
            }
        }
    } else {
        AchievementEmptyNotify()
    }
}

@Composable
private fun ProfileCard(
    name: String,
    phoneNumber: String,
    birth: String,
    profileImageUrl: String?,
    famousSaying: () -> String,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = SignalColor.White),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = SignalColor.White),
    ) {
        Column(
            modifier = Modifier.padding(
                vertical = 18.dp,
                horizontal = 26.dp,
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                ProfileImage(
                    profileImageUrl = profileImageUrl,
                    onClick = onClick,
                )
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    BodyStrong(
                        color = SignalColor.Primary200,
                        text = name,
                    )
                    Body(
                        color = SignalColor.Gray600,
                        text = phoneNumber,
                    )
                    Body(
                        color = SignalColor.Gray500,
                        text = birth,
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        width = 1.dp,
                        color = SignalColor.Primary100,
                        shape = RoundedCornerShape(8.dp),
                    )
                    .padding(
                        horizontal = 20.dp,
                        vertical = 10.dp,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Body2(text = famousSaying())
            }
        }
    }
}

@Composable
private fun ProfileImage(
    profileImageUrl: String?,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier.signalClickable(onClick = onClick)
    ) {
        AsyncImage(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            model = profileImageUrl ?: R.drawable.ic_profile_image,
            contentDescription = stringResource(id = R.string.my_page_profile_image),
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

@Composable
private fun CardUserTool(
    text: String,
    textColor: Color,
    icon: Painter,
    tint: Color,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(8.dp),
            )
            .clip(RoundedCornerShape(8.dp))
            .background(color = SignalColor.White)
            .signalClickable(
                rippleEnabled = true,
                onClick = onClick,
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
