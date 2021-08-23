package com.hianuy.todolist.project.database.dao

import androidx.room.*
import com.hianuy.todolist.project.database.entity.TaskEntity


@Dao
interface TaskDao {

    // dao pattern que surgiu para separar a lógica de négocio da logica da persistência
    // de dados

    @Insert
    suspend fun insert(task: TaskEntity): Long

    @Update
    suspend fun update(task: TaskEntity)

    @Query("DELETE FROM task WHERE id = :id")
    suspend fun delete(id:Long)

    @Query("DELETE FROM task")
    suspend fun deleteAllTasks()

    @Query("SELECT * FROM task")
    suspend fun getAllTasks() : List<TaskEntity>
}