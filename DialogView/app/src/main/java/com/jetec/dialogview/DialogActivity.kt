package com.jetec.dialogview

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.dialog_view.*
import org.jetbrains.anko.longToast


class DialogActivity(context: Context, val title: String, val message: String): Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_view)
        this.cancel_button.text = "Cancel"
        this.ok_button.text = "Ok"
        this.account_text.setText(window.getWidth().toString())
        this.password_text.setText(window.getHeight().toString())
        this.cancel_button.setOnClickListener {
            Log.e("LOG", "Cancel")
            this.dismiss()
        }


        this.ok_button.setOnClickListener {
            Log.e("LOG", "Ok")
            val acc = this.account_text.text
            val pwd = this.password_text.text
            context.longToast("$acc : $pwd")
        }
    }
}