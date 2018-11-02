package com.jetec.menucontrol

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        second_button.setOnClickListener {

        }


        val a: Boolean = false
        var counter = 0
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_second, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var str: String
        when (item!!.itemId) {
            R.id.add_item -> {
                return true
            }
            R.id.search_item -> {
                str = "Search"
            }
            R.id.revert_item -> {
                str = "Revert"
            }
            R.id.delete_item -> {

                str = "Delete"
            }
            R.id.deleteIC_item -> {
                finish()
                str = "Cross"
            }
            R.id.speak_item -> {

                str = "Speak"
            }
            R.id.alert_item -> {

                str = "Alert"
            }
            R.id.map_item -> {
                str = "Map"

            }
            else -> {
                str = "None"
            }
        }

        return super.onOptionsItemSelected(item)
    }
}