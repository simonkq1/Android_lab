package com.jetec.turnactivity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("Main")
        toSecondActivityButton.text = "Turn to Second Page"
        toSecondActivityButton.setOnClickListener {
            startActivity<SecondActivity>(
                    "value1" to "Hello",
                    "value2" to "Simon"
            )
        }

    }
}
