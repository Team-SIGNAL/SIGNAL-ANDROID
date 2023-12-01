package com.signal.signal_android.feature.main.feed

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.viewModelScope
import com.signal.domain.entity.PostCommentsEntity
import com.signal.domain.entity.PostDetailsEntity
import com.signal.domain.entity.PostsEntity
import com.signal.domain.enums.Tag
import com.signal.domain.repository.FeedRepository
import com.signal.signal_android.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.UUID

internal class FeedViewModel(
    private val feedRepository: FeedRepository,
) : BaseViewModel<FeedState, FeedSideEffect>(FeedState.getDefaultState()) {
    private val _posts: SnapshotStateList<PostsEntity.PostEntity> = mutableStateListOf()
    private val _comments: SnapshotStateList<PostCommentsEntity.CommentEntity> =
        mutableStateListOf()

    internal fun fetchPosts() {
        with(state.value) {
            viewModelScope.launch(Dispatchers.IO) {
                kotlin.runCatching {
                    feedRepository.fetchPosts(
                        tag = tag,
                        page = page,
                        size = size,
                    )
                }.onSuccess { it ->
                    if (_posts.isEmpty()) {
                        _posts.addAll(it.postEntities)
                    } else {
                        _posts.addAll(it.postEntities.filter { !_posts.contains(it) })
                    }
                    setState(
                        state.value.copy(
                            posts = _posts,
                            hasNextPage = it.postEntities.size == 10,
                        )
                    )
                }
            }
        }
    }

    internal fun createPost(imageUrl: String? = null) {
        with(state.value) {
            viewModelScope.launch(Dispatchers.IO) {
                feedRepository.createPost(
                    title = title,
                    content = content,
                    image = imageUrl,
                ).onSuccess {
                    postSideEffect(FeedSideEffect.PostSuccess)
                }
            }
        }
    }

    internal fun fetchPostDetails() {
        with(state.value) {
            if (feedId != null) {
                viewModelScope.launch(Dispatchers.IO) {
                    feedRepository.fetchPostDetails(feedId = feedId).onSuccess {
                        setState(
                            copy(
                                postDetailsEntity = PostDetailsEntity(
                                    id = it.id,
                                    image = it.image,
                                    title = it.title,
                                    date = it.date,
                                    writer = it.writer,
                                    content = it.content,
                                    profile = it.profile,
                                    isMine = it.isMine,
                                ),
                                title = it.title,
                                content = it.content,
                            ),
                        )
                    }
                }
            }
        }
    }

    internal fun fetchComments() {
        with(state.value) {
            if (feedId != null) {
                viewModelScope.launch(Dispatchers.IO) {
                    feedRepository.fetchComments(feedId).onSuccess {
                        if (_comments.size < it.comments.size) {
                            if (_comments.contains(it.comments.firstOrNull())) {
                                _comments.add(it.comments.last())
                            } else {
                                _comments.addAll(it.comments)
                            }
                            setState(copy(comments = _comments.reversed().toMutableStateList()))
                        }
                    }
                }
            }
        }
    }

    internal fun createComment() {
        with(state.value) {
            if (feedId != null) {
                viewModelScope.launch(Dispatchers.IO) {
                    feedRepository.createComment(
                        feedId = feedId,
                        content = comment,
                    ).onSuccess {
                        postSideEffect(FeedSideEffect.ClearFocus)
                        postSideEffect(FeedSideEffect.CommentSuccess)
                        setState(
                            copy(
                                buttonEnabled = false,
                                comment = "",
                            )
                        )
                        fetchComments()
                    }
                }
            }
        }
    }

    internal fun deletePost() {
        with(state.value) {
            if (feedId != null) {
                val remove = {
                    _posts.remove(_posts.find { it.id == feedId })
                    setState(copy(posts = _posts.toMutableStateList()))
                }
                viewModelScope.launch(Dispatchers.IO) {
                    feedRepository.deletePost(feedId = feedId).onSuccess {
                        remove()
                        postSideEffect(FeedSideEffect.DeleteSuccess)
                    }.onFailure {
                        if (it is KotlinNullPointerException) {
                            remove()
                            postSideEffect(FeedSideEffect.DeleteSuccess)
                        }
                    }
                }
            }
        }
    }

    internal fun clearPost() {
        _posts.clear()
        setState(state.value.copy(posts = _posts))
    }

    internal fun editPost(imageUrl: String? = null) {
        with(state.value) {
            if (feedId != null) {
                viewModelScope.launch(Dispatchers.IO) {
                    feedRepository.editPost(
                        feedId = feedId,
                        title = title,
                        image = imageUrl ?: image.ifEmpty { postDetailsEntity.image },
                        content = content,
                    ).onFailure {
                        if (it is KotlinNullPointerException) {
                            clearPost()
                            postSideEffect(FeedSideEffect.PostSuccess)
                        }
                    }
                }
            }
        }
    }

    internal fun nextPage() {
        with(state.value) {
            setState(copy(page = page + 1))
        }
        fetchPosts()
    }

    internal fun setTitle(title: String) {
        setState(state.value.copy(title = title))
        setButtonEnabled()
    }

    internal fun setContent(content: String) {
        setState(state.value.copy(content = content))
        setButtonEnabled()
    }

    private fun setButtonEnabled() {
        with(state.value) {
            setState(
                copy(
                    buttonEnabled = title.isNotBlank() && content.isNotBlank()
                )
            )
        }
    }

    internal fun setFeedId(feedId: UUID) {
        setState(state.value.copy(feedId = feedId))
    }

    internal fun setTag(tag: Tag) {
        with(state.value) {
            setState(
                copy(
                    tag = tag,
                    page = 0,
                )
            )
            _posts.clear()
            fetchPosts()
        }
    }

    internal fun setComment(comment: String) {
        setState(state.value.copy(comment = comment))
    }

    internal fun getCommentTime(date: LocalDateTime): String {
        val today = LocalDateTime.now()

        val daysDifference = ChronoUnit.DAYS.between(date.toLocalDate(), today.toLocalDate())
        val hoursDifference = ChronoUnit.HOURS.between(date.toLocalTime(), today.toLocalTime())
        val minutesDifference = ChronoUnit.MINUTES.between(date.toLocalTime(), today.toLocalTime())

        return when {
            daysDifference > 30 -> "${daysDifference / 30}달 전"
            daysDifference in 1..30 -> "${daysDifference}일 전"
            hoursDifference > 0 -> "${hoursDifference}시간 전"
            else -> "${minutesDifference}분 전"
        }.let { if (it == "0분 전") "방금 전" else it }
    }
}

enum class TimeValue(
    val value: Int,
    val max: Int,
    val message: String,
) {
    SEC(
        value = 60,
        max = 60,
        message = "분 전",
    ),
    MIN(
        value = 60,
        max = 24,
        message = "시간 전",
    ),
    HOUR(
        value = 24,
        max = 30,
        message = "일 전",
    ),
    DAY(
        value = 30,
        max = 12,
        message = "달 전",
    ),
    MONTH(
        value = 12,
        max = Int.MAX_VALUE,
        message = "년 전",
    )
}


