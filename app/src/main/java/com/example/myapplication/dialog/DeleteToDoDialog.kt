package com.example.myapplication.dialog

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.Window
import android.view.WindowManager
import com.example.myapplication.R
import com.example.myapplication.databinding.DialogDeleteTodoBinding


class DeleteToDoDialog(
    val title:String,
    contextDialog: Context,
    private val listener: YesNoListener
) : Dialog(contextDialog) {

    private lateinit var binding: DialogDeleteTodoBinding

    interface YesNoListener {
        fun ok()
        fun cancel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        setCancelable(false)
        binding = DialogDeleteTodoBinding.inflate(layoutInflater).apply {
            text="Do you want to delete “${title}”, this action can’t be undone."
            btnCancel.setOnClickListener {
                dismiss()
                listener.cancel()
            }
            btnOk.setOnClickListener {
                dismiss()
                listener.ok()
            }
        }
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        val margin=binding.root.resources.getDimension(R.dimen.dimen_16)*2
        val windowWidth = Resources.getSystem().displayMetrics.widthPixels
        window?.setLayout((windowWidth.minus(margin)).toInt(), WRAP_CONTENT)
    }
}

