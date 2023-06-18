package com.example.myapplication.ui.addtodopage

import com.example.myapplication.model.TaskDataModel

data class AddTodoModel(
    val dataModel: TaskDataModel,
    val error:ErrorModel
)

data class ErrorModel(
    var errorMessage:String?=null,
    var id:Int?=null,
    var isError:Boolean=false
)