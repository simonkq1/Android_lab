package com.jetec.bleproj.Global.ModelClass

import java.util.*

class JTCx40Data {
    companion object {
        class Logger {

            var downloadCount: Int? = null
            var allLogCount: Int = 0
            var interval: Int? = null
            var dataLog: ArrayList<Map<String, Any>> = arrayListOf()
            var logStartTime: Date? = null
            var deviceIsRecording: Boolean = false
            var startDate: String? = null
            var startTime: String? = null

        }
    }

    val deviceData: MutableMap<String, Any> = mutableMapOf()
    var deviceModel: DeviceModel = DeviceModel("BT-2-TH")
}