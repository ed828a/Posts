package com.karntrehan.posts.list.model

import android.util.Log
import com.karntrehan.posts.commons.data.datatype.VideoModel
import com.karntrehan.posts.commons.data.remote.PostService
import com.karntrehan.posts.commons.data.remote.YoutubeAPI
import com.karntrehan.posts.list.youtube.YOUTUBETAG
import io.reactivex.Single

class ListRemoteData(private val postService: PostService, val youtubeAPI: YoutubeAPI) : ListDataContract.Remote {

    override fun getVideos(): Single<List<VideoModel>> {

        val response = youtubeAPI.searchVideo(query = "trump")

        return response.flatMap { youtubeResponse ->
            Single.create<List<VideoModel>> { emitter ->
                try {
                    val videoList = youtubeResponse.items.map {
                        val video = VideoModel(it.snippet.title,
                                it.snippet.publishedAt.extractDate(),
                                it.snippet.thumbnails.high.url,
                                it.id.videoId
                        )
//                        Log.d(YOUTUBETAG, "Remote getVideos() return : ${video.toString()}")
                        video
                    }
                    emitter.onSuccess(videoList)
                } catch (e: Throwable) {
                    Log.d(YOUTUBETAG, "Remote getVideos() return Exception: ${e.message}")
                    emitter.onError(e)
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