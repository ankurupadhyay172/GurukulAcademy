package com.gca.mygca.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.gca.mygca.R

@BindingAdapter("setImage")
fun ImageView.setImage(url: String?){
    url?.let {
        Glide.with(this.context).load(url)
            .placeholder(R.drawable.images)
            .into(this)
    }
}

@BindingAdapter("setPrice")
fun TextView.setPrice(price: String?){
    this.text = "₹ $price"
}