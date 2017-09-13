package com.anfeng.game.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.TextView
import com.anfeng.wuhao.anfengkuaikan.R


object Dialogs {

    fun showLoadingDialog(context: Context, desc: String, call: (Dialog) -> Unit) {
        val view = View.inflate(context, R.layout.dialog_loading, null)
        val descView = view.findViewById(R.id.desc) as TextView
        descView.text = desc

        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(view)
        dialog.setCancelable(false)
        dialog.show()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        call(dialog)
    }

}