package com.jetec.dialogview

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DialogTitle

class DialogActivity(context: Context, val title: String): Dialog(context, R.style.Theme_AppCompat_Dialog) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_view)

    }
}