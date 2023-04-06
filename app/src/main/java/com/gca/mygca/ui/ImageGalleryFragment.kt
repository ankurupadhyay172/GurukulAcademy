package com.gca.mygca.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gca.mygca.BR
import com.gca.mygca.R
import com.gca.mygca.base.BaseFragment
import com.gca.mygca.databinding.FragmentImageGalleryBinding
import com.gca.mygca.model.adapter.ImageAdapter
import com.gca.mygca.model.adapter.ImageGalleryAdapter
import com.gca.mygca.model.request.CommonRequestModel
import com.gca.mygca.model.response.MediaModel
import com.gca.mygca.utils.Loader
import com.gca.mygca.utils.MediaType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ImageGalleryFragment :BaseFragment<FragmentImageGalleryBinding,HomeViewModel>(){
    val homeViewModel: HomeViewModel by viewModels()
    val args: ImageGalleryFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //getViewDataBinding().recyclerView.adapter = adapter
            val loader = Loader.getLoader(requireActivity())
            loader.show()
            homeViewModel.getMedia(CommonRequestModel(args.id)).observe(viewLifecycleOwner) {
                Log.d("myapiresponse", "onViewCreated: " + it.getErrorIfExists()?.message)
                it.getErrorIfExists()?.let {
                    loader.cancel()
                }
                it.getValueOrNull().let {
                    loader.cancel()
                    if (it?.status == 1) {
                        getViewDataBinding().gridView.adapter =
                            ImageAdapter(it.result, requireContext())
                        getViewDataBinding().gridView.setOnItemClickListener { adapterView, view, i, l ->
                            Log.d("mygridview", "onViewCreated: ${it.result[i]}")
                            savedInstanceState?.putString("list", Gson().toJson(it.result))
                            if(it.result[i].type==MediaType.IMAGE.type){
                                findNavController().navigate(
                                    ImageGalleryFragmentDirections.actionImageGalleryFragmentToImageViewerFragment(Gson().toJson(it.result), i))
                            }
                            if (it.result[i].type==MediaType.VIDEO.type){
                                findNavController().navigate(
                                    ImageGalleryFragmentDirections.actionImageGalleryFragmentToVideoPlayerFragment(it.result[i].path))
                            }
                        } }
                } }


    }
    override fun getLayoutId() = R.layout.fragment_image_gallery
    override fun getBindingVariable() = BR.model
    override fun getViewModel() = homeViewModel
}