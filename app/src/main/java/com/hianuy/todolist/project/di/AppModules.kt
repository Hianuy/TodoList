package com.hianuy.todolist.project.di

import androidx.room.Room
import com.hianuy.todolist.project.database.DatabaseHelper
import com.hianuy.todolist.project.repository.DatabaseDataSource
import com.hianuy.todolist.project.ui.viewmodel.TaskListViewModel
import com.hianuy.todolist.project.ui.viewmodel.TaskViewModel
import com.hianuy.todolist.project.usercase.TaskUserCase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModules = module {


    single {
        Room.databaseBuilder(
            androidContext(),
            DatabaseHelper::class.java,
            "app_database"
        ).allowMainThreadQueries().build()

    }

    factory {
        get<DatabaseHelper>().taskDao
    }


    factory<DatabaseDataSource> {
        DatabaseDataSource(get())
    }
    factory<TaskUserCase> {
        TaskUserCase(repository = get<DatabaseDataSource>())
    }

    viewModel<TaskViewModel> {
        TaskViewModel(get())
    }

    viewModel<TaskListViewModel> {
        TaskListViewModel(get())
    }


}