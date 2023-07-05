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
import com.gca.mygca.model.response.Branches
import com.gca.mygca.model.response.MediaModel
import javax.inject.Inject

class SchoolBranchesAdapter @Inject constructor():BaseListAdapter<Branches,ItemBranchesBinding>(DiffCallback()){
    var open:((id:Branches?)->Unit)? = null
    class DiffCallback: DiffUtil.ItemCallback<Branches>(){
        override fun areItemsTheSame(oldItem: Branches, newItem: Branches): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Branches, newItem: Branches): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun createBinding(parent: ViewGroup): ItemBranchesBinding {
        return DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_branches,parent,false)
    }

    override fun bind(binding: ItemBranchesBinding, item: Branches?) {
        val option = binding.option
        option.text = item?.branch_name
        //option.setBackgroundColor(Color.parseColor(item?.color))
        option.setOnClickListener {
            open?.invoke(item)

        }
    }

}