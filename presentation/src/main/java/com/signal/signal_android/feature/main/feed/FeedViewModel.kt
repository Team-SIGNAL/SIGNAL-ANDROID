package com.signal.signal_android.feature.main.feed

import androidx.lifecycle.viewModelScope
import com.signal.domain.PostsEntity
import com.signal.domain.entity.PostCommentsEntity
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
    private val _comments: MutableList<PostCommentsEntity.CommentEntity> = mutableListOf()

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

    internal fun fetchPostComments() {
        with(state.value) {
            viewModelScope.launch(Dispatchers.IO) {
                feedRepository.fetchPostComments(feedId).onSuccess {
                    _comments.clear()
                    _comments.addAll(it.comments)
                    setState(copy(comments = _comments))
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
                    fetchPostComments()
                }.onFailure {

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

    internal fun setTag(tag: Tag) {
        with(state.value) {
            setState(copy(tag = tag))
            _posts.clear()
            fetchPosts()
        }
    }

    internal fun setComment(comment: String) {
        setState(state.value.copy(comment = comment))
    }
}
