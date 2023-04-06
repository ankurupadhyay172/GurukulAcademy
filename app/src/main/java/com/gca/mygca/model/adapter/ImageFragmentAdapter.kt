package com.gca.mygca.model.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.gca.mygca.R
import com.gca.mygca.model.response.MediaModel
import com.gca.mygca.utils.MediaType
import com.gca.mygca.utils.setImage
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource

class ImageFragmentAdapter(val list: List<MediaModel>) :
    RecyclerView.Adapter<ImageFragmentAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.imageView)

        fun bind(model: MediaModel) {
            image.setImage(model.path)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_image_view_pager, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[position])

    }

    override fun getItemCount(): Int {
        return list.size
    }
}