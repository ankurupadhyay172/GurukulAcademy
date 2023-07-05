package com.gca.mygca.ui.addforms

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gca.mygca.BR
import com.gca.mygca.R
import com.gca.mygca.base.BaseFragment
import com.gca.mygca.databinding.FragmentAddTransportRoutesBinding
import com.gca.mygca.databinding.FragmentEarlyDepartureFormBinding
import com.gca.mygca.model.request.AddRequestModel
import com.gca.mygca.model.response.Transport
import com.gca.mygca.ui.HomeViewModel
import com.gca.mygca.utils.ActionType
import com.gca.mygca.utils.Loader
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class AddTransportRouteFragment: BaseFragment<FragmentAddTransportRoutesBinding,HomeViewModel>() {

    val homeViewModel: HomeViewModel by activityViewModels()
    lateinit var loader:Dialog
    val args: AddTransportRouteFragmentArgs by navArgs()
    var model:Transport? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = Loader.getLoader(requireActivity())
        if (args.item!="na"){
            model = Gson().fromJson(args.item,Transport::class.java)
            getViewDataBinding().edtTitle.setText(model?.title)
            getViewDataBinding().edtVehicle.setText(model?.vehicle_no)
        }

        getViewDataBinding().submit.setOnClickListener {
            if (getViewDataBinding().edtTitle.text.isEmpty()){
                getViewDataBinding().edtTitle.error = "Please enter route"
            }else{

                loader.show()
                if(model==null){
                    homeViewModel.manageTransportRoute(AddRequestModel(args.id,getViewDataBinding().edtTitle.text.toString(), vehicle_no = getViewDataBinding().edtVehicle.text.toString(),ActionType.ADD.type)).observe(viewLifecycleOwner){
                        it.getErrorIfExists()?.let {
                            loader.cancel()
                            showToast(""+it.message)
                        }
                        it.getValueOrNull()?.let {
                            if(it.status==1){
                                showToast(""+it.result)
                                loader.cancel()
                                findNavController().popBackStack()
                            }
                        }

                    }
                }else{
                    homeViewModel.manageTransportRoute(AddRequestModel(model?.id, title = getViewDataBinding().edtTitle.text.toString(),getViewDataBinding().edtVehicle.text.toString(), type = ActionType.EDIT.type)).observe(viewLifecycleOwner){
                        it.getErrorIfExists()?.let {
                            loader.cancel()
                            showToast(""+it.message)
                        }
                        it.getValueOrNull()?.let {
                            if(it.status==1){
                                showToast("Item updated successfully")
                                loader.cancel()
                                findNavController().popBackStack()
                            }
                        }

                    }
                }
            }
        }



    }


    override fun getLayoutId() = R.layout.fragment_add_transport_routes
    override fun getBindingVariable() = BR.model
    override fun getViewModel() = homeViewModel
}