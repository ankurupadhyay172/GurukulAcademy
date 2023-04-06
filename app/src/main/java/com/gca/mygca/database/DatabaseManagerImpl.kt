package com.gca.mygca.database

import android.content.Context
import androidx.room.Room
import com.gca.mygca.model.response.Branches
import com.gca.mygca.model.response.StudentBoardResponseModel
import com.gca.mygca.model.response.StudentMediumResponseModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DatabaseManagerImpl @Inject constructor(@ApplicationContext context: Context):DatabaseManager{

    val db = Room.databaseBuilder(context,MyDatabase::class.java, "myDatabase").build()
    override fun addBranches(branches: List<Branches>) {
        CoroutineScope(Dispatchers.IO).launch {
            db.getMyDatabaseDao().addBranches(branches)
        }
    }

    override suspend fun getFlowBranches(): Flow<List<Branches>> {
        return db.getMyDatabaseDao().getBranchesFlow()
    }

    override fun addBoard(boardResponseModel: List<StudentBoardResponseModel>) {
        CoroutineScope(Dispatchers.IO).launch {
            db.getMyDatabaseDao().addBoard(boardResponseModel)
        }
    }

    override suspend fun getBoard(): Flow<List<StudentBoardResponseModel>> {
        return db.getMyDatabaseDao().getStudentBoardFlow()
    }


}