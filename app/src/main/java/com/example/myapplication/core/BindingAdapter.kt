package com.example.myapplication.core

import android.graphics.Paint
import android.widget.CheckBox
import android.widget.TextView

import androidx.databinding.BindingAdapter
import com.example.myapplication.R
import com.example.myapplication.util.TaskStatus


@BindingAdapter( "textStrike", requireAll = true)
fun addStrike(view: TextView, /*text: CharSequence?,*/textStrike:String) {
     //view.text = text
    if (textStrike==TaskStatus.PENDING.value){
        view.paintFlags = view.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        view.setTextColor(view.context.getColor(R.color.red_color))
    }
}
@BindingAdapter( value = [  "isActive"], requireAll = true)
fun isActive(view: CheckBox, /*text: CharSequence?,*/status:String) {
    //view.text = text
    when(status){
        TaskStatus.PENDING.value->{
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
        }
    }
}