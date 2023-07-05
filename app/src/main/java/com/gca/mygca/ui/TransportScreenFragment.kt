package com.gca.mygca.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.models.SlideModel
import com.gca.mygca.BR
import com.gca.mygca.R
import com.gca.mygca.base.BaseFragment
import com.gca.mygca.databinding.FragmentFrontScreenBinding
import com.gca.mygca.databinding.FragmentHomeBinding
import com.gca.mygca.databinding.FragmentTransportFeesBinding
import com.gca.mygca.model.adapter.SchoolBranchesAdapter
import com.gca.mygca.model.adapter.SchoolTransportAdapter
import com.gca.mygca.utils.Loader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TransportScreenFragment :BaseFragment<FragmentTransportFeesBinding,HomeViewModel>(){
    val homeViewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var adapter: SchoolTransportAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewDataBinding().recyclerView.adapter = adapter

            homeViewModel.getTransport().observe(viewLifecycleOwner){
                it.getValueOrNull().let {
                    adapter.submitList(it?.result)
                }
        }

        //getViewDataBinding().fabAdd.visibility = View.VISIBLE
        adapter.open = {it,model->
            findNavController().navigate(TransportScreenFragmentDirections.actionTransportScreenFragmentToTransportRoutesFragment(it))
        }
        adapter.options = {it,model->
            AlertDialog.Builder(requireContext()).setTitle(R.string.options)
                .setItems(R.array.options) { dialog, which ->
                    if (which==0) {
                        showToast("click on edit ")
                    }
                    if (which==1){
                        showToast("click on delete ")
                    }
                }.show()
        }

    }
    override fun getLayoutId() = R.layout.fragment_transport_fees
    override fun getBindingVariable() = BR.model
    override fun getViewModel() = homeViewModel
}