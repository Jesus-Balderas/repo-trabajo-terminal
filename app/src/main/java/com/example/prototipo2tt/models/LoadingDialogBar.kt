package com.example.prototipo2tt.models

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.TextView
import com.example.prototipo2tt.R

class LoadingDialogBar(context: Context) {

    var context: Context = context
    lateinit var dialog : Dialog

    public fun ShowDialog(title: String){

        dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val progressBar : TextView = dialog.findViewById(R.id.tvProgressBar)
        progressBar.text = title
        dialog.create()
        dialog.show()
    }

    public fun HideDialog(){

        dialog.dismiss()
    }

}