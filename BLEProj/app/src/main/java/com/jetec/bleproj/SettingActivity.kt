package com.jetec.bleproj

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jetec.bleproj.Global.Global
import kotlinx.android.synthetic.main.scan_dialog.*

class SettingActivity : AppCompatActivity() {
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
    }

    fun reloadData() {
        runOnUiThread {
            val adapter: SettingsAdapter = SettingsAdapter(this, items = Global.JTCData.deviceModel.settingList)
            this.tableView.adapter = adapter
        }
    }
}