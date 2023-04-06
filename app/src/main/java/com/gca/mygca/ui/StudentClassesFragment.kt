package com.gca.mygca.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.denzcoskun.imageslider.models.SlideModel
import com.gca.mygca.BR
import com.gca.mygca.R
import com.gca.mygca.base.BaseFragment
import com.gca.mygca.databinding.FragmentFrontScreenBinding
import com.gca.mygca.databinding.FragmentHomeBinding
import com.gca.mygca.databinding.FragmentTransportFeesBinding
import com.gca.mygca.model.adapter.SchoolBoardAdapter
import com.gca.mygca.model.adapter.SchoolBranchesAdapter
import com.gca.mygca.model.adapter.SchoolTransportAdapter
import com.gca.mygca.model.adapter.StudentMediumAdapter
import com.gca.mygca.model.request.StudentClassRequestModel
import com.gca.mygca.utils.Loader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StudentClassesFragment :BaseFragment<FragmentTransportFeesBinding,HomeViewModel>(){
    val homeViewModel: HomeViewModel by viewModels()
    val args: StudentClassesFragmentArgs by navArgs()
    @Inject
    lateinit var adapter: StudentMediumAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewDataBinding().recyclerView.adapter = adapter
            val loader = Loader.getLoader(requireActivity())
            loader.show()
            homeViewModel.getStudentClass(StudentClassRequestModel(args.boardId,args.mediumId) ).observe(viewLifecycleOwner){
                it.getErrorIfExists()?.let {
                    loader.cancel()
                }
                it.getValueOrNull().let {
                    loader.cancel()
                    adapter.submitList(it?.result)
                }
        }

        adapter.fees ={id,benefit->
            findNavController().navigate(StudentClassesFragmentDirections.actionStudentClassesFragmentToStudentFeesDetailsFragment(id,benefit))
        }

    }
    override fun getLayoutId() = R.layout.fragment_transport_fees
    override fun getBindingVariable() = BR.model
    override fun getViewModel() = homeViewModel
}