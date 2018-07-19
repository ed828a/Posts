package com.karntrehan.posts.list.model

import com.karntrehan.posts.commons.data.PostWithUser
import com.karntrehan.posts.commons.data.datatype.VideoModel
import com.karntrehan.posts.commons.data.local.Post
import com.karntrehan.posts.commons.data.local.User
import com.mpaani.core.networking.Outcome
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

interface ListDataContract {
    interface Repository {
        val postFetchOutcome: PublishSubject<Outcome<List<PostWithUser>>>
        fun fetchPosts()
        fun refreshPosts()
        fun saveUsersAndPosts(users: List<User>, posts: List<Post>)
        fun handleError(error: Throwable)

        val videoFetchOutcome: PublishSubject<Outcome<List<VideoModel>>>
        fun fetchVideos()
        fun refreshVideos()
        fun saveVideos(videos: List<VideoModel>)
        fun handleVideoError(error: Throwable)
    }

    interface Local {
        fun getPostsWithUsers(): Flowable<List<PostWithUser>>
        fun saveUsersAndPosts(users: List<User>, posts: List<Post>)

        fun getVideos(): Flowable<List<VideoModel>>
        fun saveVideos(videos: List<VideoModel>)
    }

    interface Remote {
        fun getUsers(): Single<List<User>>
        fun getPosts(): Single<List<Post>>

        fun getVideos(): Single<List<VideoModel>>
    }
}