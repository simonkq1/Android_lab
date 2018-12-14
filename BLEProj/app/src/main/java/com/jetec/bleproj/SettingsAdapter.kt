package com.jetec.bleproj

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.jetec.bleproj.Global.Global
import com.jetec.bleproj.Global.LCDCommand
import com.jetec.bleproj.Global.localized
import java.util.*


class SettingsAdapter(context: Context,
                      private val resource: Int = R.layout.setting_table_cell,
                      private val items: ArrayList<String>) : ArrayAdapter<String>(context, resource, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val itemView: LinearLayout
        val item = getItem(position)
        if (convertView == null) {
            itemView = LinearLayout(context)
            val inflater = Context.LAYOUT_INFLATER_SERVICE
            val li = context.getSystemService(inflater) as LayoutInflater
            li.inflate(resource, itemView, true)

        } else {
            itemView = convertView as LinearLayout
        }


        val settingName: TextView = itemView.findViewById(R.id.textField)
        val settingValue: TextView = itemView.findViewById(R.id.detailTextField)
        val v = Global.JTCData.deviceData[item]
        if (item != null) {
            if (item.startsWith(LCDCommand.PV) ||
                    item.startsWith(LCDCommand.EH) ||
                    item.startsWith(LCDCommand.EL) ||
                    item.startsWith(LCDCommand.CR) ||
                    item.startsWith(LCDCommand.IH) ||
                    item.startsWith(LCDCommand.IL) ||
                    item.startsWith(LCDCommand.DP)) {

                val row: Int = item.removeRange(0..1).toInt()
                val t: String = item.substring(0..1)

                settingName.text = when (Global.JTCData.deviceModel.modelCode[row - 1]) {
                    "T" -> "T".localized() + t.localized()
                    "H" -> "H".localized() + t.localized()
                    "C" -> "C".localized() + t.localized()
                    "I" -> "ROW$row".localized() + t.localized()
                    else -> {""}
                }
            } else {

                settingName.text = item.localized()
            }

            if (v != null) {
                val valueText: String = when {
                    item.startsWith(LCDCommand.NAME) -> v.toString()
                    item.startsWith(LCDCommand.SPK) || item.startsWith(LCDCommand.DP) -> {
                        if ((v as Double) == 0.0) {
                            "Off"
                        } else {
                            "On"
                        }
                    }

                    else -> {
                        when (v) {
                            is String -> v.toString()
                            is Int -> v.toString()
                            is Double -> String.format("%.1f", v)
                            is Boolean -> if (v) {
                                "On"
                            } else {
                                "Off"
                            }
                            else -> " "
                        }
                    }
                }
                settingValue.text = valueText
            } else {
                settingValue.text = " "
            }
        }
        return itemView
    }


    operator fun set(index: Int, item: String) {
        if (index >= 0 && index < items.size) {
            items[index] = item
            notifyDataSetChanged()
        }
    }

    operator fun get(index: Int): String {
        return items[index]
    }

}