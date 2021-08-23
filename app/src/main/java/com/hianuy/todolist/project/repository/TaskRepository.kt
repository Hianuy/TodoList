package com.hianuy.todolist.project.repository

import com.hianuy.todolist.project.database.entity.Priority
import com.hianuy.todolist.project.database.entity.TaskEntity

interface TaskRepository {

    suspend fun insertTask(
        title: String,
        isMade: Boolean,
        priority: Priority,
        deadline: String
    ): Long

    suspend fun updateTask(
        id: Long,
        title: String,
        isMade: Boolean,
        priority: Priority,
        deadline: String
    )

    suspend fun deleteTask(id: Long)

    suspend fun deleteAllTasks()

    suspend fun getAllTasks(): List<TaskEntity>


}