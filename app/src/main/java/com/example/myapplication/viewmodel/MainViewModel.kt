package com.example.myapplication.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.myapplication.ToDoApplication
import com.example.myapplication.core.DatabaseService
import com.example.myapplication.core.Routing
import com.example.myapplication.core.StateUpdater
import com.example.myapplication.model.TaskDataModel
import com.example.myapplication.util.FunctionId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(val databaseService: DatabaseService) : ViewModel() {

    var todoListMLiveData = databaseService.getAllTodos()

    fun getTodosSortedWithTargetDate() {
        todoListMLiveData = databaseService.getAllTodos() as MutableLiveData<List<TaskDataModel>>

    }

    fun deleteTask(index: Int) {
        val item = todoListMLiveData.value?.get(index)
        viewModelScope.launch(Dispatchers.IO) {
            if (item != null) {
                databaseService.delete(item)
            }
        }
    }

    fun updateStatus(value: String?, id: Long) {
        val dataModel = todoListMLiveData.value?.find { it.createdAt==id }
        Log.e("Test","$value $id da $dataModel")
        if (dataModel==null||value.isNullOrEmpty())return
        viewModelScope.launch(Dispatchers.IO) {
            databaseService.updateTodo(
                dataModel.title,
                value,
                dataModel.timeStamp,
                dataModel.time,
                dataModel.timeFormat,
                dataModel.createdAt
            )
        }
    }

    val stateUpdater = object : StateUpdater() {
        override val handler: Routing
            get() = object : Routing {
                override fun executePage(id: String, bundle: Bundle) {
                    TODO("Not yet implemented")
                }

                override fun executeFun(id: String, bundle: Bundle) {
                    when (id) {
                        FunctionId.DELETE_TASK.value -> {
                            // saveTask()
                            deleteTask(bundle.getInt("index"))
                        }
                        FunctionId.UPDATE_STATUS.value -> {
                            Log.e("TEST","$bundle")
                            updateStatus(bundle.getString("status"), bundle.getLong("id"))
                        }
                        // else ->this@AddToDoViewModel.handler.executeFun(id, bundle)
                    }
                }


            }

    }


    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras
                val savedStateHandle = extras.createSavedStateHandle()

                return MainViewModel(
                    (application as ToDoApplication).databaseService
                ) as T
            }
        }
    }
}