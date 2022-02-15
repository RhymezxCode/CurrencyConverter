package com.project.currencyconverter.data.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.project.currencyconverter.R

internal class Progress(private var context: Context) {
    private var dialog: Dialog? = null


    fun show() {
        dialog = Dialog(context)
        dialog!!.setContentView(R.layout.loader)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.setCancelable(true)
        dialog!!.show()
    }

    fun dismiss() {
        if(dialog != null){
            dialog!!.dismiss()
        }
    }
}