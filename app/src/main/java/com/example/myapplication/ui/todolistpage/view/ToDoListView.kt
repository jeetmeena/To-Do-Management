package com.example.myapplication.ui.todolistpage.view

import android.util.Log
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.myapplication.R
import com.example.myapplication.adapter.TaskItemAdapter
import com.example.myapplication.core.StateUpdater
import com.example.myapplication.databinding.ToDoListPageBinding
import com.example.myapplication.model.TaskDataModel

class ToDoListView(val stateUpdater: StateUpdater, val binding: ToDoListPageBinding) :
    Observer<List<TaskDataModel>> {
    private var taskItemAdapter: TaskItemAdapter? = null
    private var sortingOrder = R.id.sort_by_target

    init {
        taskItemAdapter = TaskItemAdapter(stateUpdater)
        binding.rvTodos.apply {
            adapter = taskItemAdapter
            //setHasFixedSize(true)
        }

        binding.topAppBar.setNavigationOnClickListener {
            binding.root.findNavController().navigateUp()
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.sort_by_ace_date -> {
                    sortingOrder = R.id.sort_by_ace_date
                    taskItemAdapter?.apply {
                        updateItem(getSortedList(taskList))
                    }
                    true
                }
                R.id.sort_by_target -> {

                    sortingOrder = R.id.sort_by_target
                    taskItemAdapter?.apply {
                        updateItem(getSortedList(taskList))
                    }
                    true
                }

                else -> {
                    false
                }
            }
        }
        binding.fab.setOnClickListener {
            // Respond to FAB click
            //navController.navigate(com.myflex.app.R.id.dashboardFragment)
            binding.root.findNavController().navigate(R.id.addToDoFragment)
        }
    }

    private fun bindData(value: List<TaskDataModel>) {
        Log.e("Test", value.toString())

        taskItemAdapter?.updateItem(getSortedList(value))
    }

    override fun onChanged(value: List<TaskDataModel>) {
        bindData(value)
    }

    private fun getSortedList(value: List<TaskDataModel>): List<TaskDataModel> {
        val newList = if (sortingOrder == R.id.sort_by_ace_date) {
            value.sortedBy { it.createdAt }
        } else {
            value.sortedBy { it.timeStamp }
        }
        return newList
    }
}