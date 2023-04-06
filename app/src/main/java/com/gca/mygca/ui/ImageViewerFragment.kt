package com.gca.mygca.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.gca.mygca.BR
import com.gca.mygca.R
import com.gca.mygca.base.BaseFragment
import com.gca.mygca.databinding.FragmentImageViewerBinding
import com.gca.mygca.model.adapter.ImageFragmentAdapter
import com.gca.mygca.model.response.MediaModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageViewerFragment :BaseFragment<FragmentImageViewerBinding,HomeViewModel>(){
    val homeViewModel: HomeViewModel by viewModels()
    val args:ImageViewerFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myType = object : TypeToken<List<MediaModel>>() {}.type
        val list = Gson().fromJson<List<MediaModel>>(args.list, myType)
        val adapter = ImageFragmentAdapter(list)
        getViewDataBinding().viewPager.adapter = adapter
        getViewDataBinding().viewPager.setCurrentItem(args.pos,false)


    }
    override fun getLayoutId() = R.layout.fragment_image_viewer
    override fun getBindingVariable() = BR.model
    override fun getViewModel() = homeViewModel
}