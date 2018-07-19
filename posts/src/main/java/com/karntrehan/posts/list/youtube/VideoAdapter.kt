package com.karntrehan.posts.list.youtube

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.karntrehan.posts.R
import com.karntrehan.posts.commons.data.PostWithUser
import com.karntrehan.posts.commons.data.datatype.VideoModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cell_video.view.*

/**
 * Created by Edward on 7/19/2018.
 */
class VideoAdapter(private val picasso: Picasso):  RecyclerView.Adapter<VideoAdapter.VideoViewHolder>(){
    private var data = emptyList<VideoModel>()
    var interactor: VideoInteractor? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.cell_video, parent, false)

        return VideoViewHolder(view)
    }

    override fun getItemCount(): Int = data.count()

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(data[position], picasso)
    }

    fun setData(data: List<VideoModel>?) {
        Log.d("VideoAdapter", "setData: called")
        if (data != null) {
            this.data = data
            notifyDataSetChanged()
        }
    }

    inner class VideoViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView){
        private val textViewTitle = itemView?.textViewTitle
        private val textViewDate = itemView?.textViewDate
        private val imageViewThumb = itemView?.imageViewThumb

        fun bind(video: VideoModel, picasso: Picasso){
            textViewTitle?.text = video.title
            textViewDate?.text = video.date
            picasso.load(video.thumbnail).into(imageViewThumb)
        }
    }

    interface VideoInteractor {
        fun videoClicked(video: VideoModel)
    }
}