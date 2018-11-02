package com.jetec.turnactivity.colorSpinner

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.jetec.turnactivity.*
import com.jetec.turnactivity.R.id.async
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.doAsync
import java.lang.Math.log
import kotlin.concurrent.thread


class  ColorSpinnerArrayAdapter(private val context: Context, private val resource: Int, private val items: ArrayList<ColorItem>): BaseAdapter(){

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val itemView: LinearLayout
        val item = items[position]

        if (convertView == null) {
            itemView = LinearLayout(context)
            val inflater = Context.LAYOUT_INFLATER_SERVICE
            val li = context.getSystemService(inflater) as LayoutInflater
            li.inflate(resource, itemView, true)
        }else {
            itemView = convertView as LinearLayout
        }

        val typeColor: RelativeLayout = itemView.findViewById(R.id.color_block)
        val colorName: TextView = itemView.findViewById(R.id.color_name)

        val background = typeColor.background as GradientDrawable
        if (item.color != null) {
            background.setColor(item.color?.parseColor()!!)
        }
        colorName.text = item.name

        return itemView
    }



    override fun isEnabled(position: Int): Boolean {
        return if (position == 0) false else super.isEnabled(position)
    }



}