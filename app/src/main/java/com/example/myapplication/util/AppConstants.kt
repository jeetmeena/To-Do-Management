package com.example.myapplication.util

enum class AppConstants {

}

enum class TaskStatus(val value:String ){
    PENDING("pending"),
    IN_PROGRESS("in_progress"),
    DONE("done")
}

enum class PageId(val value: String){

}

enum class FunctionId(val value: String){
    SAVE_TASK("save_task"),
    DELETE_TASK("delete_task"),
    BACK_PRESS("back_press"),
    UPDATE_STATUS("update_status")
}