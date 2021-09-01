package com.hianuy.todolist.project.ui.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView

import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.hianuy.todolist.R
import com.hianuy.todolist.R.string.*
import com.hianuy.todolist.databinding.TaskFragmentBinding
import com.hianuy.todolist.project.database.DatabaseHelper
import com.hianuy.todolist.project.database.dao.TaskDao
import com.hianuy.todolist.project.database.entity.Priority
import com.hianuy.todolist.project.extension.toText
import com.hianuy.todolist.project.helper.CustomItems
import com.hianuy.todolist.project.repository.DatabaseDataSource
import com.hianuy.todolist.project.repository.TaskRepository
import com.hianuy.todolist.project.ui.adapter.SpinnerAdapter
import com.hianuy.todolist.project.ui.viewmodel.TaskViewModel
import java.util.*
import com.hianuy.todolist.project.helper.*
import org.koin.android.ext.android.inject

class TaskFragment : Fragment(), AdapterView.OnItemSelectedListener {


    private var _binding: TaskFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: TaskFragmentArgs by navArgs()
    private lateinit var customItems: CustomItems


//    private val viewModel: TaskViewModel by viewModels {
//        object : ViewModelProvider.Factory {
//            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//                val taskDao: TaskDao = DatabaseHelper
//                    .getInstance(requireContext()).taskDao
//
//                val repository: TaskRepository = DatabaseDataSource(taskDao)
//
//                return TaskViewModel(repository) as T
//            }
//
//
//
//        }
//    }

    private val viewModel: TaskViewModel by inject()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setSpinner()


        val priority: Priority
        args.task?.let { task ->
            binding.edtTitle.text = task.title.toText()
            binding.edtDeadline.text = task.deadline.toText()
            priority = task.priority
            binding.spinnerPriority.setSelection(parsePriorityToInt(priority))


        }




        observerEvent()
    }


    private fun observerEvent() {

        viewModel.taskStateEventData.observe(viewLifecycleOwner) { taskState ->

            when (taskState) {
                is TaskViewModel.TaskState.Inserted,
                is TaskViewModel.TaskState.Updated,
                is TaskViewModel.TaskState.Delete -> {
                    findNavController().popBackStack()

                }
            }
        }
        viewModel.messageEventData.observe(viewLifecycleOwner) { responseEvent ->
            Snackbar.make(
                requireView(),
                responseEvent,
                Snackbar.LENGTH_LONG
            ).show()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = TaskFragmentBinding.inflate(inflater, container, false)
        DatePicker(requireContext(), binding.edtDeadline).pickDate()
        return binding.root


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add_task -> addTask()
            R.id.item_delete_task -> viewModel.deleteTask(args.task!!.id)

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addTask() {
        val title = binding.edtTitle.text.toString()
        val date = binding.edtDeadline.text.toString()
        val priority = binding.spinnerPriority.selectedItemPosition

        viewModel.addOrUpdateTask(
            title, false, date,
            parseIntToPriority(priority), args.task?.id ?: 0
        )

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        if (args.task == null) {
            val findItem = menu.findItem(R.id.item_delete_task)
            findItem.isVisible = false

        } else if (args.task != null) {
            (requireActivity() as AppCompatActivity).supportActionBar?.title = "Edit"
            val findItem = menu.findItem(R.id.item_delete_task)
            findItem.isVisible = true

        }

        super.onPrepareOptionsMenu(menu)
    }

    private fun setSpinner() {
        val customList: ArrayList<CustomItems?> = ArrayList()
        customList.add(
            CustomItems(
                Priority.HIGH.toString(),
                R.drawable.ic_circle_red
            )
        )
        customList.add(
            CustomItems(
                Priority.MEDIUM.toString(),
                R.drawable.ic_circle_yellow
            )
        )
        customList.add(
            CustomItems(
                Priority.LOW.toString(),
                R.drawable.ic_circle_green
            )
        )

        val customAdapter = SpinnerAdapter(requireContext(), customList = customList)

        binding.spinnerPriority.adapter = customAdapter
        binding.spinnerPriority.onItemSelectedListener = this
    }

    private fun parseIntToPriority(priority: Int): Priority {

        return when (priority) {
            0 -> Priority.HIGH
            1 -> Priority.MEDIUM
            2 -> Priority.LOW
            else -> Priority.LOW
        }
    }

    private fun parsePriorityToInt(priority: Priority): Int {
        return when (priority) {
            Priority.HIGH -> 0
            Priority.MEDIUM -> 1
            Priority.LOW -> 2
        }


    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        customItems = parent!!.selectedItem as CustomItems
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }


}