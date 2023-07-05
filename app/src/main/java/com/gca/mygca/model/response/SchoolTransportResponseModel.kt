package com.gca.mygca.model.response

data class SchoolTransportResponseModel(val status: Int, val result: List<Transport>)

data class Transport(val id: String,val title: String,val vehicle_no:String)