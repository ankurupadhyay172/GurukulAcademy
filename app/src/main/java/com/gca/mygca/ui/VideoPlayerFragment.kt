package com.gca.mygca.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.gca.mygca.BR
import com.gca.mygca.R
import com.gca.mygca.base.BaseFragment
import com.gca.mygca.databinding.FragmentImageViewerBinding
import com.gca.mygca.databinding.FragmentVideoPlayerBinding
import com.gca.mygca.model.adapter.ImageFragmentAdapter
import com.gca.mygca.model.response.MediaModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoPlayerFragment :BaseFragment<FragmentVideoPlayerBinding,HomeViewModel>(){
    val homeViewModel: HomeViewModel by viewModels()
   // private val mp4Url = "https://html5demos.com/assets/dizzy.mp4"
    val args:VideoPlayerFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val exoPlayer = ExoPlayer.Builder(requireContext()).build()
        exoPlayer.playWhenReady = true
        getViewDataBinding().exoPlayer.player = exoPlayer
        val mediaItem = MediaItem.fromUri(args.url)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.seekTo(0)
        exoPlayer.playWhenReady = true
        exoPlayer.prepare()

    }
    override fun getLayoutId() = R.layout.fragment_video_player
    override fun getBindingVariable() = BR.model
    override fun getViewModel() = homeViewModel
}