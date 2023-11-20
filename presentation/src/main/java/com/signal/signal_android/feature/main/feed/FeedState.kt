package com.signal.signal_android.feature.main.feed

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.signal.domain.entity.PostsEntity
import com.signal.domain.entity.PostCommentsEntity
import com.signal.domain.entity.PostDetailsEntity
import com.signal.domain.enums.Tag

data class FeedState(
    val posts: List<PostsEntity.PostEntity>,
    val tag: Tag,
    val page: Long,
    val size: Long,
    val title: String,
    val content: String,
    val postDetailsEntity: PostDetailsEntity,
    val feedId: Long,
    val image: String,
    val comments: SnapshotStateList<PostCommentsEntity.CommentEntity>,
    val comment: String,
    val buttonEnabled: Boolean,
    val hasNextPage: Boolean,
) {
    companion object {
        fun getDefaultState() = FeedState(
            posts = emptyList(),
            tag = Tag.GENERAL,
            page = 0,
            size = 10,
            title = "",
            content = "",
            postDetailsEntity = PostDetailsEntity(
                id = 0L,
                image = null,
                title = "",
                date = "",
                writer = "",
                content = "",
                profile = "https://github.com/Team-SIGNAL/SIGNAL-ANDROID/blob/develop/presentation/src/main/res/drawable/ic_profile_image.png?raw=true",
                isMine = false,
            ),
            feedId = 0L,
            image = "",
            comments = mutableStateListOf(),
            comment = "",
            buttonEnabled = false,
            hasNextPage = false,
        )
    }
}
