package com.hianuy.todolist.project.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hianuy.todolist.project.database.dao.TaskDao
import com.hianuy.todolist.project.database.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)

abstract class DatabaseHelper : RoomDatabase() {

    abstract val taskDao: TaskDao


//    companion object {
//        private var instance: DatabaseHelper? = null
//
//        // singleton
//        // pois cada instancia do database é muito cara é custosa
//        //
//        fun getInstance(context: Context): DatabaseHelper {
//            val liveInstance = instance
//            if (liveInstance != null)
//                return liveInstance
//
//            synchronized(this) {
//                val buildingInstance = Room.databaseBuilder(
//                    context.applicationContext,
//                    DatabaseHelper::class.java,
//                    "app_database"
//                ).build()
//                instance = buildingInstance
//                return buildingInstance
//            }
//        }
//    }


}