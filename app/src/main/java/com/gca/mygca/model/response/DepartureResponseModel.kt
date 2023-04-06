package com.gca.mygca.model.response

data class DepartureResponseModel(val status: Int, val result: List<Departure>)

data class Departure(val id: String,val name: String,val student_class: String,val student_section: String,val receiver_name: String,val relation_with_student: String?,
val image:String,val timestamp:String?,val branch_id:String?,val mobileNo:String,val inChargeName:String,val date:String,val time:String)