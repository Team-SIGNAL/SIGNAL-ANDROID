package com.signal.signal_android.feature.main.feed

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
                    _posts.clear()
                    _posts.addAll(it.postEntities)
                    setState(copy(posts = _posts.toMutableStateList()))
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
                    fetchPosts()
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

    internal fun deletePost() {
        with(state.value) {
            val remove = {
                _posts.remove(_posts.find { it.id == feedId })
                setState(copy(posts = _posts.toMutableStateList()))
            }
            viewModelScope.launch(Dispatchers.IO) {
                feedRepository.deletePost(feedId = feedId).onSuccess {
                    remove()
                }.onFailure {
                    if (it is KotlinNullPointerException) {
                        remove()
                    }
                }
            }
        }
    }

    internal fun editPost() {
        with(state.value) {
            viewModelScope.launch(Dispatchers.IO) {
                feedRepository.editPost(
                    feedId = feedId,
                    title = title,
                    image = image,
                    content = content,
                    tag = tag,
                ).onSuccess {

                }
            }
        }
    }

    internal fun editPost() {
        with(state.value) {
            viewModelScope.launch(Dispatchers.IO) {
                feedRepository.editPost(
                    feedId = feedId,
                    title = title,
                    image = image,
                    content = content,
                    tag = tag,
                ).onSuccess {

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
