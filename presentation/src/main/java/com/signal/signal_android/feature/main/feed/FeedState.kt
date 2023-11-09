package com.signal.signal_android.feature.main.feed

import com.signal.domain.PostsEntity
import com.signal.domain.entity.PostDetailsEntity
import com.signal.domain.enums.Tag

data class FeedState(
    val posts: List<PostsEntity.PostEntity>,
    val tag: Tag,
    val pageNum: Long,
    val size: Long,
    val isPostsEmpty: Boolean,
    val title: String,
    val content: String,
    val postDetailsEntity: PostDetailsEntity,
    val feedId: Long,
) {
    companion object {
        fun getDefaultState() = FeedState(
            posts = listOf(),
            tag = Tag.GENERAL,
            pageNum = 1,
            size = 0,
            isPostsEmpty = true,
            title = "",
            content = "",
            postDetailsEntity = PostDetailsEntity(
                imageUrl = null,
                title = "",
                date = "",
                writer = "",
                content = "",
                profile = "https://github.com/Team-SIGNAL/SIGNAL-ANDROID/blob/develop/presentation/src/main/res/drawable/ic_profile_image.png?raw=true",
                isMine = false,
            ),
            feedId = 0L,
        )
    }
}
