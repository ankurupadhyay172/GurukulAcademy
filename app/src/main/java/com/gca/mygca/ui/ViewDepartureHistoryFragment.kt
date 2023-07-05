package com.gca.mygca.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.denzcoskun.imageslider.models.SlideModel
import com.gca.mygca.BR
import com.gca.mygca.R
import com.gca.mygca.base.BaseFragment
import com.gca.mygca.databinding.*
import com.gca.mygca.model.adapter.DepartureAdapter
import com.gca.mygca.model.adapter.SchoolBoardAdapter
import com.gca.mygca.model.adapter.SchoolBranchesAdapter
import com.gca.mygca.model.adapter.SchoolTransportAdapter
import com.gca.mygca.model.request.CommonRequestModel
import com.gca.mygca.model.request.ReadDepartureRequestModel
import com.gca.mygca.model.response.Departure
import com.gca.mygca.model.response.MediaModel
import com.gca.mygca.utils.Loader
import com.gca.mygca.utils.MediaType
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ViewDepartureHistoryFragment :BaseFragment<ViewDepartureHistoryFragmentBinding,HomeViewModel>(){
    val homeViewModel: HomeViewModel by activityViewModels()
    val args:ViewDepartureHistoryFragmentArgs by navArgs()
    @Inject
    lateinit var adapter: DepartureAdapter
    lateinit var list:List<Int>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewDataBinding().recyclerView.adapter = adapter
        //findId()
        adapter.open = {
            val list = Gson().toJson(listOf(MediaModel("","",it,MediaType.IMAGE.type,"")))
            findNavController().navigate(ViewDepartureHistoryFragmentDirections.actionViewDepartureHistoryFragmentToImageViewerFragment(list,0))
        }
        val loader = Loader.getLoader(requireActivity())

        getViewDataBinding().ivForward.setOnClickListener {
            loader.show()
            getViewDataBinding().date.text = homeViewModel.incrementDate()
            homeViewModel.getDeparture(ReadDepartureRequestModel(homeViewModel.selectedBranch,homeViewModel.selectedDate)).observe(viewLifecycleOwner){
                it.getValueOrNull()?.let {
                    loader.cancel()
                    adapter.submitList(it.result)
                    getViewDataBinding().isEmpty = it.status==0
                }
            }
        }
        getViewDataBinding().ivBack.setOnClickListener {
            loader.show()
            getViewDataBinding().date.text = homeViewModel.decrementDate()
            homeViewModel.getDeparture(ReadDepartureRequestModel(homeViewModel.selectedBranch,homeViewModel.selectedDate)).observe(viewLifecycleOwner){
                it.getValueOrNull()?.let {
                    loader.cancel()
                    adapter.submitList(it.result)
                    getViewDataBinding().isEmpty = it.status==0
                }
            }
        }
        getViewDataBinding().date.text = homeViewModel.selectedDate
        loader.show()

        if (args.type==0){
            homeViewModel.getDeparture(ReadDepartureRequestModel(homeViewModel.selectedBranch,homeViewModel.selectedDate)).observe(viewLifecycleOwner){
                it.getValueOrNull()?.let {
                    loader.cancel()
                    adapter.submitList(it.result)
                    getViewDataBinding().isEmpty = it.status==0
                }
            }
        }else{
            getViewDataBinding().date.visibility = View.GONE
            getViewDataBinding().ivForward.visibility = View.GONE
            getViewDataBinding().ivBack.visibility = View.GONE
            homeViewModel.getAllDeparture(CommonRequestModel(homeViewModel.selectedBranch)).observe(viewLifecycleOwner){
                it.getValueOrNull()?.let {
                    loader.cancel()
                    adapter.submitList(it.result)
                    list = it.result.map { it.id.toInt() }
                    getViewDataBinding().isEmpty = it.status==0
                }
            }
        }
    }

    private fun findId() {
        getViewDataBinding().search.doOnTextChanged { text, start, before, count ->

            val input = if(text.toString() == "") 0 else text.toString().toInt()
            val output = homeViewModel.findElement(list,input)
            Log.d("mylog23", "element: $output")

        }
    }

    override fun getLayoutId() = R.layout.view_departure_history_fragment
    override fun getBindingVariable() = BR.model
    override fun getViewModel() = homeViewModel
}