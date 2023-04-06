package com.gca.mygca.model.response

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StudentBoardResponseModel(
    @PrimaryKey(autoGenerate = false)
    val id: String,val title: String)