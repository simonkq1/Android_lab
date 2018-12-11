package com.jetec.bleproj

import android.bluetooth.BluetoothDevice
import android.content.ClipData
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.support.constraint.ConstraintLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*

class DevicesAdapter(context: Context,
                     private val resource: Int,
                     private val items: ArrayList<BluetoothDevice>): ArrayAdapter<BluetoothDevice>(context, resource, items)
{

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val itemView: LinearLayout
        val item = getItem(position)
        if (convertView == null) {
            itemView = LinearLayout(context)
            val inflater = Context.LAYOUT_INFLATER_SERVICE
            val li = context.getSystemService(inflater) as LayoutInflater
            li.inflate(resource, itemView, true)

        }else {
            itemView = convertView as LinearLayout
        }


        val deviceName: TextView = itemView.findViewById(R.id.deviceName)
        val deviceAddress: TextView = itemView.findViewById(R.id.deviceAddress)
        if (item != null) {
            deviceName.text = item.name ?: "unknown"
            deviceAddress.text = item.address
        }


        return itemView
    }

    operator fun set (index: Int, item: BluetoothDevice){
        if (index >= 0 && index < items.size) {
            items[index] = item
            notifyDataSetChanged()
        }
    }

    operator fun get (index: Int): BluetoothDevice {
        return items[index]
    }

}