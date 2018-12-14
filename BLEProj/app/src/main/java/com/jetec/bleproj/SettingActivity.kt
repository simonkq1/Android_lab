package com.jetec.bleproj

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import com.jetec.bleproj.Global.Global
import com.jetec.bleproj.Global.ModelClass.JTCx40Data
import com.jetec.bleproj.Global.localized
import kotlinx.android.synthetic.main.scan_dialog.*
import java.util.*

class SettingActivity : AppCompatActivity() {
    var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_list_view)

        Global.service!!.delegate = this
        Global.service!!.status = BluetoothStatus.GETTING_ORIGINAL_DATA

        Global.service!!.sendData("get")


    }

    override fun onResume() {
        super.onResume()
        val adapter: SettingsAdapter = SettingsAdapter(this, items = Global.JTCData.deviceModel.settingList)
        this.tableView.adapter = adapter
        setOnItemClickListener()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        this.menu = menu

        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.disconnect -> {
                disconnect()
            }
        }

        //220.29.131.156.202.164

        return super.onOptionsItemSelected(item)
    }

    fun setOnItemClickListener() {
        tableView.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                1 -> {
                    Global.service!!.sendData("Delay00009")
                    Thread.sleep(200)
                    Global.service!!.sendData("DOWNLOAD")
                }
            }
        }
    }

    fun disconnect() {
        if (!Global.isConnected) {
            return
        }
        Global.service!!.connectedGATT!!.setCharacteristicNotification(Global.service!!.connectedCharacteristic!!, false)
        Global.service!!.connectedGATT!!.disconnect()
        Global.service = null
        Global.isConnected = false
        Global.userPassword.clear()
        Global.JTCData = JTCx40Data()
        Global.logger = JTCx40Data.Companion.Logger()
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        this.finish()

    }


    fun reloadData() {
        runOnUiThread {
            val adapter: SettingsAdapter = SettingsAdapter(this, items = Global.JTCData.deviceModel.settingList)
            this.tableView.adapter = adapter
        }
    }

    override fun onBackPressed() {
        return
    }
}