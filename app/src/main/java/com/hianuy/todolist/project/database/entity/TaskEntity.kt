package com.hianuy.todolist.project.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "task")
data class TaskEntity(
    @PrimaryKey (autoGenerate = true) val id : Long = 0,
    val title: String,
    val isMade: Boolean,
    val priority: Priority,
    val deadline: String

) : Parcelable
