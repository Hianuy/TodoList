package com.hianuy.todolist.project.usercase

import com.hianuy.todolist.project.database.entity.Priority
import com.hianuy.todolist.project.database.entity.TaskEntity
import com.hianuy.todolist.project.repository.TaskRepository

class TaskUserCase(private val repository: TaskRepository) {


    suspend fun insertTask(
        title: String,
        isMade: Boolean,
        priority: Priority,
        deadline: String
    ): Long {
        return repository.insertTask(title, isMade, priority, deadline)
    }


    suspend fun updateTask(
        id: Long,
        title: String,
        isMade: Boolean,
        priority: Priority,
        deadline: String
    ) {
        repository.updateTask(id, title, isMade, priority, deadline)
    }

    suspend fun deleteTask(id: Long) {
        repository.deleteTask(id)
    }

    suspend fun getAllTasks(): List<TaskEntity> {
        return repository.getAllTasks()
    }


}