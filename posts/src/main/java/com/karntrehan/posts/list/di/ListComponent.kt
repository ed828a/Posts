package com.karntrehan.posts.list.di

import android.arch.persistence.room.Room
import android.content.Context
import com.karntrehan.posts.commons.data.local.PostDb
import com.karntrehan.posts.commons.data.remote.PostService
import com.karntrehan.posts.commons.data.remote.YoutubeAPI
import com.karntrehan.posts.core.constants.Constants
import com.karntrehan.posts.core.di.CoreComponent
import com.karntrehan.posts.core.networking.Scheduler
import com.karntrehan.posts.list.model.ListDataContract
import com.karntrehan.posts.list.model.ListLocalData
import com.karntrehan.posts.list.model.ListRemoteData
import com.karntrehan.posts.list.model.ListRepository
import com.karntrehan.posts.list.viewmodel.ListActivity
import com.karntrehan.posts.list.viewmodel.ListAdapter
import com.karntrehan.posts.list.viewmodel.ListViewModelFactory
import com.karntrehan.posts.list.youtube.YoutubeActivity
import com.karntrehan.posts.list.youtube.YoutubeViewModelFactory
import com.squareup.picasso.Picasso
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit

@ListScope
@Component(dependencies = [CoreComponent::class], modules = [ListModule::class])
interface ListComponent {

    //Expose to dependent components
    fun postDb(): PostDb

    fun postService(): PostService
    fun picasso(): Picasso
    fun scheduler(): Scheduler
    fun provideYoutubeApi(): YoutubeAPI

    fun inject(listActivity: ListActivity)
    fun inject(youtubeActivity: YoutubeActivity)
}

@Module
@ListScope
class ListModule {


    @Provides
    fun provideYoutubeApi(): YoutubeAPI = YoutubeAPI.create()

    @Provides
    fun provideYoutubeViewModelFactory(repository: ListDataContract.Repository,
                                       compositeDisposable: CompositeDisposable): YoutubeViewModelFactory {
        return YoutubeViewModelFactory(repository, compositeDisposable)
    }

    /*Adapter*/
    @Provides
    @ListScope
    fun adapter(picasso: Picasso): ListAdapter = ListAdapter(picasso)

    /*ViewModel*/
    @Provides
    @ListScope
    fun listViewModelFactory(repository: ListDataContract.Repository,compositeDisposable: CompositeDisposable): ListViewModelFactory = ListViewModelFactory(repository,compositeDisposable)

    /*Repository*/
    @Provides
    @ListScope
    fun listRepo(local: ListDataContract.Local, remote: ListDataContract.Remote, scheduler: Scheduler, compositeDisposable: CompositeDisposable): ListDataContract.Repository = ListRepository(local, remote, scheduler, compositeDisposable)

    @Provides
    @ListScope
    fun remoteData(postService: PostService, youtubeAPI: YoutubeAPI): ListDataContract.Remote = ListRemoteData(postService, youtubeAPI)

    @Provides
    @ListScope
    fun localData(postDb: PostDb, scheduler: Scheduler): ListDataContract.Local = ListLocalData(postDb, scheduler)

    @Provides
    @ListScope
    fun compositeDisposable(): CompositeDisposable = CompositeDisposable()

    /*Parent providers to dependents*/
    @Provides
    @ListScope
    fun postDb(context: Context): PostDb = Room.databaseBuilder(context, PostDb::class.java, Constants.Posts.DB_NAME).build()

    @Provides
    @ListScope
    fun postService(retrofit: Retrofit): PostService = retrofit.create(PostService::class.java)


}