package com.gca.mygca.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.models.SlideModel
import com.gca.mygca.BR
import com.gca.mygca.R
import com.gca.mygca.base.BaseFragment
import com.gca.mygca.database.DatabaseManager
import com.gca.mygca.databinding.FragmentFrontScreenBinding
import com.gca.mygca.databinding.FragmentHomeBinding
import com.gca.mygca.databinding.FragmentTransportFeesBinding
import com.gca.mygca.model.adapter.SchoolBoardAdapter
import com.gca.mygca.model.adapter.SchoolBranchesAdapter
import com.gca.mygca.model.adapter.SchoolTransportAdapter
import com.gca.mygca.utils.Loader
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class StudentBoardFragment :BaseFragment<FragmentTransportFeesBinding,HomeViewModel>(){
    val homeViewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var adapter: SchoolBoardAdapter

    @Inject
    lateinit var databaseManager: DatabaseManager
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewDataBinding().recyclerView.adapter = adapter
        val loader =Loader.getLoader(requireActivity())
        loader.show()

        CoroutineScope(Dispatchers.Main).launch {
            databaseManager.getBoard().collect{
                loader.cancel()
                adapter.submitList(it)
            }
        }
            homeViewModel.getStudentBoard().observe(viewLifecycleOwner){
                it.getValueOrNull().let {
                    //adapter.submitList(it?.result)

                }
        }

        adapter.open = {
            if(it=="2"){
                findNavController().navigate(StudentBoardFragmentDirections.actionStudentBoardFragmentToStudentClassesFragment(it))
            }else
            findNavController().navigate(StudentBoardFragmentDirections.actionStudentBoardFragmentToStudentMediumFragment(it))
        }


    }
    override fun getLayoutId() = R.layout.fragment_transport_fees
    override fun getBindingVariable() = BR.model
    override fun getViewModel() = homeViewModel
}