package com.example.borjaweatherapp.ui.customdialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

class BasicDialog : DialogFragment() {

    companion object {
        const val ARG_TITLE = "BasicDialog.Title"
        const val ARG_MESSAGE = "BasicDialog.Message"
        const val ARG_TWO_BUTTON = "BasicDialog.TwoButton"
        var basicDialogListener: BasicDialogListener? = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = arguments?.getString(ARG_TITLE) ?: "Alert"
        val message = arguments?.getString(ARG_MESSAGE) ?: ""
        val twoButton = arguments?.getBoolean(ARG_TWO_BUTTON) ?: false

        val dialog = if (twoButton) {
            AlertDialog.Builder(requireContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok") { _,_ ->
                    basicDialogListener?.onOkButtonClicked(tag ?: "")
                    dismiss()
                }
                .setNegativeButton("Cancel") { _,_ ->
                    basicDialogListener?.onCancelButtonClicked(tag ?: "")
                    dismiss()
                }
                .create()
        } else {
            AlertDialog.Builder(requireContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok") { _,_ ->
                    basicDialogListener?.onOkButtonClicked(tag ?: "")
                    dismiss()
                }
                .create()
        }

        val layoutParams = dialog.window?.attributes
        if (layoutParams != null) {
            layoutParams.dimAmount = 0.6f
        }
        dialog.window?.addFlags(
            WindowManager.LayoutParams.FLAG_DIM_BEHIND
        )

        dialog.setTitle(title)
        dialog.setMessage(message)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)

        return dialog
    }

    @JvmOverloads
    fun alertPopup(
        fragmentManager: FragmentManager,
        title: String?,
        message: String?,
        twoButton: Boolean = false,
        tag: String = ""
    ) {
        arguments = Bundle().apply {
            putString(ARG_TITLE, title)
            putString(ARG_MESSAGE, message)
            putBoolean(ARG_TWO_BUTTON, twoButton)
        }
        show(fragmentManager, tag)
    }

    interface BasicDialogListener {
        fun onOkButtonClicked(tag: String = "") {}
        fun onCancelButtonClicked(tag: String = "") {}
    }
}