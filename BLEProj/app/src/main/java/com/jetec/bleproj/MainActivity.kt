package com.jetec.bleproj

import android.app.Dialog
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.location.LocationManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.jetec.bleproj.Global.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {


    var devices: ArrayList<BluetoothDevice> = arrayListOf()
    var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val gd: GradientDrawable = GradientDrawable()
        scanButton.setOnClickListener {
            val dialog: Dialog = ScanDialogViewController(this)
            dialog.show()
        }

    }

    var devicesAdapter: DevicesAdapter? = null

    override fun onResume() {
        super.onResume()

        val gd = GradientDrawable()
        val width = window.getWidth()
        val height = window.getHeight()
        val diameter: Int = if (width > height) height / 2 else width / 2
        gd.apply {
            this.shape = GradientDrawable.OVAL
            this.setSize(diameter, diameter)
            this.cornerRadius = (diameter / 2).toFloat()
            this.colors = intArrayOf(R.color.button_material_light, R.color.colorAccent)
            this.gradientType = GradientDrawable.LINEAR_GRADIENT
            this.setGradientCenter(0.5f, 0.5f)
        }
        scanButton.background = gd
        /*
        val a = DeviceModel("BT-3-ICILM")
        var keys = ArrayList(a.defaultSettings.keys)
        var n: Int = 0
        Log.e("LOG", keys.toString())
        for (i in 0 until a.deviceRowNumber) {
            val ind = keys.indexOf("DP" + (i + 1).toString())
            if (ind != -1) {
                keys.run {
                    val tmp = keys[n]
                    this[n] = this[ind]
                    this[ind] = tmp
                }
                n += 1
            }
        }

        for (i in keys) {
            var code: String
            val value = a.defaultSettings[i]
            when (i) {
                LCDCommand.NAME -> {
                    code = i + value.toString()
                }
                LCDCommand.INT -> {
                    code = i + String.format("%05d", value as Int)
                }
                else -> {
                    code = (value as Double).toSendableString(i) ?: ""
                }
            }
            Log.e("LOG", code)
        }

*/

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


        return super.onOptionsItemSelected(item)
    }


    fun disconnect() {
        if (!Global.isConnected) {
            return
        }
        Global.service!!.connectedGATT!!.setCharacteristicNotification(Global.service!!.connectedCharacteristic!!, false)
        Global.service!!.connectedGATT!!.disconnect()
        Global.service = null
        Global.isConnected = false
    }


    fun locationCheck() {
        val LOCATION_RESULT_CODE: Int = 0
        if (Build.VERSION.SDK_INT >= 23) {
            val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) &&
                    !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setMessage("定位服務尚未開啟，請開啟定位")
                builder.setCancelable(false)
                builder.setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, which ->

                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivityForResult(intent, LOCATION_RESULT_CODE)
                    Log.e("TAG", "A")
                    return@OnClickListener

                })
                builder.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
                    dialog.cancel()
                    return@OnClickListener
                })
                builder.show()
                return

            }

        }
    }


    fun initBluetoothAdapter() {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val bluetoothAdapter = bluetoothManager.adapter
        //如果蓝牙没有打开则向用户申请
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled)
            bluetoothAdapter.enable()
    }

    /*
    //开始扫描之前判断是否开启了蓝牙，enable 为 false 可以停止扫描
    fun scanLeDeviceWithBLE(enable:Boolean = true){
        if (mBluetoothAdapter == null)
            initBluetoothAdapter()

        if (mBluetoothAdapter?.isEnabled as Boolean){
            mBluetoothAdapter?.enable()
        }
        if (enable){
            mScanning = true
            mBluetoothAdapter?.startLeScan(bleScanCallback)
            TimeUtilWithoutKotlin.Delay(8, TimeUnit.SECOND).setTodo {
                mBluetoothAdapter?.stopLeScan(bleScanCallback)
                mScanning = false
            }
        }else {
            //停止扫描，在连接设备时最好调用 stopLeScan()
            mBluetoothAdapter?.stopLeScan(bleScanCallback)
            mScanning = false
        }
    }*/

}
