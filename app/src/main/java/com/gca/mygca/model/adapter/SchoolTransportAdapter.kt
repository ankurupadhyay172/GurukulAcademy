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
import com.gca.mygca.model.response.Branches
import com.gca.mygca.model.response.MediaModel
import com.gca.mygca.model.response.Transport
import javax.inject.Inject

class SchoolTransportAdapter @Inject constructor():BaseListAdapter<Transport,ItemSchoolTransportBinding>(DiffCallback()){

    var open:((id:String?,Transport?)->Unit)? = null
    var options:((id:String?,Transport?)->Unit)? = null
    class DiffCallback: DiffUtil.ItemCallback<Transport>(){
        override fun areItemsTheSame(oldItem: Transport, newItem: Transport): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Transport, newItem: Transport): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun createBinding(parent: ViewGroup): ItemSchoolTransportBinding {
        return DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_school_transport,parent,false)
    }

    override fun bind(binding: ItemSchoolTransportBinding, item: Transport?) {
        binding.option.text = item?.title
        binding.option.setOnClickListener {
            open?.invoke(item?.id,item)
        }
        binding.option.setOnLongClickListener {
            options?.invoke(item?.id,item)
            return@setOnLongClickListener true
        }
    }

}