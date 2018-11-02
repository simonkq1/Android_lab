package com.jetec.dialogview

import android.app.AlertDialog
import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main_button.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Hello")
            alertDialog.setMessage("World")
            alertDialog.setPositiveButton("Cancel") {dialog, which ->
                Log.e("LOG", "AAAAAAAAAAAAAA")
            }
            alertDialog.create().show()
        }
    }
}
