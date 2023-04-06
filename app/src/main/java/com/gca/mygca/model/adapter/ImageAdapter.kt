package com.gca.mygca.model.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.core.view.isVisible
import com.gca.mygca.R
import com.gca.mygca.model.response.MediaModel
import com.gca.mygca.utils.MediaType
import com.gca.mygca.utils.setImage


class ImageAdapter(val list: List<MediaModel>,val context: Context):BaseAdapter() {

    var layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(p0: Int): Any {
        return list[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if(view==null){
            view = layoutInflater.inflate(R.layout.item_gallery,parent,false)
        }
        val image = view?.findViewById<ImageView>(R.id.imageView)
        val playIcon = view?.findViewById<ImageView>(R.id.playIcon)
        if(list[position].type==MediaType.VIDEO.type){
            image?.alpha = 0.5f
            playIcon?.isVisible = true
        }
        image?.setImage(list[position].path)

        return view!!
    }
}