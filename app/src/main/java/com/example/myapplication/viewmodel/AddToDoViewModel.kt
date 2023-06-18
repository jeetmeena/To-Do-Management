package com.example.myapplication.viewmodel

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.myapplication.R
import com.example.myapplication.ToDoApplication
import com.example.myapplication.core.DatabaseService
import com.example.myapplication.core.Routing
import com.example.myapplication.core.StateUpdater
import com.example.myapplication.model.TaskDataModel
import com.example.myapplication.ui.addtodopage.AddTodoModel
import com.example.myapplication.ui.addtodopage.ErrorModel
import com.example.myapplication.util.FunctionId
import com.example.myapplication.util.TaskStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AddToDoViewModel(val databaseService: DatabaseService, savedStateHandle: SavedStateHandle) : ViewModel() {
    lateinit var handler:Routing
    val taskDataMLiveData=MutableLiveData<AddTodoModel>()
    fun getData(){
        val taskDataModel=TaskDataModel("",TaskStatus.IN_PROGRESS.value,0,"","",0)
        val errorModel=ErrorModel()
        val addTodoModel=AddTodoModel(taskDataModel,errorModel)
        taskDataMLiveData.postValue(addTodoModel)
    }

    fun saveTask() {
        val addtodoModel=taskDataMLiveData.value
        val taskDataModel=addtodoModel?.dataModel
        if (taskDataModel?.title.isNullOrEmpty()){
            addtodoModel?.error?.id= R.id.et_title
            addtodoModel?.error?.errorMessage= "Please enter your title"
            addtodoModel?.error?.isError=true
            taskDataMLiveData.postValue(addtodoModel!!)
            return
        }
        if (taskDataModel?.time.isNullOrEmpty()){
            addtodoModel?.error?.id= R.id.et_date
            addtodoModel?.error?.errorMessage= "Please select to-do completion time"
            addtodoModel?.error?.isError=true
            taskDataMLiveData.postValue(addtodoModel!!)
            return
        }
        if (taskDataModel?.timeFormat.isNullOrEmpty()){
            addtodoModel?.error?.id= R.id.et_time_format
            addtodoModel?.error?.errorMessage= "Please select time format"
            addtodoModel?.error?.isError=true
            taskDataMLiveData.postValue(addtodoModel!!)
            return
        }
        taskDataModel?.createdAt=Calendar.getInstance().time.time
        val timeItem=taskDataModel?.time?.split(":")
         if (timeItem.isNullOrEmpty())return
        val sHour=timeItem.first().toInt()
        val smins=timeItem.last().toInt()
        taskDataModel.timeStamp=getTimeStamp(sHour,smins,taskDataModel.timeFormat)
        Log.e("Test",taskDataModel.toString())
        viewModelScope.launch(Dispatchers.IO) {
            taskDataModel?.let { databaseService.insertToDoAll(it) }
        }
        stateUpdater.processFun(FunctionId.BACK_PRESS.value)
    }
      fun getTimeStamp(sHour:Int,selectedMinute:Int,format:String): Long {
        val selectedHour= if (format=="PM"){
            sHour+12
        } else sHour
        val mcurrentTime: Calendar = Calendar.getInstance()
        val calendar = Calendar.getInstance()
        calendar[Calendar.YEAR] = mcurrentTime.get(Calendar.YEAR)
        calendar[Calendar.MONTH] = mcurrentTime.get(Calendar.MONTH)
        calendar[Calendar.DAY_OF_MONTH] = mcurrentTime.get(Calendar.DAY_OF_MONTH)
        calendar[Calendar.HOUR_OF_DAY] = selectedHour
        calendar[Calendar.MINUTE] = selectedMinute
        calendar[Calendar.SECOND] = 0
          return calendar.time.time
    }

    val stateUpdater=object: StateUpdater(){
        override val handler: Routing
            get() = object :Routing{
                override fun executePage(id: String, bundle: Bundle) {
                    TODO("Not yet implemented")
                }

                override fun executeFun(id: String, bundle: Bundle) {
                   when(id){
                       FunctionId.SAVE_TASK.value->{
                           saveTask()
                       }

                       else ->this@AddToDoViewModel.handler.executeFun(id, bundle)
                   }
                }


            }

    }


    fun currentTime(){
        val pattern = "hh:mm:ss a"


        val nowTime: LocalDateTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.now()
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        System.out.println(nowTime.format(DateTimeFormatter.ofPattern(pattern)))
        val current_date = Date()
        println("The current date is: $current_date")
        val formatTime = SimpleDateFormat("hh:mm aa")
        val result_time: String = formatTime.format(current_date)
        current_date.time
        val formatTime2 = SimpleDateFormat("dd-MM-yyyy hh:mm aa")
        val time= formatTime2.parse("17-06-2023 $result_time")
        time.time
        Log.e("test","$current_date ${result_time} cur ${current_date.time} ${time.time}")
    }
    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras
                val savedStateHandle = extras.createSavedStateHandle()

                return AddToDoViewModel(
                    (application as ToDoApplication).databaseService,
                    savedStateHandle
                ) as T
            }
        }
    }
}
fun utilTime(value: Int): String? {
    return if (value < 10) "0$value" else value.toString()
}