package com.gca.mygca.test

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gca.mygca.model.Banner
import com.gca.mygca.model.response.Departure

class MyPagingAdapter:PagingDataAdapter<Banner,MyPagingAdapter.MyViewHolder>(DiffCallback()) {

    class DiffCallback: DiffUtil.ItemCallback<Banner>(){
        override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean {
            return oldItem == newItem
        }

    }
    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

    }
    
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        TODO("Not yet implemented")
    }


}