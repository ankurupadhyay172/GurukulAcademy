package com.gca.mygca.utils

enum class MediaType(val type:Int){
    IMAGE(1),
    VIDEO(2)
}

enum class ActionType(val type: String){
    ADD("add"),
    EDIT("edit"),
    DELETE("delete")
}