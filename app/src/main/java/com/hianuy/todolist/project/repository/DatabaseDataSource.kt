package com.hianuy.todolist.project.repository

import com.hianuy.todolist.project.database.dao.TaskDao
import com.hianuy.todolist.project.database.entity.Priority
import com.hianuy.todolist.project.database.entity.TaskEntity

class
DatabaseDataSource(private val taskDao: TaskDao) : TaskRepository {
    override suspend fun insertTask(
        title: String,
        isMade: Boolean,
        priority: Priority,
        deadline: String


    ): Long {
        val task = TaskEntity(
            title = title,
            isMade = isMade,
            priority = priority,
            deadline = deadline

        )
        return taskDao.insert(task)
    }

    override suspend fun updateTask(
        id: Long,
        title: String,
        isMade: Boolean,
        priority: Priority,
        deadline: String
    ) {

        val task = TaskEntity(
            id = id,
            title = title,
            isMade = isMade,
            priority = priority,
            deadline = deadline
        )
        return taskDao.update(task)
    }

    override suspend fun deleteTask(id: Long) {
        return taskDao.delete(id)
    }

    override suspend fun deleteAllTasks() {
        taskDao.deleteAllTasks()
    }

    override suspend fun getAllTasks(): List<TaskEntity> {
        return taskDao.getAllTasks()
    }
}