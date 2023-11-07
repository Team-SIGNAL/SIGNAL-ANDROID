package com.signal.signal_android.feature.main.feed

import com.signal.domain.PostsEntity
import com.signal.domain.enums.Tag

data class FeedState(
    val posts: List<PostsEntity.PostEntity>,
    val tag: Tag,
    val pageNum: Long,
    val isPostsEmpty: Boolean,
    val title: String,
    val content: String,
) {
    companion object {
        fun getDefaultState() = FeedState(
            posts = listOf(),
            tag = Tag.GENERAL,
            pageNum = 1,
            isPostsEmpty = false,
            title = "",
            content = "",
        )
    }
}
