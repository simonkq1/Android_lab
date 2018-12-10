package com.jetec.bleproj

import android.bluetooth.*
import android.content.Context
import android.bluetooth.BluetoothClass as BClass
import android.util.Log
import com.jetec.bleproj.Global.ModelClass.DeviceModel
import com.jetec.bleproj.Global.Global
import com.jetec.bleproj.Global.LCDCommand
import com.jetec.bleproj.Global.UART_UUIDS
import com.jetec.bleproj.Global.codeToDoubleValue
import kotlinx.android.synthetic.main.scan_dialog.*
import java.util.*

enum class BluetoothStatus {
    NONE,
    CONNECTING,
    CONNECTED,
    MAIN_ACTIVITY,
    DOWNLOADING,
    GETTING_ORIGINAL_DATA
}

class BluetoothLeService(context: Context, device: BluetoothDevice) {

    var device: BluetoothDevice? = null
    var characteristic: BluetoothGattCharacteristic? = null
    var connectedGATT: BluetoothGatt? = null
    var connectedCharacteristic: BluetoothGattCharacteristic? = null
    var writableCharacteristic: BluetoothGattCharacteristic? = null
    var delegate: Context? = null
    var status: BluetoothStatus = BluetoothStatus.NONE

    val gattCallback: BluetoothGattCallback = object : BluetoothGattCallback() {

        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            super.onConnectionStateChange(gatt, status, newState)

            if (gatt == null) {
                return
            }

            if (this@BluetoothLeService.status == BluetoothStatus.CONNECTING) {

                this@BluetoothLeService.connectedGATT = gatt

                if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    gatt.close()
                }
                if (newState == BluetoothProfile.STATE_CONNECTING) {
                    //设备在连接中
                }

                gatt.discoverServices()

            }
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
                            this@BluetoothLeService.connectedCharacteristic = ii
                            this@BluetoothLeService.writableCharacteristic = i.getCharacteristic(UUID.fromString(UART_UUIDS.uartRXCharacteristicUUIDString))

                            if (this@BluetoothLeService.writableCharacteristic == null) {
                                return
                            }
//                            Log.e("LOG", this@ScanDialogViewController.writableCharacteristic!!.uuid.toString())
//                            Log.e("LOG", ii.uuid.toString())
                            val descriptor: BluetoothGattDescriptor = ii.getDescriptor(UUID.fromString(UART_UUIDS.TXDescriptor))

                            descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE

                            if (gatt.writeDescriptor(descriptor)) {
                                Log.e("LOG", "writeDescriptor Success")
                            }
                            Log.e("LOG", "is Notification")

                            return
                        } else {
                            // notification failed
                            Log.e("LOG", "Notification Failed")
                        }
                    } else {
                        // is not notify
                        Log.e("LOG", "ERROR D")
                    }
                }
            }
            gatt.disconnect()
        }

        override fun onDescriptorWrite(gatt: BluetoothGatt?, descriptor: BluetoothGattDescriptor?, status: Int) {
            super.onDescriptorWrite(gatt, descriptor, status)
            if (this@BluetoothLeService.writableCharacteristic == null || gatt == null) {
                //获取特征失败,直接断开连接

                gatt?.disconnect()
                return
            }

            //mSendValue 即要往硬件发送的数据
            //如果这里写入数据成功会回调下面的 onCharacteristicWrite() 方法
            this@BluetoothLeService.writableCharacteristic!!.value = "Jetec".toByteArray()

            if (!gatt.writeCharacteristic(this@BluetoothLeService.writableCharacteristic)) {
                //写入数据失败,断开连接
                Log.e("LOG", "write Error.")
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
            if (characteristic == null) {
                return
            }
            Log.e("LOG", characteristic.getStringValue(0))
        }

        override fun onCharacteristicChanged(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?) {
            super.onCharacteristicChanged(gatt, characteristic)
            //TODO Receive Data
            if (characteristic == null) {
                return
            }

            val x = characteristic.getStringValue(0)
            Log.e("LOG", "============ Receive Data ============")
            Log.e("LOG", x ?: "")
            when {
                x == "OK" -> {
                    Global.isConnected = true
                }
                x.startsWith("BT-") -> {
                    Global.JTCData.deviceModel = DeviceModel(x)
                }
                x.startsWith(LCDCommand.ENGE) -> {
                    val pass = x.replace(LCDCommand.ENGE, "")

                    Log.e("LOG", pass)
                    Global.userPassword.put(LCDCommand.ENGE, pass)
                    this@BluetoothLeService.checkPassword()
                }
                x.startsWith(LCDCommand.PASS) -> {
                    val pass = x.replace(LCDCommand.PASS, "")
                    Global.userPassword.put(LCDCommand.PASS, pass)
                    this@BluetoothLeService.checkPassword()
                }
                x.startsWith(LCDCommand.GUES) -> {
                    val pass = x.replace(LCDCommand.GUES, "")
                    Global.userPassword.put(LCDCommand.GUES, pass)
                    this@BluetoothLeService.checkPassword()
                }
                x.startsWith(LCDCommand.INIT) -> {
                    val pass = x.replace(LCDCommand.INIT, "")
                    Global.userPassword.put(LCDCommand.INIT, pass)
                }
                x.startsWith("PV")
                        || x.startsWith("EH")
                        || x.startsWith("EL")
                        || x.startsWith("CR")
                        || x.startsWith("IH")
                        || x.startsWith("IL")
                        || x.startsWith("DP") -> {
                    val code = x.substring(0..2)
                    val value = x.codeToDoubleValue()
                    if (value != null) {
                        Global.JTCData.deviceData[code] = value
                    }
                }
                x.startsWith(LCDCommand.NAME) -> {
                    val name = x.replace(LCDCommand.NAME, "")
                    Global.JTCData.deviceData[LCDCommand.NAME] = name
                }
                x.startsWith(LCDCommand.INT) -> {
                    val name = x.replace(LCDCommand.INT, "")
                    Global.JTCData.deviceData[LCDCommand.NAME] = name
                }
                x.startsWith("OVER") -> {
                    if (this@BluetoothLeService.status == BluetoothStatus.GETTING_ORIGINAL_DATA && delegate is SettingActivity) {
                        (delegate as SettingActivity).reloadData()
                        this@BluetoothLeService.status = BluetoothStatus.NONE
                    }
                }

                else -> {

                }

            }
        }


        override fun onCharacteristicRead(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?, status: Int) {
            super.onCharacteristicRead(gatt, characteristic, status)
            Log.e("LOG", "onCharacteristicRead")
        }


    }

    init {
        this.device = device
        this.delegate = context
        this.device!!.connectGatt(context, true, this.gattCallback)
    }


    fun sendData(value: String) {
        if (this.writableCharacteristic == null) {
            return
        }
        this.writableCharacteristic!!.value = value.toByteArray(Charsets.UTF_8)
        this.connectedGATT!!.writeCharacteristic(this.writableCharacteristic)
    }

    fun BluetoothGattCharacteristic.sendData(gatt: BluetoothGatt = this@BluetoothLeService.connectedGATT!!, value: String) {
        this.value = value.toByteArray(Charsets.UTF_8)
        gatt.writeCharacteristic(this)
    }

    fun BluetoothGattCharacteristic.sendData(gatt: BluetoothGatt = this@BluetoothLeService.connectedGATT!!, value: ByteArray) {
        this.value = value
        gatt.writeCharacteristic(this)
    }


}