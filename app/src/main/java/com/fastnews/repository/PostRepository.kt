package com.fastnews.repository

import com.fastnews.service.api.RedditAPI
import com.fastnews.service.model.NewPost
import com.fastnews.service.model.PostData

object PostRepository : BaseRepository() {

    suspend fun getPosts(after: String, limit: Int): NewPost {

        val postResponse = safeApiCall(
            call = { RedditAPI.redditService.getPostList(after, limit).await() },
            errorMessage = "Error to fetching posts"
        )

        val afterId = postResponse?.data?.let { it.after }?: kotlin.run { "" }

        val result: MutableList<PostData> = mutableListOf()
        postResponse?.data?.children?.map { postChildren -> result.add(postChildren.data) }

        return NewPost(result, afterId)

    }
}