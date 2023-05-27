package com.example.borjaweatherapp.ui.customdialog

import android.app.Dialog
import android.content.Context

class ProgressDialog {

    companion object {
        private var progressDialog: Dialog? = null

        fun showProgressDialog(context: Context) {
            if (progressDialog == null) {
                progressDialog = Dialog(context)
                val customProgressBar = CustomProgressBar(context)
                progressDialog?.run {
                    setContentView(customProgressBar.rootView)
                    window?.setBackgroundDrawableResource(android.R.color.transparent)
                    setCancelable(false)
                }
            }
            if (progressDialog?.isShowing != true) progressDialog?.show()
        }

        fun hideProgressDialog() {
            progressDialog?.dismiss()
            progressDialog = null
        }
    }
}