package com.gca.mygca.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gca.mygca.model.response.Branches
import com.gca.mygca.model.response.StudentBoardResponseModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBranches(branches: List<Branches>)

    @Query("select * from Branches")
    fun getBranchesFlow():Flow<List<Branches>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBoard(boardResponseModel: List<StudentBoardResponseModel>)

    @Query("select * from StudentBoardResponseModel")
    fun getStudentBoardFlow():Flow<List<StudentBoardResponseModel>>
}