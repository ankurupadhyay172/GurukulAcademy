package com.gca.mygca.utils

import androidx.annotation.IdRes
import com.gca.mygca.R

enum class PageConfiguration(
    val id:Int,
    val toolbarVisible:Boolean=true){
    FRONT(
        R.id.frontScreenFragment,
        false
    ),OTHER(0);


    companion object{
        fun getConfiguration(@IdRes id: Int):PageConfiguration{
            return values().firstOrNull{it.id==id}?:OTHER
        }
    }
}