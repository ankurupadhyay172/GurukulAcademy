package com.gca.mygca.model.response

data class TransportFeesResponseModel(val status: Int, val result: List<TransportFees>)

data class TransportFees(val id: String,val transport_id: String,val route_id: String,val fees: String,
val installment_id: String,val fees_title:String,val fees_timing: String,val fees_description: String)