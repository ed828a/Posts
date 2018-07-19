package com.karntrehan.posts.commons.data.remote

import android.util.Log
import com.karntrehan.posts.commons.data.datatype.YoutubeResponse
import com.karntrehan.posts.core.BuildConfig
import io.reactivex.Single
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url


/**
 * Created by Edward on 7/19/2018.
 */
interface YoutubeAPI {

    @GET("search")
    fun searchVideo(@Query("q") query: String = "",
                    @Query("pageToken") pageToken: String ="",
                    @Query("part") part: String = "snippet",
                    @Query("maxResults") maxResults: String = "50",
                    @Query("type") type: String = "video",
                    @Query("key") key: String = BuildConfig.YOUTUBE_API_KEY): Single<YoutubeResponse>

    @GET("search")
    fun getRelatedVideos(@Query("relatedToVideoId") relatedToVideoId: String = "",
                         @Query("pageToken") pageToken: String = "",
                         @Query("part") part: String = "snippet",
                         @Query("maxResults") maxResults: String = "50",
                         @Query("type") type: String = "video",
                         @Query("key") key: String = BuildConfig.YOUTUBE_API_KEY): Single<YoutubeResponse>

    companion object {

        fun create(): YoutubeAPI = createYoutubeApi(HttpUrl.parse(BuildConfig.YOUTUBE_BASE_URL)!!)
        private fun createYoutubeApi(httpUrl: HttpUrl): YoutubeAPI {
            val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                Log.d("YoutubeAPI", it)
            })
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val okHttpClient = OkHttpClient.Builder().addInterceptor(logger).build()

            return Retrofit.Builder()
                    .baseUrl(httpUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(YoutubeAPI::class.java)
        }
    }
}