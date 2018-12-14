package com.jetec.bleproj

import android.app.Activity
import android.app.Dialog
import android.bluetooth.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Window
import android.view.WindowManager
import com.jetec.bleproj.Global.*
import com.jetec.bleproj.Global.ModelClass.DeviceModel
import kotlinx.android.synthetic.main.scan_dialog.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.*
import kotlin.concurrent.schedule

class ScanDialogViewController(context: Context) : Dialog(context) {

    var bluetoothManager: BluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    var mBluetoothAdapter: BluetoothAdapter = bluetoothManager.adapter
    var devices: ArrayList<BluetoothDevice> = arrayListOf()
    var bleScanCallback: BluetoothAdapter.LeScanCallback? = null
    var connectedAddress: String? by Preference(context, "connectedAddress", "Key Not Found")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.requestFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.scan_dialog)

        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.window.setLayout((window.getWidth() * 0.8).toInt(), (window.getHeight() * 0.8).toInt())


        var mScanning: Boolean = false

        var mDevice: BluetoothDevice? = null
        //扫描结果的回调，开始扫描后会多次调用该方法
        var devicesAdapter: DevicesAdapter

        setOnItemClickListener()

        bleScanCallback = BluetoothAdapter.LeScanCallback { device, rssi, scanRecord ->
            //通过对比设备的 mac 地址获取需要的实例
//            Log.e("Log", devices.indexOf(device).toString())

            if (devices.indexOf(device) == -1) {
                if (connectedAddress == device.address) {
                    devices.add(0, device)
                }else {
                    devices.add(device)
                }
                devicesAdapter = DevicesAdapter(context, R.layout.scan_table_cell, devices)
                tableView.adapter = devicesAdapter
            }

        }
        mBluetoothAdapter.startLeScan(bleScanCallback)
        if (connectedAddress != null) {
            Log.e("LOG", connectedAddress)
        }

    }

    fun setOnItemClickListener() {
        tableView.setOnItemClickListener { parent, view, position, id ->
            Log.e("TABLE", position.toString())

            Global.service = BluetoothLeService(context, devices[position])
            Global.service!!.status = BluetoothStatus.CONNECTING
//            val l: Dialog =
            Timer("checkConnect", false).schedule(1000) {
                checkConnectionWithOk()
            }

//            this.dismiss()


        }
    }

    fun checkConnectionWithOk() {
        if (Global.service == null) {return}
        if (Global.isConnected) {
            if (Global.JTCData.deviceModel == null) {
                Global.JTCData.deviceModel = DeviceModel("BT-2-TH")
            }
            // is Connect
            Global.service!!.sendData("ENGEWD")
            Thread.sleep(100)
            Global.service!!.sendData("PASSWD")
            Thread.sleep(100)
            Global.service!!.sendData("GUESWD")
            Thread.sleep(100)
//            Global.service!!.sendData("INITWD")
//            Thread.sleep(100)

            this.dismiss()
            context.startActivity<SettingActivity>()

        }else {
            // connect Failed

            if (!Global.isConnected) {return}
            Global.service!!.connectedGATT!!.setCharacteristicNotification(Global.service!!.connectedCharacteristic!!, false)
            Global.service!!.connectedGATT!!.disconnect()
            Global.service = null
            Global.isConnected = false
            context.toast("Failed")

        }

    }


    override fun onStop() {
        super.onStop()
        Log.e("LIFE_CYCLE", "STOP")
        mBluetoothAdapter.stopLeScan(bleScanCallback)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.e("LIFE_CYCLE", "BackPressed")
    }


}
