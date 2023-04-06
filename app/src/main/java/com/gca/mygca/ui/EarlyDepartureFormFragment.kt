package com.gca.mygca.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gca.mygca.BR
import com.gca.mygca.R
import com.gca.mygca.base.BaseFragment
import com.gca.mygca.databinding.FragmentEarlyDepartureFormBinding
import com.gca.mygca.model.request.DepartureRequestModel
import com.gca.mygca.utils.Loader
import com.gca.mygca.utils.setImage
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class EarlyDepartureFormFragment: BaseFragment<FragmentEarlyDepartureFormBinding,HomeViewModel>() {

    val homeViewModel: HomeViewModel by activityViewModels()
    var image: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //registering fragment for camera listener
        val takePhoto = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val imageBitmap = it.data?.extras?.get("data") as Bitmap
                // do your thing with the obtained bitmap
                //getViewDataBinding().image.setImageBitmap(imageBitmap)
                image = homeViewModel.BitMapToString(imageBitmap)
                getViewDataBinding().capture.setImageBitmap(imageBitmap)
            }
        }

        val date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        getViewDataBinding().edtDate.setText(date)

        val time = SimpleDateFormat("hh:mm aaa", Locale.getDefault()).format(Date())
        getViewDataBinding().edtTime.setText(time)
        getViewDataBinding().capture.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requireActivity().requestPermissions(arrayOf(Manifest.permission.CAMERA),101)
                }
            }else{
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                takePhoto.launch(intent)
            }

        }









        val edtDate = getViewDataBinding().edtDate.text.toString()
        val edtTime = getViewDataBinding().edtTime.text.toString()

        val loader = Loader.getLoader(requireActivity())
        getViewDataBinding().submit.setOnClickListener {

            if(getViewDataBinding().edtStudentName.text.isEmpty()||
                getViewDataBinding().edtStudentClass.text.isEmpty()||
                getViewDataBinding().edtReceiverName.text.isEmpty()||
                getViewDataBinding().edtRelationWithStudent.text.isEmpty()||
                getViewDataBinding().edtMobileNo.text.isEmpty()||
                getViewDataBinding().edtInchargeName.text.isEmpty()){
                if(getViewDataBinding().edtStudentName.text.isEmpty())
                    getViewDataBinding().edtStudentName.error = "Enter student name"

                if(getViewDataBinding().edtStudentClass.text.isEmpty())
                    getViewDataBinding().edtStudentClass.error = "Enter student class"

                if(getViewDataBinding().edtReceiverName.text.isEmpty())
                    getViewDataBinding().edtReceiverName.error = "Enter receiver name"

                if(getViewDataBinding().edtRelationWithStudent.text.isEmpty())
                    getViewDataBinding().edtRelationWithStudent.error = "Enter relation with student"

                if(getViewDataBinding().edtMobileNo.text.isEmpty())
                    getViewDataBinding().edtMobileNo.error = "Enter student mobile no"

                if(getViewDataBinding().edtInchargeName.text.isEmpty())
                    getViewDataBinding().edtInchargeName.error = "Enter incharge name"



            }else{
                loader.show()
                homeViewModel.addDeparture(
                    DepartureRequestModel(getViewDataBinding().edtStudentName.text.toString(),getViewDataBinding().edtStudentClass.text.toString(),getViewDataBinding().edtStudentSection.text.toString()
                        ,getViewDataBinding().edtRelationWithStudent.text.toString(),homeViewModel.selectedBranch,getViewDataBinding().edtReceiverName.text.toString(),image,getViewDataBinding().edtMobileNo.text.toString(),getViewDataBinding().edtInchargeName.text.toString(),edtDate,edtTime)).observe(viewLifecycleOwner){
                    Log.d("mylogdata", "onViewCreated: "+it.getErrorIfExists()?.message)
                    it.getErrorIfExists()?.let {
                        loader.cancel()
                        showToast(""+it.message)
                    }
                    it.getValueOrNull()?.let {
                        //showToast("${it.status}")
                        loader.cancel()
                        showToast("Departure added successfully")
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    override fun getLayoutId() = R.layout.fragment_early_departure_form
    override fun getBindingVariable() = BR.model
    override fun getViewModel() = homeViewModel
}