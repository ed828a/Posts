package com.karntrehan.posts.commons.data.datatype

import android.annotation.SuppressLint
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.ColumnInfo

/**
 * Created by Edward on 7/19/2018.
 */
@Entity(tableName = "videos_table", indices = [(Index(value = ["video_id"], unique = true))])
@Parcelize
@SuppressLint("ParcelCreator")
data class VideoModel(var title: String = "",
                      var date: String = "",
                      var thumbnail: String = "",
                      @PrimaryKey
                      @ColumnInfo(name = "video_id")
                      var videoId: String = "",
                      var relatedToVideoId: String = "",
        // indexResponse: to be consistent with changing backend order
                      var indexResponse: Int = -1) : Parcelable