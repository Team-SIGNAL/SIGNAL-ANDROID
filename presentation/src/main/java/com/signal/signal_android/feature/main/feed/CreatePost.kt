package com.signal.signal_android.feature.main.feed

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.signal.data.util.FileUtil
import com.signal.domain.enums.Coin
import com.signal.signal_android.R
import com.signal.signal_android.designsystem.button.SignalFilledButton
import com.signal.signal_android.designsystem.component.Header
import com.signal.signal_android.designsystem.foundation.SignalColor
import com.signal.signal_android.designsystem.textfield.SignalTextField
import com.signal.signal_android.designsystem.util.signalClickable
import com.signal.signal_android.feature.coin.CoinDialog
import com.signal.signal_android.feature.coin.CoinSideEffect
import com.signal.signal_android.feature.coin.CoinViewModel
import com.signal.signal_android.feature.file.AttachmentSideEffect
import com.signal.signal_android.feature.file.AttachmentViewModel
import org.koin.androidx.compose.koinViewModel
import java.util.UUID

@Composable
internal fun CreatePost(
    moveToBack: () -> Unit,
    feedId: UUID?,
    feedViewModel: FeedViewModel = koinViewModel(),
    attachmentViewModel: AttachmentViewModel = koinViewModel(),
    coinViewModel: CoinViewModel = koinViewModel(),
) {
    val state by feedViewModel.state.collectAsState()
    val coinState by coinViewModel.state.collectAsState()
    val fileState by attachmentViewModel.state.collectAsState()
    val details = state.postDetailsEntity
    var showCoinDialog by remember { mutableStateOf(false) }

    var imagePreview: Uri? by remember { mutableStateOf(null) }

    if (showCoinDialog) {
        Dialog(onDismissRequest = { showCoinDialog = false }) {
            CoinDialog(
                coin = Coin.FEED,
                coinCount = coinState.createCoinEntity.coinCount,
                onClick = moveToBack,
            )
        }
    }

    LaunchedEffect(Unit) {
        if (feedId != null) {
            with(feedViewModel) {
                setFeedId(feedId)
                fetchPostDetails()
            }
        }
    }

    val context = LocalContext.current

    val focusManager = LocalFocusManager.current

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        it?.run {
            imagePreview = it
            attachmentViewModel.setFile(
                FileUtil.toFile(
                    context = context,
                    uri = this,
                )
            )
        }
    }

    LaunchedEffect(Unit) {
        attachmentViewModel.sideEffect.collect {
            when (it) {
                is AttachmentSideEffect.Success -> {
                    if (feedId == null) {
                        feedViewModel.createPost(imageUrl = fileState.imageUrl)
                    } else {
                        feedViewModel.editPost(imageUrl = fileState.imageUrl)
                    }
                }

                is AttachmentSideEffect.Failure -> {

                }
            }
        }
    }

    LaunchedEffect(Unit) {
        feedViewModel.sideEffect.collect {
            when (it) {
                is FeedSideEffect.PostSuccess -> {
                    coinViewModel.createCoin(
                        coin = 2,
                        type = Coin.FEED,
                    )
                }

                else -> {

                }
            }
        }
    }

    LaunchedEffect(Unit) {
        coinViewModel.sideEffect.collect {
            when (it) {
                is CoinSideEffect.Success -> {
                    showCoinDialog = true
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Header(
            title = stringResource(
                id = if (feedId == null) {
                    R.string.create_post_header_title
                } else {
                    R.string.edit_post_header_title
                },
            ),
            onLeadingClicked = moveToBack,
        )
        Spacer(modifier = Modifier.height(4.dp))
        SignalTextField(
            value = state.title,
            onValueChange = feedViewModel::setTitle,
            hint = stringResource(id = R.string.create_post_title_hint),
            title = stringResource(id = R.string.create_post_title),
            showLength = true,
            maxLength = 20,
        )
        Spacer(modifier = Modifier.height(8.dp))
        SignalTextField(
            modifier = Modifier.fillMaxHeight(0.5f),
            value = state.content,
            onValueChange = feedViewModel::setContent,
            hint = stringResource(id = R.string.create_post_content_hint),
            title = stringResource(id = R.string.create_post_content),
            alignment = Alignment.Top,
            showLength = true,
            singleLine = false,
            maxLength = 300,
        )
        PostImage(
            uri = { imagePreview },
            imageUrl = { details.image },
        ) {
            focusManager.clearFocus()
            launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        Spacer(modifier = Modifier.weight(1f))
        SignalFilledButton(
            text = stringResource(id = R.string.my_page_secession_check),
            onClick = {
                if (imagePreview == null) {
                    if (feedId == null) {
                        feedViewModel.createPost()
                    } else {
                        feedViewModel.editPost()
                    }
                } else {
                    attachmentViewModel.uploadFile()
                }
                focusManager.clearFocus()
            },
            enabled = state.buttonEnabled,
        )
        Spacer(modifier = Modifier.height(26.dp))
    }
}

@Composable
internal fun PostImage(
    uri: () -> Uri?,
    imageUrl: () -> String?,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(vertical = 28.dp)
            .clip(RoundedCornerShape(8.dp))
            .size(88.dp)
            .background(SignalColor.Gray300)
            .signalClickable(
                onClick = onClick,
                rippleEnabled = true,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier.size(28.dp),
            painter = painterResource(id = R.drawable.ic_gallery),
            contentDescription = stringResource(id = R.string.create_post_image),
            tint = SignalColor.Gray500,
        )
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = if (imageUrl().isNullOrBlank()) uri()
            else imageUrl(),
            contentDescription = stringResource(id = R.string.feed_image),
            contentScale = ContentScale.Crop,
        )
    }
}
