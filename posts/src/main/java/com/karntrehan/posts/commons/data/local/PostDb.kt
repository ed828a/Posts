package com.karntrehan.posts.commons.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.karntrehan.posts.commons.data.datatype.VideoModel

@Database(entities = [Post::class, User::class, Comment::class, VideoModel::class], version = 2,exportSchema = false)
abstract class PostDb : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun userDao(): UserDao
    abstract fun commentDao(): CommentDao
    abstract fun youtubeDao(): YoutubeDao
}
