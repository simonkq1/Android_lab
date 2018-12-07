package com.jetec.bleproj

import android.util.Log

class DeviceModel(model: String) {
    var model = model
    var deviceRowNumber: Int = 0

    init {
        analysis()
    }

    fun analysis() {
        if (model.startsWith("BT")) {
            val split: List<String> = model.split("-")
            this.deviceRowNumber = split[1].toInt()
            Log.e("LOG", split.toString())
            for (i in split[2]) {
                Log.e("LOG", i.toString())
                when (i) {
                    'T' -> {

                    }
                    'H' -> {

                    }
                    'C' -> {

                    }
                    'I' -> {

                    }
                    'V' -> {

                    }
                    'L' -> {

                    }
                    'M' -> {

                    }
                }
            }
        }
    }


}