package com.karntrehan.posts.list.model

import android.util.Log
import com.karntrehan.posts.commons.data.datatype.VideoModel
import com.karntrehan.posts.commons.data.remote.PostService
import com.karntrehan.posts.commons.data.remote.YoutubeAPI
import io.reactivex.Single

class ListRemoteData(private val postService: PostService, val youtubeAPI: YoutubeAPI) : ListDataContract.Remote {

    override fun getVideos(): Single<List<VideoModel>> {

        return youtubeAPI.searchVideo(query = "trump").flatMap { youtubeResponse ->
            Single.create<List<VideoModel>> {
                youtubeResponse.items.map {
                    val video = VideoModel(it.snippet.title,
                            it.snippet.publishedAt.extractDate(),
                            it.snippet.thumbnails.high.url,
                            it.id.videoId
                    )
                    Log.d("getVideos", video.toString())
                    video
                }
            }
        }
    }


    override fun getUsers() = postService.getUsers()

    override fun getPosts() = postService.getPosts()
}

fun String.extractDate(): String {
    val stringArray = this.split('T')

    return stringArray[0]
}