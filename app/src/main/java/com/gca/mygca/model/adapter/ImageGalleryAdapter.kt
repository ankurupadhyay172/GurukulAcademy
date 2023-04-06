package com.gca.mygca.model.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.gca.mygca.R
import com.gca.mygca.base.BaseListAdapter
import com.gca.mygca.databinding.ItemGalleryBinding
import com.gca.mygca.model.response.MediaModel
import javax.inject.Inject

class ImageGalleryAdapter @Inject constructor():BaseListAdapter<MediaModel,ItemGalleryBinding>(DiffCallback()){

    class DiffCallback: DiffUtil.ItemCallback<MediaModel>(){
        override fun areItemsTheSame(oldItem: MediaModel, newItem: MediaModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MediaModel, newItem: MediaModel): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun createBinding(parent: ViewGroup): ItemGalleryBinding {
        return DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_gallery,parent,false)
    }

    override fun bind(binding: ItemGalleryBinding, item: MediaModel?) {
        binding.image = item?.path
    }

}