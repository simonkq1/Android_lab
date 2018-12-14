package com.jetec.bleproj.Global.ModelClass

import android.util.Log
import com.jetec.bleproj.Global.DataLogFormat
import com.jetec.bleproj.Global.InitialSettings
import com.jetec.bleproj.Global.SettingList



class DeviceModel(model: String) {
    var model = model
    var deviceRowNumber: Int = 0
    var settingList: ArrayList<String> = ArrayList(SettingList.DEF)
    var defaultSettings: MutableMap<String, Any> = mutableMapOf()
    var modelCode: ArrayList<String> = arrayListOf()
    var logPattern: String = ""

    init {
        analysis()
    }

    private fun analysis() {
        if (model.startsWith("BT")) {
            val split: List<String> = model.split("-")
            this.deviceRowNumber = split[1].toInt()
            this.logPattern += "^"
            for ((k, v) in InitialSettings.DEF) {
                defaultSettings.put(k, v)
            }
            for (i in 0 until split[2].length) {
                modelCode.add(split[2][i].toString())

                var sets: ArrayList<String> = ArrayList(SettingList.SET)
                for (s in 0 until sets.size) {
                    sets[s] = sets[s] + (i + 1).toString()
                }

                when (split[2][i]) {
                    'T' -> {
                        val ds = InitialSettings.T
                        for ((k, v) in ds) {
                            defaultSettings.put((k + (i + 1).toString()), v)
                        }
                        this.settingList.addAll(sets)
                        this.logPattern += DataLogFormat.T
                    }
                    'H' -> {
                        val ds = InitialSettings.H
                        for ((k, v) in ds) {
                            defaultSettings.put((k + (i + 1).toString()), v)
                        }
                        this.settingList.addAll(sets)
                        this.logPattern += DataLogFormat.H
                    }
                    'C' -> {
                        val ds = InitialSettings.C
                        for ((k, v) in ds) {
                            defaultSettings.put((k + (i + 1).toString()), v)
                        }
                        this.settingList.addAll(sets)
                        this.logPattern += DataLogFormat.C
                    }
                    'I' -> {
                        val ds = InitialSettings.I
                        for (ii in SettingList.I) {
                            this.settingList.add(ii + (i + 1).toString())
                        }

                        for ((k, v) in ds) {
                            defaultSettings.put((k + (i + 1).toString()), v)
                        }
                        this.settingList.addAll(sets)

                        this.logPattern += DataLogFormat.I
                    }
                    'V' -> {
                        this.settingList.addAll(sets)

                        this.logPattern += DataLogFormat.V
                    }
                    'L' -> {
                        val ds = InitialSettings.L
                        for ((k, v) in ds) {
                            defaultSettings.put(k, v)
                        }
                        this.settingList.addAll(SettingList.L)
                    }
                    'M' -> {
                        val ds = InitialSettings.M
                        for ((k, v) in ds) {
                            defaultSettings.put(k, v)
                        }
                        this.settingList.addAll(SettingList.M)
                    }
                }
            }
            Log.e("LOG", this.settingList.toString())
            Log.e("LOG", this.defaultSettings.toString())
            this.logPattern += "$"

        }
        Log.e("LOG", this.logPattern)
    }


}


