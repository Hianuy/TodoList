package com.hianuy.todolist.project.ui.fragment

import android.app.AlertDialog
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Database
import com.google.android.material.snackbar.Snackbar
import com.hianuy.todolist.R
import com.hianuy.todolist.databinding.TaskListFragmentBinding
import com.hianuy.todolist.project.database.DatabaseHelper
import com.hianuy.todolist.project.database.dao.TaskDao
import com.hianuy.todolist.project.extension.navigationWithAnimation
import com.hianuy.todolist.project.repository.DatabaseDataSource
import com.hianuy.todolist.project.repository.TaskRepository
import com.hianuy.todolist.project.ui.adapter.TaskAdapter
import com.hianuy.todolist.project.ui.viewmodel.TaskListViewModel

class TaskListFragment : Fragment() {


    private var _binding: TaskListFragmentBinding? = null
    private val binding get() = _binding!!
    // metodo acessor customizado


    private val viewModel: TaskListViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val taskDao: TaskDao = DatabaseHelper
                    .getInstance(requireContext()).taskDao

                val repository: TaskRepository = DatabaseDataSource(taskDao)

                return TaskListViewModel(repository) as T
            }


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        setHasOptionsMenu(true)
        _binding = TaskListFragmentBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureClickInButton()
        observerViewModelEvents()

    }

    private fun observerViewModelEvents() {

        viewModel.allTaskEvent.observe(viewLifecycleOwner) { allTasks ->


            Log.i("ListOf", "observerViewModelEvents: $allTasks")
            val taskAdapter = TaskAdapter(allTasks)
                .apply {
                    onItemClick = { task ->
                        val direction = TaskListFragmentDirections
                            .actionTaskListFragmentToTaskFragment(task)

                        findNavController().navigationWithAnimation(direction)

                    }
                }
            binding.recyclerView.run {
                setHasFixedSize(true)
                adapter = taskAdapter
            }
            val listEmpty = binding.recyclerView.adapter?.itemCount == 0
            if (listEmpty) {
                binding.icNoTasks.visibility = View.VISIBLE
                binding.txtInfoNotHaveTasks.visibility = View.VISIBLE

            } else {
                binding.txtInfoNotHaveTasks.visibility = View.GONE
                binding.icNoTasks.visibility = View.GONE
            }
        }

    }

    private fun configureClickInButton() {
        binding.floating.setOnClickListener {
            findNavController()
                .navigationWithAnimation(
                    R.id
                        .action_taskListFragment_to_taskFragment
                )
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getTasks()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add_task -> Log.i("testando", "onOptionsItemSelected: add")
            R.id.item_delete_task -> Log.i("testando", "onOptionsItemSelected: delete ")
        }

        return super.onOptionsItemSelected(item)

    }


}