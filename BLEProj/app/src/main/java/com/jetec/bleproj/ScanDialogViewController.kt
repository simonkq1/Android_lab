package com.jetec.bleproj

import android.app.Dialog
import android.bluetooth.*
import android.bluetooth.le.BluetoothLeScanner
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat.getSystemService
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import kotlinx.android.synthetic.main.scan_dialog.*

class ScanDialogViewController(context: Context) : Dialog(context) {

    var bluetoothManager: BluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    var mBluetoothAdapter: BluetoothAdapter = bluetoothManager.adapter
    var devices: ArrayList<BluetoothDevice> = arrayListOf()
    var mLeScanCallback: BluetoothAdapter.LeScanCallback? = null
    var gatt: BluetoothGatt? = null
    var connectedCharacteristic: BluetoothGattCharacteristic? = null

    val gattCallback: BluetoothGattCallback = object : BluetoothGattCallback() {

        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            super.onConnectionStateChange(gatt, status, newState)
            if (gatt == null) {return}
            this@ScanDialogViewController.gatt = gatt
            gatt.discoverServices()
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            super.onServicesDiscovered(gatt, status)
            if (gatt == null) {return}
            for (i in gatt.services) {
                for (ii in i.characteristics) {
                    if (ii.properties == BluetoothGattCharacteristic.PROPERTY_NOTIFY) {
                        gatt.setCharacteristicNotification(ii, true)
                        this@ScanDialogViewController.connectedCharacteristic = ii
                        Log.e("LOG", "is Notification")
                        return
                    }
                }
            }
            gatt.disconnect()
        }



        override fun onCharacteristicWrite(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?, status: Int) {
            super.onCharacteristicWrite(gatt, characteristic, status)
            Log.e("LOG", "onCharacteristicWrite")
        }

        override fun onCharacteristicChanged(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?) {
            super.onCharacteristicChanged(gatt, characteristic)
            Log.e("LOG", "onCharacteristicChanged")
        }


        override fun onCharacteristicRead(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?, status: Int) {
            super.onCharacteristicRead(gatt, characteristic, status)
            Log.e("LOG", "onCharacteristicRead")
        }


    }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.requestFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.scan_dialog)

        this.window.setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.window.setLayout((window.getWidth() * 0.8).toInt(), (window.getHeight() * 0.8).toInt())


        var mScanning: Boolean = false

        var mDevice: BluetoothDevice? = null
        //扫描结果的回调，开始扫描后会多次调用该方法
        var devicesAdapter = DevicesAdapter(context, R.layout.scan_table_cell, devices)

        setOnItemClickListener()

        mLeScanCallback = BluetoothAdapter.LeScanCallback { device, rssi, scanRecord ->
            //通过对比设备的 mac 地址获取需要的实例
//            Log.e("Log", devices.indexOf(device).toString())

            if (devices.indexOf(device) == -1) {
                devices.add(device)
                devicesAdapter = DevicesAdapter(context, R.layout.scan_table_cell, devices)
                tableView.adapter = devicesAdapter
            }

        }

        mBluetoothAdapter.startLeScan(mLeScanCallback)

    }

    fun setOnItemClickListener() {
        tableView.setOnItemClickListener { parent, view, position, id ->
            Log.e("TABLE", position.toString())
            devices[position].connectGatt(context, true, gattCallback)


        }
    }




    override fun onStop() {
        super.onStop()
        Log.e("LIFE", "STOP")
        mBluetoothAdapter.stopLeScan(mLeScanCallback)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.e("LIFE", "BackPressed")
    }








}
