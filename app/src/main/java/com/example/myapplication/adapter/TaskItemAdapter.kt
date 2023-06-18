package com.example.myapplication.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.core.StateUpdater
import com.example.myapplication.databinding.TaskItemBinding
import com.example.myapplication.dialog.DeleteToDoDialog
import com.example.myapplication.model.TaskDataModel
import com.example.myapplication.util.FunctionId
import com.example.myapplication.util.TaskStatus

class TaskItemAdapter(val stateUpdater: StateUpdater) : RecyclerView.Adapter<TaskItemAdapter.ViewHolder>() {
    var taskList:List<TaskDataModel> = ArrayList<TaskDataModel>()
    fun updateItem(items: List<TaskDataModel>) {
         this.taskList = items
          notifyDataSetChanged()
    }
    fun addNewItems(items: List<TaskDataModel>){
        val preSize=taskList.size
        if (items.size<preSize){
            this.taskList = items
            notifyDataSetChanged()
        }else{
            this.taskList = items
            val newSize=taskList.size
            if (preSize==0){
                notifyItemRangeInserted(0,taskList.size)
            }else notifyItemRangeInserted(preSize,preSize-newSize)
        }
    }

    lateinit var binding: TaskItemBinding

    inner class ViewHolder(val binding: TaskItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // ViewHolder()
        binding = TaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position:Int) {
       val taskDataModel=taskList[ position]
        holder.binding.apply {
            taskDataItem=taskDataModel
            ibClose.setOnClickListener {
                DeleteToDoDialog(binding.root.context,object :DeleteToDoDialog.YesNoListener{
                    override fun ok() {
                        stateUpdater.processFun(FunctionId.DELETE_TASK.value, Bundle().apply {
                            putInt("index", holder.adapterPosition)
                        })
                    }

                    override fun cancel() {

                    }

                }).show()
            }
            checkbox.addOnCheckedStateChangedListener { checkBox, state ->
            }
            checkbox.setOnClickListener {
                val bundle=Bundle().apply {
                    if (checkbox.isChecked){
                        putString("status", TaskStatus.DONE.value)
                    }else{
                        putString("status", TaskStatus.IN_PROGRESS.value)
                    }
                    putLong("id", taskDataModel.createdAt)

                }
                Log.e("Test ${checkbox.isChecked}",bundle.toString())
                stateUpdater.processFun(FunctionId.UPDATE_STATUS.value,bundle )

            }
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }
}