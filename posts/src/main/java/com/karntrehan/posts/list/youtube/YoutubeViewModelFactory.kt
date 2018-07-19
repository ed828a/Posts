package com.karntrehan.posts.list.youtube

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.karntrehan.posts.list.model.ListDataContract
import io.reactivex.disposables.CompositeDisposable


/**
 * Created by Edward on 7/19/2018.
 */
class YoutubeViewModelFactory(
        private val repository: ListDataContract.Repository,
        private val compositeDisposable: CompositeDisposable) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(YoutubeViewModel::class.java))
            return YoutubeViewModel(repository, compositeDisposable) as T
        else
            throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
