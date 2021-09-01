package com.hianuy.todolist.project.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hianuy.todolist.project.database.entity.TaskEntity
import com.hianuy.todolist.project.repository.TaskRepository
import com.hianuy.todolist.project.usercase.TaskUserCase
import kotlinx.coroutines.launch

class TaskListViewModel(
      private val userCase: TaskUserCase
        ) : ViewModel() {

    private val _allTaskEvent = MutableLiveData<List<TaskEntity>>()
    val allTaskEvent: LiveData<List<TaskEntity>> get() = _allTaskEvent




    // toda vida viewModel tem seu scopo de couroutines
    // isso facilita muito pois eu não preciso me preocupar com desalocação
    // de recursos apos o uso desse sc
    fun getTasks() = viewModelScope.launch {
        _allTaskEvent.postValue(userCase.getAllTasks())
    }


}