package com.gca.mygca.model.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.gca.mygca.R
import com.gca.mygca.base.BaseListAdapter
import com.gca.mygca.databinding.ItemBranchesBinding
import com.gca.mygca.databinding.ItemGalleryBinding
import com.gca.mygca.databinding.ItemSchoolTransportBinding
import com.gca.mygca.model.response.*
import javax.inject.Inject

class StudentMediumAdapter @Inject constructor():BaseListAdapter<StudentMediumResponseModel,ItemSchoolTransportBinding>(DiffCallback()){

    var open:((id:String?)->Unit)? = null
    var fees:((id:String?,benefit: String?)->Unit)?=null
    class DiffCallback: DiffUtil.ItemCallback<StudentMediumResponseModel>(){
        override fun areItemsTheSame(oldItem: StudentMediumResponseModel, newItem: StudentMediumResponseModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: StudentMediumResponseModel, newItem: StudentMediumResponseModel): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun createBinding(parent: ViewGroup): ItemSchoolTransportBinding {
        return DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_school_transport,parent,false)
    }

    override fun bind(binding: ItemSchoolTransportBinding, item: StudentMediumResponseModel?) {
        binding.option.text = item?.title
        binding.option.setOnClickListener {
            fees?.invoke(item?.id,item?.advance_fees_benifit)
        }
    }

}