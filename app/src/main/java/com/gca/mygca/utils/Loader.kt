package com.gca.mygca.utils

import android.app.Activity
import android.app.Dialog
import android.view.Window
import android.view.WindowManager
import com.gca.mygca.R


class Loader {
    companion object {
        fun getLoader(context: Activity): Dialog {
            val dialog = Dialog(context, R.style.DialogFragmentTheme)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE)
            dialog.setContentView(R.layout.loader_dialog)
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)
            return dialog
        }
    }
}