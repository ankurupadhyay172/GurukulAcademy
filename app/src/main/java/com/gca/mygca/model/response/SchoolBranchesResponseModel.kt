package com.gca.mygca.model.response

import androidx.room.Entity
import androidx.room.PrimaryKey

data class SchoolBranchesResponseModel(val status: Int, val result: List<Branches>)

@Entity
data class Branches(
    @PrimaryKey(autoGenerate = false)
    val id: String,val branch_name: String,val address: String,val image: String,val from: String?,val to:String,val color: String?)