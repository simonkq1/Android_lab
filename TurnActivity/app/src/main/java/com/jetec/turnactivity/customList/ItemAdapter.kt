package com.jetec.turnactivity.customList

import com.jetec.turnactivity.*
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class ItemAdapter(context: Context,
 private val resource: Int,
 private val items: MutableList<Item>): ArrayAdapter<Item>(context, resource, items) {

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
        val typeColor: RelativeLayout = itemView.findViewById(R.id.type_color)
        val selectedItem: ImageView = itemView.findViewById(R.id.selected_item)
        val listText1: TextView = itemView.findViewById(R.id.listText1)
        val listText2: TextView = itemView.findViewById(R.id.listText2)

        val background = typeColor.background as GradientDrawable
        background.setColor(item.color.parseColor())

        listText1.text = item.title
        listText2.text = item.localeDatetime

        selectedItem.visibility = if (item.isSelected) View.VISIBLE else View.INVISIBLE

        return itemView
    }

    operator fun set (index: Int, item: Item){
        if (index >= 0 && index < items.size) {
            items[index] = item
            notifyDataSetChanged()
        }
    }

    operator fun get (index: Int): Item {
        return items[index]
    }

}