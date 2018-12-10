package com.jetec.bleproj

import android.app.Dialog
import android.bluetooth.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.view.Window
import android.view.WindowManager
import com.jetec.bleproj.Global.ModelClass.DeviceModel
import com.jetec.bleproj.Global.Global
import com.jetec.bleproj.Global.getHeight
import com.jetec.bleproj.Global.getWidth
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
    var gatt: BluetoothGatt? = null
    var connectedCharacteristic: BluetoothGattCharacteristic? = null
    var writableCharacteristic: BluetoothGattCharacteristic? = null
    var service: BluetoothLeService? = null
/*
    val gattCallback: BluetoothGattCallback = object : BluetoothGattCallback() {

        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            super.onConnectionStateChange(gatt, status, newState)
            if (gatt == null) {
                return
            }
            this@ScanDialogViewController.gatt = gatt

            if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                gatt.close()
            }
            if (newState == BluetoothProfile.STATE_CONNECTING) {
                //设备在连接中
            }

            gatt.discoverServices()
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            super.onServicesDiscovered(gatt, status)
            if (gatt == null) {
                return
            }
            Log.e("LOG", "AAAA")
            for (i in gatt.services) {
                for (ii in i.characteristics) {

                    Log.e("LOG", ii.uuid.toString())
                    if (ii.properties == BluetoothGattCharacteristic.PROPERTY_NOTIFY) {

                        if (gatt.setCharacteristicNotification(ii, true)) {
                            this@ScanDialogViewController.connectedCharacteristic = ii
                            this@ScanDialogViewController.writableCharacteristic = i.getCharacteristic(UUID.fromString(UART_UUIDS.uartRXCharacteristicUUIDString))

                            if (this@ScanDialogViewController.writableCharacteristic == null) {
                                Log.e("LOG", "C ERROR")
                                return
                            }
//                            Log.e("LOG", this@ScanDialogViewController.writableCharacteristic!!.uuid.toString())
//                            Log.e("LOG", ii.uuid.toString())
                            val descriptor: BluetoothGattDescriptor = ii.getDescriptor(UUID.fromString(UART_UUIDS.TXDescriptor))

                            descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                            if (gatt.writeDescriptor(descriptor)) {

                                Log.e("LOG", "Jetec Success")
                            } else {

                                Log.e("LOG", "Jetec Error")
                            }
                            Log.e("LOG", "is Notification")

                            return
                        } else {
                            Log.e("LOG", "Notification Failed")
                        }
                    } else {
                        Log.e("LOG", "ERROR D")

                    }
                }
            }
            gatt.disconnect()
        }

        override fun onDescriptorWrite(gatt: BluetoothGatt?, descriptor: BluetoothGattDescriptor?, status: Int) {
            super.onDescriptorWrite(gatt, descriptor, status)
            if (this@ScanDialogViewController.writableCharacteristic == null || gatt == null) {
                //获取特征失败,直接断开连接

                gatt?.disconnect()
                return
            }

            //mSendValue 即要往硬件发送的数据
            //如果这里写入数据成功会回调下面的 onCharacteristicWrite() 方法
            this@ScanDialogViewController.writableCharacteristic!!.value = "Jetec".toByteArray()
            if (!gatt.writeCharacteristic(this@ScanDialogViewController.writableCharacteristic)) {
                //写入数据失败,断开连接
                gatt.disconnect()
            }

        }


        override fun onDescriptorRead(gatt: BluetoothGatt?, descriptor: BluetoothGattDescriptor?, status: Int) {
            super.onDescriptorRead(gatt, descriptor, status)
            if (descriptor == null) {
                return
            }
            Log.e("LOG", descriptor.value.toString())
        }

        override fun onCharacteristicWrite(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?, status: Int) {
            super.onCharacteristicWrite(gatt, characteristic, status)
            Log.e("LOG", "onCharacteristicWrite")
        }

        override fun onCharacteristicChanged(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?) {
            super.onCharacteristicChanged(gatt, characteristic)

            Log.e("LOG", characteristic!!.getStringValue(0))
            Log.e("LOG", "onCharacteristicChanged")
        }


        override fun onCharacteristicRead(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?, status: Int) {
            super.onCharacteristicRead(gatt, characteristic, status)
            Log.e("LOG", "onCharacteristicRead")
        }


    }
*/


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
                devices.add(device)
                devicesAdapter = DevicesAdapter(context, R.layout.scan_table_cell, devices)
                tableView.adapter = devicesAdapter
            }

        }
        mBluetoothAdapter.startLeScan(bleScanCallback)
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
