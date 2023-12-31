package com.gca.mygca.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.models.SlideModel
import com.gca.mygca.BR
import com.gca.mygca.R
import com.gca.mygca.base.BaseFragment
import com.gca.mygca.databinding.FragmentHomeBinding
import com.gca.mygca.test.Dsa
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :BaseFragment<FragmentHomeBinding,HomeViewModel>(){
    val homeViewModel: HomeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Loader.getLoader(requireActivity()).show()
        homeViewModel.getBanner().observe(viewLifecycleOwner){
            it.getValueOrNull()?.let {
                val list = mutableListOf<SlideModel>()
                it.result.map { it.image }.forEach { list.add(SlideModel(it)) }
                getViewDataBinding().imageSlider.setImageList(list)
            }
        }

        getViewDataBinding().feeStructure.setOnClickListener {
            if (homeViewModel.selectedBranch=="103"){
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToStudentBoardFragment())
            }else{
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToStudentClassesFragment("2"))
            }
        }
        getViewDataBinding().manageDeparture.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDepartureMenuFragment())
        }
        getViewDataBinding().manageImageGallery.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToImageGalleryFragment("1"))
        }
        getViewDataBinding().manageVideoGallery.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToImageGalleryFragment("2"))
        }
        getViewDataBinding().transportLayout.setOnClickListener {
           findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToTransportScreenFragment())
        }

        getViewDataBinding().sliderLayout.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSliderFragment())
        }

       // test()
    }

    private fun test() {
        homeViewModel.getAllSchoolFees().observe(viewLifecycleOwner){
            it.getValueOrNull()?.let {
                if (it.status==1){
                    val dsa = Dsa()
                   val result = dsa.sortByAmount(it.result)
//                    Log.d("mysortedlist", "test: "+result)
//                    dsa.deleteBoardId("2",it.result)
                    dsa.onlyUniqueValue(result)
                }
            }
        }
    }

    override fun getLayoutId() = R.layout.fragment_home
    override fun getBindingVariable() = BR.model
    override fun getViewModel() = homeViewModel
}