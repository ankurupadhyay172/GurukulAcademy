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
import com.gca.mygca.databinding.ItemTransportFeesBinding
import com.gca.mygca.model.response.Branches
import com.gca.mygca.model.response.MediaModel
import com.gca.mygca.model.response.Transport
import com.gca.mygca.model.response.TransportFees
import com.gca.mygca.utils.setPrice
import javax.inject.Inject

class TransportFeesAdapter @Inject constructor():BaseListAdapter<TransportFees,ItemTransportFeesBinding>(DiffCallback()){

    var open:((id:String?)->Unit)? = null
    class DiffCallback: DiffUtil.ItemCallback<TransportFees>(){
        override fun areItemsTheSame(oldItem: TransportFees, newItem: TransportFees): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TransportFees, newItem: TransportFees): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun createBinding(parent: ViewGroup): ItemTransportFeesBinding {
        return DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_transport_fees,parent,false)
    }

    override fun bind(binding: ItemTransportFeesBinding, item: TransportFees?) {
       binding.month.text = item?.fees_title
       binding.payTerm1.text = item?.fees_timing
       binding.termFirstFees.setPrice(item?.fees)
       binding.tandcfee1.text = item?.fees_description
    }

}