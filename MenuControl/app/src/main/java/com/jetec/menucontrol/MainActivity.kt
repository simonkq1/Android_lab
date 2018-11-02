package com.jetec.menucontrol

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity


class MainActivity : AppCompatActivity() {
    var menu: Menu? = null
    var isMainMenu: Boolean = true
        set(value) {
            Log.e("LOG", value.toString())
            if (menu != null) {
                this.menu!!.clear()
                menuInflater.inflate(if (value) R.menu.menu_main else R.menu.menu_second, this.menu)
            }
            field = value
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main_button.setOnClickListener {

            startActivity<SecondActivity>()

        }
    }

    fun changeMenu() {
        Thread {
            var n: Int = 0
            while (true) {
                Thread.sleep(500)
                n += 1
                Log.e("TAG", n.toString())
                runOnUiThread {
                    if (n % 10 == 0) {
                        if (menu != null) {
                            this.menu!!.clear()
                            menuInflater.inflate(R.menu.menu_main, this.menu)
                        }
                    } else if (n % 5 == 0) {
                        if (menu != null) {
                            this.menu!!.clear()
                            menuInflater.inflate(R.menu.menu_second, this.menu)
                        }
                    }
                }
            }
        }.start()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        this.menu = menu
        return super.onCreateOptionsMenu(menu)
    }

    public fun aaaaa(view: View) {
        Log.e("LOG", "AAAAAAAAAAAA")
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var str: String

        when (item!!.itemId) {
            R.id.add_item -> {
//                startActivity<SecondActivity>()
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

        runOnUiThread {
            main_text.text = str
        }

        return super.onOptionsItemSelected(item)
    }
}
