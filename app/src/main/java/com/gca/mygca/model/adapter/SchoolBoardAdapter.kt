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
import com.gca.mygca.model.response.StudentBoardResponseModel
import com.gca.mygca.model.response.Transport
import javax.inject.Inject

class SchoolBoardAdapter @Inject constructor():BaseListAdapter<StudentBoardResponseModel,ItemSchoolTransportBinding>(DiffCallback()){

    var open:((id:String?)->Unit)? = null
    class DiffCallback: DiffUtil.ItemCallback<StudentBoardResponseModel>(){
        override fun areItemsTheSame(oldItem: StudentBoardResponseModel, newItem: StudentBoardResponseModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: StudentBoardResponseModel, newItem: StudentBoardResponseModel): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun createBinding(parent: ViewGroup): ItemSchoolTransportBinding {
        return DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_school_transport,parent,false)
    }

    override fun bind(binding: ItemSchoolTransportBinding, item: StudentBoardResponseModel?) {
        binding.option.text = item?.title
        binding.option.setOnClickListener {
            open?.invoke(item?.id)
        }
    }

}