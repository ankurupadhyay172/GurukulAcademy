package com.gca.mygca.model.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.gca.mygca.R
import com.gca.mygca.base.BaseListAdapter
import com.gca.mygca.databinding.ItemGalleryBinding
import com.gca.mygca.databinding.ItemTransportFeesBinding

import com.gca.mygca.model.response.SchoolFeesResponseModel
import com.gca.mygca.utils.setPrice
import javax.inject.Inject

class StudentFeesAdapter @Inject constructor():BaseListAdapter<SchoolFeesResponseModel,ItemTransportFeesBinding>(DiffCallback()){

    class DiffCallback: DiffUtil.ItemCallback<SchoolFeesResponseModel>(){
        override fun areItemsTheSame(oldItem: SchoolFeesResponseModel, newItem: SchoolFeesResponseModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SchoolFeesResponseModel, newItem: SchoolFeesResponseModel): Boolean {
            return oldItem == newItem
        }

    }

    override fun createBinding(parent: ViewGroup): ItemTransportFeesBinding {
        return DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_transport_fees,parent,false)
    }

    override fun bind(binding: ItemTransportFeesBinding, item: SchoolFeesResponseModel?) {
        binding.month.text = item?.fees_title
        binding.payTerm1.text = item?.fees_timing
        binding.termFirstFees.setPrice(item?.amount)
        binding.tandcfee1.text = item?.fees_description
    }

}