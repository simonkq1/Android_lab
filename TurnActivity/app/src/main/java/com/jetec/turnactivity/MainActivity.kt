package com.jetec.turnactivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var menu: Menu? = null
    var isMain: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("Main")
        toSecondActivityButton.text = "Turn to Second Page"
        toSecondActivityButton.setOnClickListener {
            /*
            startActivity<SecondActivity>(
                    "value1" to "Hello",
                    "value2" to "Simon"
            )*/
            this.menu!!.clear()
            menuInflater.inflate(if (isMain) R.menu.menu_second else R.menu.menu_main,this.menu)
            isMain = !isMain
            if (isMain) {
                
            }
//            onCreateOptionsMenu(this.menu)
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
