package com.gca.mygca.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gca.mygca.model.response.Branches
import com.gca.mygca.model.response.StudentBoardResponseModel

@Database(entities = [Branches::class,StudentBoardResponseModel::class], version = 1, exportSchema = true)
abstract class MyDatabase: RoomDatabase() {
    abstract fun getMyDatabaseDao():MyDao
}