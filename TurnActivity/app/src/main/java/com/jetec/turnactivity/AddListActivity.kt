package com.jetec.turnactivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.add_list_activity.*
import org.jetbrains.anko.toast
import android.widget.AdapterView
import com.jetec.turnactivity.colorSpinner.ColorItem
import com.jetec.turnactivity.colorSpinner.ColorSpinnerArrayAdapter
import com.jetec.turnactivity.customList.Colors
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope


class AddListActivity : AppCompatActivity() {
    var dataList: ArrayList<ColorItem> = ArrayList()
    val arrayAdapter: ColorSpinnerArrayAdapter by lazy { ColorSpinnerArrayAdapter(this, R.layout.color_spinner, dataList) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_list_activity)
        dataList.add(ColorItem(Colors.CLEAR, "Select a Color"))
        dataList.add(ColorItem(Colors.BLUE, "Blue"))
        dataList.add(ColorItem(Colors.RED, "Red"))

        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }
}