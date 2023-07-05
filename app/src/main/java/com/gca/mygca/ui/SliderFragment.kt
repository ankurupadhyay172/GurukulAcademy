package com.gca.mygca.ui

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gca.mygca.BR
import com.gca.mygca.R
import com.gca.mygca.base.BaseFragment
import com.gca.mygca.databinding.FragmentImageGalleryBinding
import com.gca.mygca.model.adapter.ImageAdapter
import com.gca.mygca.model.adapter.ImageGalleryAdapter
import com.gca.mygca.model.adapter.ImageSliderAdapter
import com.gca.mygca.model.request.AddMediaRequestModel
import com.gca.mygca.model.request.AddRequestModel
import com.gca.mygca.model.request.AddSliderRequestModel
import com.gca.mygca.model.request.CommonRequestModel
import com.gca.mygca.model.response.MediaModel
import com.gca.mygca.utils.ActionType
import com.gca.mygca.utils.Loader
import com.gca.mygca.utils.MediaType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SliderFragment :BaseFragment<FragmentImageGalleryBinding,HomeViewModel>(){
    val homeViewModel: HomeViewModel by viewModels()

    lateinit var loader: Dialog
    var actionType = ActionType.ADD.type
    val takePhoto = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val uri = it.data?.data
            val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri);

            //val imageBitmap = it.data?.extras?.get("data") as Bitmap
            // do your thing with the obtained bitmap
            //getViewDataBinding().image.setImageBitmap(imageBitmap)
            val image = homeViewModel.BitMapToString(homeViewModel.getResizedBitmap(bitmap,1000)!!)
            uploadImage(image)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //getViewDataBinding().recyclerView.adapter = adapter
            loader = Loader.getLoader(requireActivity())
            loader.show()
            getMedia()
        getViewDataBinding().fabAdd.visibility = View.VISIBLE
        getViewDataBinding().fabAdd.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    requireActivity().requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),101)
                }
            }else{
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                takePhoto.launch(intent)
            }

        }
    }

    private fun getMedia() {
        homeViewModel.getBanner().observe(viewLifecycleOwner) {
            Log.d("myapiresponse", "onViewCreated: " + it.getErrorIfExists()?.message)
            it.getErrorIfExists()?.let {
                loader.cancel()
            }
            it.getValueOrNull().let {
                loader.cancel()
                if (it?.status == 1) {
                    val adapter = ImageSliderAdapter(it.result, requireContext())
                    getViewDataBinding().gridView.adapter = adapter

                    getViewDataBinding().gridView.setOnItemClickListener { adapterView, view, i, l ->
                        Log.d("mygridview", "onViewCreated: ${it.result[i]}")

                    }

                    adapter.options ={it,model->
                        AlertDialog.Builder(requireContext()).setTitle(R.string.options)
                            .setItems(R.array.option_del) { dialog, which ->

                                if (which==0){
                                    loader.show()
                                homeViewModel.manageSlider(AddSliderRequestModel(id = model?.id, name = model?.name, type = ActionType.DELETE.type)).observe(viewLifecycleOwner){
                                    it.getErrorIfExists()?.let {
                                        loader.cancel()
                                        showToast(""+it.message)
                                    }
                                    it.getValueOrNull()?.let {
                                        loader.cancel()
                                        showToast("Slider deleted successfully")
                                        getMedia()

                                    }
                                }
                                }
                            }.show()
                    }
                }
            } }

    }

    fun uploadImage(image:String?){
        loader.show()
        homeViewModel.manageSlider(AddSliderRequestModel(image = image, type = actionType)).observe(viewLifecycleOwner){
            it.getErrorIfExists()?.let {
               loader.cancel()
                showToast(""+it.message)
                Log.d("mylogerror", "uploadImage: ${it.message}")
            }
            it.getValueOrNull()?.let {
                if (it.status==1){
                    loader.cancel()
                    showToast("Image uploaded successfully")
                    getMedia()
                }

            }
        }
    }
    override fun getLayoutId() = R.layout.fragment_image_gallery
    override fun getBindingVariable() = BR.model
    override fun getViewModel() = homeViewModel
}