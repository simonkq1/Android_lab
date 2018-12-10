package com.jetec.bleproj

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.jetec.bleproj.R.id.deviceAddress
import com.jetec.bleproj.R.id.deviceName

class SettingsAdapter(context: Context,
                      private val resource: Int = R.layout.setting_table_cell,
                      private val items: List<String>): ArrayAdapter<Map<String, Any>>(context, resource, items.size) {

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


        val settingName: TextView = itemView.findViewById(R.id.textField)
        val settingValue: TextView = itemView.findViewById(R.id.detailTextField)
        if (item != null) {
            settingName.text = items[position]
            settingValue.text = 5.toString()
        }




        return itemView
    }

}