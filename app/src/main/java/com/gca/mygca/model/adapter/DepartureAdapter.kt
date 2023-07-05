package com.gca.mygca.model.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.gca.mygca.R
import com.gca.mygca.base.BaseListAdapter
import com.gca.mygca.databinding.ItemDepartureBinding
import com.gca.mygca.databinding.ItemGalleryBinding
import com.gca.mygca.databinding.ItemTransportFeesBinding
import com.gca.mygca.model.response.Departure

import com.gca.mygca.model.response.SchoolFeesResponseModel
import com.gca.mygca.utils.setImage
import com.gca.mygca.utils.setPrice
import javax.inject.Inject

class DepartureAdapter @Inject constructor():BaseListAdapter<Departure,ItemDepartureBinding>(DiffCallback()){

    var open:((id:String?)->Unit)? = null
    class DiffCallback: DiffUtil.ItemCallback<Departure>(){
        override fun areItemsTheSame(oldItem: Departure, newItem: Departure): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Departure, newItem: Departure): Boolean {
            return oldItem == newItem
        }

    }

    override fun createBinding(parent: ViewGroup): ItemDepartureBinding {
        return DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_departure,parent,false)
    }

    override fun bind(binding: ItemDepartureBinding, item: Departure?) {
        binding.itemNewsImvNews.setImage(item?.image)
        binding.itemNewsTvTitle.text = "Departure No. : ${item?.id}\nStudent Name : ${item?.name}\n" +
                "Student Class : ${item?.student_class}\nStudent Section : ${item?.student_section}\nMobile No : ${item?.mobileNo}\nReceiver Name : ${item?.receiver_name}\n" +
                "Relation : ${item?.relation_with_student}\nIncharge Name : ${item?.inChargeName}\nVehicle No. : ${item?.vehicle_no}\n" +
                "Time : ${item?.date} ${item?.time}"

        binding.itemNewsImvNews.setOnClickListener {
            open?.invoke(item?.image)
        }
    }

}