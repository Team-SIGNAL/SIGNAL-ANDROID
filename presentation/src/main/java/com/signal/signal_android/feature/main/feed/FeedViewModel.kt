package com.signal.signal_android.feature.main.feed

import androidx.lifecycle.viewModelScope
import com.signal.domain.PostsEntity
import com.signal.domain.entity.PostDetailsEntity
import com.signal.domain.enums.Tag
import com.signal.domain.repository.FeedRepository
import com.signal.signal_android.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class FeedViewModel(
    private val feedRepository: FeedRepository,
) : BaseViewModel<FeedState, FeedSideEffect>(FeedState.getDefaultState()) {
    private val _posts: MutableList<PostsEntity.PostEntity> = mutableListOf()

    internal fun fetchPosts() {
        with(state.value) {
            viewModelScope.launch(Dispatchers.IO) {
                kotlin.runCatching {
                    feedRepository.fetchPosts(
                        tag = tag,
                        pageNum = pageNum,
                        num = num,
                    )
                }.onSuccess {
                    _posts.addAll(it.postEntities)
                    setState(
                        copy(
                            posts = _posts,
                            isPostsEmpty = _posts.isEmpty(),
                        )
                    )
                }.onFailure {
                    setState(copy(isPostsEmpty = _posts.isEmpty()))
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
                    tag = tag,
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
                                imageUrl = it.imageUrl,
                                title = it.title,
                                date = it.date,
                                writer = it.writer,
                                content = it.content,
                                profile = it.profile,
                                isMine = it.isMine,
                            ),
                        ),
                    )
                }
            }
        }
    }

    internal fun setTitle(title: String) {
        setState(state.value.copy(title = title))
    }

    internal fun setContent(content: String) {
        setState(state.value.copy(content = content))
    }

    internal fun setFeedId(feedId: Long) {
        setState(state.value.copy(feedId = feedId))
    }

    internal fun setTag() {
        with(state.value) {
            setState(
                copy(
                    tag = if (tag == Tag.GENERAL) {
                        Tag.NOTIFICATION
                    } else {
                        Tag.GENERAL
                    },
                ),
            )
            fetchPosts()
        }
    }
}
