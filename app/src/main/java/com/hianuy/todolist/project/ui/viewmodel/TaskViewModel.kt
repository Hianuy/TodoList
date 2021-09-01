package com.hianuy.todolist.project.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hianuy.todolist.R
import com.hianuy.todolist.R.string.*
import com.hianuy.todolist.project.database.entity.Priority
import com.hianuy.todolist.project.usercase.TaskUserCase
import kotlinx.coroutines.launch
import java.lang.reflect.Executable

class TaskViewModel(private val userCase: TaskUserCase) : ViewModel() {

    private val _taskStateEventData = MutableLiveData<TaskState>()
    val taskStateEventData: LiveData<TaskState>
        get() = _taskStateEventData

    private val _messageEventData = MutableLiveData<Int>()
    val messageEventData: LiveData<Int>
        get() = _messageEventData




    fun addOrUpdateTask(
        title: String, isMade: Boolean, deadline: String, priority: Priority, id: Long = 0
    ) {
        if (id > 0)
            updateTask(id, title, isMade, priority, deadline)
        else
            insertTask(title, isMade, deadline, priority)
    }

    private fun insertTask(
        title: String, isMade: Boolean = false, deadline: String, priority: Priority
    ) = viewModelScope.launch {
        try {
            val id = userCase.insertTask(title, isMade, priority, deadline)
            if (id > 0) {
                _taskStateEventData.value = TaskState.Inserted
                _messageEventData.value = successfully_to_inserted_task
            }


        } catch (exception: Exception) {
            Log.e("Error", "insertTask: ${exception.printStackTrace()}")
            _messageEventData.value = error_to_updated_task
        }

    }

    private fun updateTask(
        id: Long, title: String, isMade: Boolean, priority: Priority, deadline: String
    ) = viewModelScope.launch {
        try {
            userCase.updateTask(id, title, isMade, priority, deadline)
            _taskStateEventData.value = TaskState.Updated
            _messageEventData.value = successfully_to_updated_task

        } catch (exception: Exception) {
            Log.e("Error", "updateTask: ${exception.printStackTrace()}")
            _messageEventData.value = error_to_updated_task
        }

    }

    fun deleteTask(id: Long) = viewModelScope.launch {
        try {
            userCase.deleteTask(id)
            _taskStateEventData.value = TaskState.Delete
            _messageEventData.value = successfully_to_deleted_task

        } catch (exception: Exception) {
            Log.e("Error", "deleteTask: ${exception.stackTraceToString()}")
            _messageEventData.value = error_to_deleted_task

        }

    }

    sealed class TaskState {
        object Inserted : TaskState()
        object Updated : TaskState()
        object Delete : TaskState()
    }
}
