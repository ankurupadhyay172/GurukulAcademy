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
import com.gca.mygca.databinding.FragmentShowTransportFeesBinding
import com.gca.mygca.databinding.FragmentTransportFeesBinding
import com.gca.mygca.model.adapter.SchoolBranchesAdapter
import com.gca.mygca.model.adapter.SchoolTransportAdapter
import com.gca.mygca.model.adapter.TransportFeesAdapter
import com.gca.mygca.model.request.CommonRequestModel
import com.gca.mygca.model.request.TransportFeesRequestModel
import com.gca.mygca.utils.Loader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TransportFeesFragment :BaseFragment<FragmentShowTransportFeesBinding,HomeViewModel>(){
    val homeViewModel: HomeViewModel by viewModels()
    val args:TransportFeesFragmentArgs by navArgs()
    @Inject
    lateinit var adapter: TransportFeesAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewDataBinding().recyclerView.adapter = adapter

        getViewDataBinding().paidName.text = args.vehicleNo
            homeViewModel.getTransportFees(TransportFeesRequestModel(args.id,args.routeId) ).observe(viewLifecycleOwner){
                it.getValueOrNull().let {
                    adapter.submitList(it?.result)
                    if (it?.status==1){
                        val list = it.result.map { it.fees.toInt() }
                        var total =0
                        for (i in list){
                            total +=i
                        }
                        getViewDataBinding().totalFees.text = "REGISTRATION FEES = ₹500\nTotal Fees = ₹$total"
                    }
                }
        }


    }
    override fun getLayoutId() = R.layout.fragment_show_transport_fees
    override fun getBindingVariable() = BR.model
    override fun getViewModel() = homeViewModel
}