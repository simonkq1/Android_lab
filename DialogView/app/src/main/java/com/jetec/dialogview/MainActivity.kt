package com.jetec.dialogview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Global.data = 888

        main_button.setOnClickListener {

//            startActivity<SecondActivity>()


            val alert = DialogActivity(this, "Hello", "World")
//            alert.create()
            alert.show()

            /*
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Hello")
            alertDialog.setMessage("World")
            alertDialog.setPositiveButton("Cancel") {dialog, which ->
                Log.e("LOG", "AAAAAAAAAAAAAA")
                dialog.cancel()
            }
            alertDialog.create().show()
            */

        }
    }
}
