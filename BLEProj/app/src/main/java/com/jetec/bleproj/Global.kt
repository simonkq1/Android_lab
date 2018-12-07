package com.jetec.bleproj

import android.content.Context

class Global {

    companion object {
        var service: BluetoothLeService? = null
        var isConnected: Boolean = false
        var userPassword: MutableMap<String, String> = mutableMapOf()
        var deviceModel: DeviceModel? = null
    }

}