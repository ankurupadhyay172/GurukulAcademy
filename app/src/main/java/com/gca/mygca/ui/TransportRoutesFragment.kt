package com.gca.mygca.ui

import android.app.AlertDialog
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
import com.gca.mygca.model.adapter.SchoolBranchesAdapter
import com.gca.mygca.model.adapter.SchoolTransportAdapter
import com.gca.mygca.model.request.AddRequestModel
import com.gca.mygca.model.request.CommonRequestModel
import com.gca.mygca.utils.ActionType
import com.gca.mygca.utils.Loader
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TransportRoutesFragment :BaseFragment<FragmentTransportFeesBinding,HomeViewModel>(){
    val homeViewModel: HomeViewModel by viewModels()
    val args:TransportRoutesFragmentArgs by navArgs()
    @Inject
    lateinit var adapter: SchoolTransportAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewDataBinding().recyclerView.adapter = adapter

            updateData()

        adapter.open ={it,model->
            findNavController().navigate(TransportRoutesFragmentDirections.actionTransportRoutesFragmentToTransportFeesFragment(args.id,it,model?.vehicle_no))
        }

        getViewDataBinding().fabAdd.visibility = View.VISIBLE

        adapter.options = {it,model->
            AlertDialog.Builder(requireContext()).setTitle(R.string.options)
                .setItems(R.array.options) { dialog, which ->
                    if (which==0) {
                        findNavController().navigate(TransportRoutesFragmentDirections.actionTransportRoutesFragmentToAddTransportRouteFragment(args.id,Gson().toJson(model)))
                    }
                    if (which==1){
                       homeViewModel.manageTransportRoute(AddRequestModel(it, type = ActionType.DELETE.type)).observe(viewLifecycleOwner){
                           it.getValueOrNull()?.let {
                               showToast("Item Deleted Successfully")
                               updateData()
                           }
                       }
                    }
                }.show()
        }
        getViewDataBinding().fabAdd.setOnClickListener {
            findNavController().navigate(TransportRoutesFragmentDirections.actionTransportRoutesFragmentToAddTransportRouteFragment(args.id))
        }

    }

    private fun updateData() {
        homeViewModel.getTransportRoute(CommonRequestModel(args.id) ).observe(viewLifecycleOwner){
            it.getValueOrNull().let {
                adapter.submitList(it?.result)
            }
        }
    }

    override fun getLayoutId() = R.layout.fragment_transport_fees
    override fun getBindingVariable() = BR.model
    override fun getViewModel() = homeViewModel
}