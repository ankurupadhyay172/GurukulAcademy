package com.gca.mygca.database

import com.gca.mygca.model.response.Branches
import com.gca.mygca.model.response.StudentBoardResponseModel
import com.gca.mygca.model.response.StudentMediumResponseModel
import kotlinx.coroutines.flow.Flow

interface DatabaseManager {

    fun addBranches(branches: List<Branches>)
    suspend fun getFlowBranches():Flow<List<Branches>>
    fun addBoard(boardResponseModel: List<StudentBoardResponseModel>)
    suspend fun getBoard():Flow<List<StudentBoardResponseModel>>

}