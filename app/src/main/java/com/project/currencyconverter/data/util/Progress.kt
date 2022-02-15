package com.project.currencyconverter.data.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.project.currencyconverter.R

internal class Progress(private var context: Context?) {
    private var dialog_main: Dialog? = null


    fun show() {
        dialog_main = context?.let { Dialog(it) }
        dialog_main!!.setContentView(R.layout.loader)
        dialog_main!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog_main!!.setCancelable(true)
        dialog_main!!.show()
    }

    fun dismiss() {
        if(dialog_main != null){
            dialog_main!!.dismiss()
        }
    }
}