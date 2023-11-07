package com.signal.signal_android.feature.main.feed

import androidx.lifecycle.viewModelScope
import com.signal.domain.PostsEntity
import com.signal.domain.enums.Tag
import com.signal.domain.repository.FeedRepository
import com.signal.signal_android.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class FeedViewModel(
    private val feedRepository: FeedRepository,
) : BaseViewModel<FeedState, FeedSideEffect>(FeedState.getDefaultState()) {
    private val _posts: MutableList<PostsEntity.PostEntity> = mutableListOf()

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        with(state.value) {
            viewModelScope.launch(Dispatchers.IO) {
                kotlin.runCatching {
                    feedRepository.fetchPosts(
                        tag = tag,
                        pageNum = pageNum,
                    )
                }.onSuccess {
                    _posts.addAll(it.postEntities)
                    setState(copy(posts = _posts))
                }.onFailure {
                }
            }
        }
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
