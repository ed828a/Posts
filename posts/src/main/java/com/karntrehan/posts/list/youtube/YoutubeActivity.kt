package com.karntrehan.posts.list.youtube

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.karntrehan.posts.R
import com.karntrehan.posts.commons.PostDH
import com.karntrehan.posts.commons.data.datatype.VideoModel
import com.mpaani.core.networking.Outcome
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_youtube.*
import java.io.IOException
import javax.inject.Inject

class YoutubeActivity : AppCompatActivity(), VideoAdapter.VideoInteractor {

    private val component by lazy { PostDH.listComponent() }

    @Inject
    lateinit var viewModelFactory: YoutubeViewModelFactory
    @Inject
    lateinit var picasso: Picasso

    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(YoutubeViewModel::class.java)
    }

    lateinit var adapter: VideoAdapter
    private val context: Context by lazy { this }


    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube)

        adapter = VideoAdapter(picasso)
        adapter.interactor = this

        videoListView.adapter = adapter

        swipeRefreshLayout.setOnRefreshListener { viewModel.refreshVideos() }

        initiateVideoDataListener()
        getVideos()
    }

    private fun getVideos() {
        Log.d(YOUTUBETAG, "getVideos called")
        swipeRefreshLayout.isRefreshing = true
        viewModel.getVideos()
    }

    private fun initiateVideoDataListener() {
        Log.d(YOUTUBETAG, "initiateVideoDataListener called")
        viewModel.videosOutcome.observe(this, Observer<Outcome<List<VideoModel>>> { outcome ->
            Log.d(YOUTUBETAG, "initiateVideoDataListener: Outcome = ${outcome.toString()}")

            when (outcome) {
                is Outcome.Progress -> swipeRefreshLayout.isRefreshing = outcome.loading
                is Outcome.Success -> {
                    Log.d(YOUTUBETAG, "initiateVideoDataListener: Successfully: Outcome.data = ${outcome.data}")
                    if (outcome.data.isEmpty()){
                        viewModel.refreshVideos()
                    } else {
                        adapter.setData(outcome.data)
                    }
                }

                is Outcome.Failure -> {
                    if (outcome.e is IOException)
                        Toast.makeText(this, R.string.need_internet_posts, Toast.LENGTH_LONG)
                                .show()
                    else Toast.makeText(context, R.string.failed_post_try_again, Toast.LENGTH_LONG)
                            .show()
                }
            }
        })
    }

    override fun videoClicked(video: VideoModel) {
        Toast.makeText(this, "$video", Toast.LENGTH_LONG).show()
    }
}
