package com.gca.mygca.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.models.SlideModel
import com.gca.mygca.BR
import com.gca.mygca.R
import com.gca.mygca.base.BaseFragment
import com.gca.mygca.databinding.*
import com.gca.mygca.model.adapter.DepartureAdapter
import com.gca.mygca.model.adapter.SchoolBoardAdapter
import com.gca.mygca.model.adapter.SchoolBranchesAdapter
import com.gca.mygca.model.adapter.SchoolTransportAdapter
import com.gca.mygca.utils.Loader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ViewDepartureHistoryFragment :BaseFragment<ViewDepartureHistoryFragmentBinding,HomeViewModel>(){
    val homeViewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var adapter: DepartureAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewDataBinding().recyclerView.adapter = adapter
        homeViewModel.getDeparture().observe(viewLifecycleOwner){
            it.getValueOrNull()?.let {
                adapter.submitList(it.result)
            }
        }
    }
    override fun getLayoutId() = R.layout.view_departure_history_fragment
    override fun getBindingVariable() = BR.model
    override fun getViewModel() = homeViewModel
}