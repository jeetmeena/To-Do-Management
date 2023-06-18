package com.example.myapplication.core

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.model.TaskDataModel

@Dao
interface DatabaseService {
    @Query("SELECT * FROM todos ORDER BY createdAt ASC")
    fun getTimeLineElements(): List<TaskDataModel>

    @Insert
    fun insertToDoAll(vararg todos: TaskDataModel)

    @Query("SELECT * FROM todos")
    fun getAllTodos(): LiveData<List<TaskDataModel>>

    @Delete
    fun delete(todo: TaskDataModel)


    @Query(
        "INSERT OR REPLACE INTO todos(createdAt,title,status,timeStamp,timeFormat,time)" +
                " VALUES((select createdAt from todos where createdAt =:createdAt),:title,:status,:timeStamp,:timeFormat,:time)"
    )
    fun updateTodo(
        title: String,
        status: String,
        timeStamp: Long,
        timeFormat: String,
        time: String,
        createdAt: Long,
    )

}