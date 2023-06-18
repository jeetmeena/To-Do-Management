package com.example.myapplication.model

import android.view.View
import androidx.room.Entity
import com.example.myapplication.util.TaskStatus

/*@RealmClass
open class RealmTaskDataModel : RealmModel {
    @Required
    val name: String = ""
    val status: String = ""

    @Required
    val date: Long = 0

    @PrimaryKey
    val createdAt: Long = 0
}*/

@Entity(tableName = "todos")
data class TaskDataModel(
    var title: String,
    val status: String,
    var timeStamp: Long,
    var timeFormat: String,
    var time: String,
    @androidx.room.PrimaryKey var createdAt: Long = 0
) {
    fun isStatusVisible(): Int = if (getCurrentStatus() == "pending") View.VISIBLE else View.GONE
    fun getTimeWithFormat() = "$time $timeFormat"
    fun getCurrentStatus(): String {
        return when (status) {
            TaskStatus.IN_PROGRESS.value -> {
                if (System.currentTimeMillis() > timeStamp) TaskStatus.PENDING.value else TaskStatus.IN_PROGRESS.value
            }
            else -> status
        }
    }
    fun isCheckBoxEnable()= getCurrentStatus() != TaskStatus.PENDING.value
    fun isChecked()=getCurrentStatus() == TaskStatus.DONE.value
    /*   TaskStatus.PENDING.value->{
            view.isEnabled=false
            view.isChecked=false
        }
        TaskStatus.DONE.value->{
            view.isEnabled=false
            view.isChecked=true
        }
        else->{
            view.isEnabled=true
            view.isChecked=false
        }*/
}
