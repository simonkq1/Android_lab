package com.jetec.bleproj

import android.util.Log
import com.jetec.bleproj.Global.Global
import com.jetec.bleproj.Global.LCDCommand
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast


fun BluetoothLeService.checkPassword() {
    Log.e("LOG", "Check START")
    if (Global.userPassword[LCDCommand.ENGE] != null
            && Global.userPassword[LCDCommand.PASS] != null
            && Global.userPassword[LCDCommand.GUES] != null) {
        Log.e("LOG", "Check PWD")
        Thread.sleep(1000)
//        Global.service!!.sendData("get")
        this.delegate!!.runOnUiThread {
            this.toast("GET PWD")
        }

    }
}