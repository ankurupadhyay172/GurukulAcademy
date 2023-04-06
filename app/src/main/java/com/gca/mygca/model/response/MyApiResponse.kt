package com.gca.mygca.model.response

data class MyApiResponse<T> (val status: Int, val result: List<T>,val msg: String?)