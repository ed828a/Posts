package com.karntrehan.posts.commons.data.local

import android.arch.persistence.room.*
import com.karntrehan.posts.commons.data.datatype.VideoModel
import io.reactivex.Flowable


/**
 * Created by Edward on 7/19/2018.
 */
@Dao
interface YoutubeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(videos: List<VideoModel>)

    @Query("DELETE FROM videos_table")
    fun deleteAll()

    @Query("SELECT * FROM videos_table")
    fun getAll(): Flowable<List<VideoModel>>
}