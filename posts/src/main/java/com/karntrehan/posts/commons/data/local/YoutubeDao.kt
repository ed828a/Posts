package com.karntrehan.posts.commons.data.local

import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.karntrehan.posts.commons.data.datatype.VideoModel
import io.reactivex.Flowable


/**
 * Created by Edward on 7/19/2018.
 */
interface YoutubeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(videos: List<VideoModel>)

    @Query("DELETE FROM videos_table")
    fun deleteAll(video: Post)

    @Query("SELECT * FROM videos_table")
    fun getAll(): Flowable<List<VideoModel>>
}