package com.signal.data.repository

import com.signal.data.datasource.feed.FeedDataSource
import com.signal.data.model.feed.request.PostRequest
import com.signal.data.model.feed.response.toEntity
import com.signal.domain.PostsEntity
import com.signal.domain.enums.Tag
import com.signal.domain.repository.FeedRepository

class FeedRepositoryImpl(
    private val feedDataSource: FeedDataSource,
) : FeedRepository {
    override suspend fun fetchPosts(
        tag: Tag,
        pageNum: Long,
    ): PostsEntity = feedDataSource.fetchPosts(
        tag = tag,
        pageNum = pageNum,
    ).toEntity()

    override suspend fun createPost(
        title: String,
        content: String,
        image: String,
        tag: Tag,
    ) = runCatching {
        feedDataSource.createPost(
            PostRequest(
                title = title,
                content = content,
                image = image,
                tag = tag,
            ),
        )
    }

    override suspend fun fetchPostDetails(feedId: Long) = runCatching {
        feedDataSource.fetchPostDetails(feedId = feedId).toEntity()
    }
}
