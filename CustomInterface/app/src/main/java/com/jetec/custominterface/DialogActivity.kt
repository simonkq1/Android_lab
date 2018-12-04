package com.jetec.custominterface

import android.app.Dialog
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.dialog_activity.*

class DialogActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_activity)

        showDialogButton.setOnClickListener {
            val d: Dialog = MyDialog(this)
            d.show()
            d.setCanceledOnTouchOutside(false)
        }
    }

    override fun onResume() {
        super.onResume()

    }
}