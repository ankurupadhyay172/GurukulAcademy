package com.gca.mygca.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.models.SlideModel
import com.gca.mygca.BR
import com.gca.mygca.R
import com.gca.mygca.base.BaseFragment
import com.gca.mygca.databinding.DepartureMenuFragmentBinding
import com.gca.mygca.databinding.FragmentFrontScreenBinding
import com.gca.mygca.databinding.FragmentHomeBinding
import com.gca.mygca.databinding.FragmentTransportFeesBinding
import com.gca.mygca.model.adapter.SchoolBoardAdapter
import com.gca.mygca.model.adapter.SchoolBranchesAdapter
import com.gca.mygca.model.adapter.SchoolTransportAdapter
import com.gca.mygca.utils.Loader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DepartureMenuFragment :BaseFragment<DepartureMenuFragmentBinding,HomeViewModel>(){
    val homeViewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var adapter: SchoolBoardAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewDataBinding().view.setOnClickListener {
            findNavController().navigate(DepartureMenuFragmentDirections.actionDepartureMenuFragmentToViewDepartureHistoryFragment())
        }
        getViewDataBinding().add.setOnClickListener {
            findNavController().navigate(DepartureMenuFragmentDirections.actionDepartureMenuFragmentToEarlyDepartureFormFragment())
        }
    }
    override fun getLayoutId() = R.layout.departure_menu_fragment
    override fun getBindingVariable() = BR.model
    override fun getViewModel() = homeViewModel
}