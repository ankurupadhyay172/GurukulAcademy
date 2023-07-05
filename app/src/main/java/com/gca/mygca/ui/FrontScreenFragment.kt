package com.gca.mygca.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.models.SlideModel
import com.gca.mygca.BR
import com.gca.mygca.R
import com.gca.mygca.base.BaseFragment
import com.gca.mygca.database.DatabaseManager
import com.gca.mygca.databinding.FragmentFrontScreenBinding
import com.gca.mygca.databinding.FragmentHomeBinding
import com.gca.mygca.model.adapter.SchoolBranchesAdapter
import com.gca.mygca.utils.Loader
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FrontScreenFragment :BaseFragment<FragmentFrontScreenBinding,HomeViewModel>(){
    val homeViewModel: HomeViewModel by activityViewModels()

    @Inject
    lateinit var adapter: SchoolBranchesAdapter

    @Inject
    lateinit var databaseManager: DatabaseManager
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewDataBinding().recyclerView.adapter = adapter

        CoroutineScope(Dispatchers.Main).launch {
            databaseManager.getFlowBranches().collect{
                adapter.submitList(it)
            }
        }

        homeViewModel.getSchoolBranch().observe(viewLifecycleOwner){
            it.getValueOrNull()?.let {

            }
        }
        adapter.open = {
            homeViewModel.selectedBranch = it?.id
            homeViewModel.selectedBranchModel = it
            findNavController().navigate(FrontScreenFragmentDirections.actionFrontScreenFragmentToHomeFragment())
        }

        getViewDataBinding().tvTransport.setOnClickListener {
            findNavController().navigate(FrontScreenFragmentDirections.actionFrontScreenFragmentToTransportScreenFragment())
        }

    }
    override fun getLayoutId() = R.layout.fragment_front_screen
    override fun getBindingVariable() = BR.model
    override fun getViewModel() = homeViewModel
}