package com.example.myapplication.ui.addtodopage.view

import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import android.view.MotionEvent
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.myapplication.R
import com.example.myapplication.core.StateUpdater
import com.example.myapplication.databinding.FragmentAddToDoBinding
import com.example.myapplication.model.TaskDataModel
import com.example.myapplication.ui.addtodopage.AddTodoModel
import com.example.myapplication.ui.addtodopage.ErrorModel
import com.example.myapplication.util.FunctionId
import com.example.myapplication.viewmodel.utilTime
import java.util.*

class AddToDoView(
    context: Context,
    val binding: FragmentAddToDoBinding,
    stateUpdater: StateUpdater
) : Observer<AddTodoModel> {
    init {
        val items = listOf("AM", "PM")
        val adapter = ArrayAdapter(context, R.layout.time_format_item, items)
        binding.etTimeFormat.setAdapter(adapter)
         binding.topAppBar.setNavigationOnClickListener {
            binding.root.findNavController().navigateUp()
        }

        binding.etDate.setOnTouchListener { view, motionEvent ->
            Log.e("Test", " onTuuch")
            //currentTime()
            val mcurrentTime: Calendar = Calendar.getInstance()
            val hour: Int = mcurrentTime.get(Calendar.HOUR_OF_DAY)

            val minute: Int = mcurrentTime.get(Calendar.MINUTE)
            val mTimePicker: TimePickerDialog
            mTimePicker = TimePickerDialog(
                context,
                { timePicker, selectedHour, selectedMinute ->
                    binding.etDate.setText("${utilTime(selectedHour)}:${utilTime(selectedMinute)}")
                    val calendar = Calendar.getInstance()
                    calendar[Calendar.YEAR] = mcurrentTime.get(Calendar.YEAR)
                    calendar[Calendar.MONTH] = mcurrentTime.get(Calendar.MONTH)
                    calendar[Calendar.DAY_OF_MONTH] = mcurrentTime.get(Calendar.DAY_OF_MONTH)
                    calendar[Calendar.HOUR_OF_DAY] = selectedHour
                    calendar[Calendar.MINUTE] = selectedMinute
                    calendar[Calendar.SECOND] = 0
                    Log.e("Test", "${calendar.time.time}")
                    //setTime(selectedHour,selectedMinute)
                },
                hour,
                minute,
                true
            ) //Yes 24 hour time

            mTimePicker.setTitle("Select Time")
            //mTimePicker.show()
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                mTimePicker.show()
                true;
            }
            false;
        }
        binding.etTimeFormat.setOnItemClickListener { adapterView, view, i, l ->
            binding.etTimeFormat.error=null
            binding.taskDataModel?.timeFormat = items[i]
        }
        binding.bCancel.setOnClickListener {
            binding.root.findNavController().navigateUp()
        }
        binding.bAdd.setOnClickListener {
            stateUpdater.processFun(FunctionId.SAVE_TASK.value)

        }
    }

    private fun bind(value: TaskDataModel) {
        binding.taskDataModel = value
    }

    private fun bindError(error: ErrorModel) {
        if (error.isError) {
            when (error.id) {
                R.id.et_title -> {
                    binding.etTitle.error = error.errorMessage
                }
                R.id.et_date -> {
                    binding.etDate.error = error.errorMessage
                }
                R.id.et_time_format -> {
                    binding.etTimeFormat.error = error.errorMessage
                }
            }
        }
    }

    override fun onChanged(value: AddTodoModel) {
        bind(value.dataModel)
        bindError(value.error)
    }
}