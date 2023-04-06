package com.gca.mygca.model


data class SliderResponseModel(val status: Int,val result: List<Banner>)

data class Banner(val id: String,val title: String,val image: String,val slider_order: Int)