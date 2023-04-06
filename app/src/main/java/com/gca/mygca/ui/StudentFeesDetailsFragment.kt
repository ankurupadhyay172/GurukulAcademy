package com.gca.mygca.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.gca.mygca.BR
import com.gca.mygca.R
import com.gca.mygca.base.BaseFragment
import com.gca.mygca.databinding.*
import com.gca.mygca.model.adapter.StudentFeesAdapter
import com.gca.mygca.model.request.CommonRequestModel
import com.gca.mygca.utils.Loader
import com.gca.mygca.utils.setPrice
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StudentFeesDetailsFragment :BaseFragment<FragmentFeesDetailsBinding,HomeViewModel>(){
    val homeViewModel: HomeViewModel by viewModels()
    val args: StudentFeesDetailsFragmentArgs by navArgs()
    @Inject
    lateinit var adapter: StudentFeesAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewDataBinding().recyclerView.adapter = adapter
        getViewDataBinding().benifit.setPrice(args.feesBenifit)
        val loader = Loader.getLoader(requireActivity())
        loader.show()

        homeViewModel.getSchoolFees(CommonRequestModel(args.id)).observe(viewLifecycleOwner){
            it.getErrorIfExists()?.let {
                loader.cancel()
            }
            it.getValueOrNull()?.let {
                loader.cancel()
                Log.d("mylogvalue", "onViewCreated: "+it.result)
                adapter.submitList(it.result)
                if(it.status==1){
                    val list = it.result.map { it.amount.toInt() }
                    var total =0
                    for (i in list){
                        total +=i
                    }
                    getViewDataBinding().total.setPrice(total.toString())
                    val benefit = total - args.feesBenifit?.toInt()!!
                    getViewDataBinding().benefitFees.setPrice(benefit.toString())
                }
            }
        }
    }
    override fun getLayoutId() = R.layout.fragment_fees_details
    override fun getBindingVariable() = BR.model
    override fun getViewModel() = homeViewModel
}