package com.jetec.bleproj.Global

import com.jetec.bleproj.BluetoothLeService
import com.jetec.bleproj.Global.ModelClass.DeviceModel
import com.jetec.bleproj.Global.ModelClass.JTCx40Data

class Global {

    companion object {
        var service: BluetoothLeService? = null
        var isConnected: Boolean = false
        var userPassword: MutableMap<String, String> = mutableMapOf()
        var JTCData: JTCx40Data = JTCx40Data()
        var logger: JTCx40Data.Companion.Logger = JTCx40Data.Companion.Logger()
    }

}