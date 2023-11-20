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
                }.onSuccess {
                    with(it.postEntities) {
                        if (_posts.size != it.postEntities.size) {
                            when (_posts.contains(firstOrNull())) {
                                true -> _posts.add(last())
                                else -> _posts.addAll(this)
                            }
                            setState(
                                copy(
                                    posts = _posts,
                                    hasNextPage = isNotEmpty()
                                )
                            )
                        }
                    }
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

    internal fun fetchComments() {
        with(state.value) {
            viewModelScope.launch(Dispatchers.IO) {
                feedRepository.fetchComments(feedId).onSuccess {
                    if (_comments.contains(it.comments.first()) && _comments.size != it.comments.size) {
                        _comments.add(it.comments.last())
                    } else {
                        _comments.addAll(it.comments)
                    }
                    setState(copy(comments = _comments.reversed().toMutableStateList()))
                }
            }
        }
    }

    internal fun createComment() {
        with(state.value) {
            viewModelScope.launch(Dispatchers.IO) {
                feedRepository.createComment(
                    feedId = feedId,
                    content = comment,
                ).onSuccess {
                    postSideEffect(FeedSideEffect.ClearFocus)
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

    internal fun deletePost() {
        with(state.value) {
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

    internal fun editPost(imageUrl: String? = null) {
        with(state.value) {
            viewModelScope.launch(Dispatchers.IO) {
                feedRepository.editPost(
                    feedId = feedId,
                    title = title,
                    image = imageUrl ?: image.ifEmpty { postDetailsEntity.image },
                    content = content,
                ).onSuccess {
                    postSideEffect(FeedSideEffect.PostSuccess)
                    fetchPosts()
                }.onFailure {
                    if (it is KotlinNullPointerException) {
                        postSideEffect(FeedSideEffect.PostSuccess)
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

    internal fun setFeedId(feedId: Long) {
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
}
