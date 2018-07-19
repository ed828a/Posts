package com.karntrehan.posts.commons.data

import com.karntrehan.posts.commons.data.remote.YoutubeAPI
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * Created by Edward on 7/19/2018.
 */
@Module
class CommonsModule {

    @Singleton
    @Provides
    fun provideYoutubeApi(): YoutubeAPI = YoutubeAPI.create()
}