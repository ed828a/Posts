package com.karntrehan.posts.list.youtube

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.karntrehan.posts.commons.PostDH
import com.karntrehan.posts.commons.data.datatype.VideoModel
import com.karntrehan.posts.core.extensions.toLiveData
import com.karntrehan.posts.list.model.ListDataContract
import com.mpaani.core.networking.Outcome
import io.reactivex.disposables.CompositeDisposable


/**
 * Created by Edward on 7/19/2018.
 */
class YoutubeViewModel(private val repo: ListDataContract.Repository,
                       private val compositeDisposable: CompositeDisposable) : ViewModel() {

    val videosOutcome: LiveData<Outcome<List<VideoModel>>> by lazy {
        repo.videoFetchOutcome.toLiveData(compositeDisposable)
    }

    fun getVideos() {
        if (videosOutcome.value == null)
            repo.fetchVideos()
    }

    fun refreshVideos() {
        repo.refreshVideos()
    }

    override fun onCleared() {
        super.onCleared()
        //clear the disposables when the viewmodel is cleared
        compositeDisposable.clear()
        PostDH.destroyListComponent()
    }
}