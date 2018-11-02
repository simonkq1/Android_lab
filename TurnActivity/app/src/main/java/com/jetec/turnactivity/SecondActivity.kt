package com.jetec.turnactivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.jetec.turnactivity.customList.Colors
import com.jetec.turnactivity.customList.Item
import com.jetec.turnactivity.customList.ItemAdapter
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.startActivity
import java.util.*


class SecondActivity : AppCompatActivity() {
    private val items: ArrayList<Item> = ArrayList()
    private lateinit var add_item: MenuItem
    private lateinit var search_item: MenuItem
    private lateinit var revert_item: MenuItem
    private lateinit var delete_item: MenuItem
    private var selectedCount = 0

    val itemAdapter: ItemAdapter by lazy { ItemAdapter(this, R.layout.my_list_cell, items) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val bundle = intent.extras
        secondButton.text = "Add"
        secondButton.setOnClickListener {
            startActivity<AddListActivity>()
        }


        items.add(Item(1, Date().getTime(), Colors.RED,
                "關於Android Tutorial的事情.", "Hello content", "", "", 0.0, 0.0, 0))
        items.add(Item(2, Date().getTime(), Colors.BLUE,
                "一隻非常可愛的小狗狗!", "她的名字叫「大熱狗」，又叫\n作「奶嘴」，是一隻非常可愛\n的小狗。", "", "", 0.0, 0.0, 0))
        items.add(Item(3, Date().getTime(), Colors.GREEN,
                "一首非常好聽的音樂！", "Hello content", "", "", 0.0, 0.0, 0))

        listView.adapter = itemAdapter
        listView.setOnItemClickListener { parent, view, position, id ->
            items[position].isSelected = !items[position].isSelected
            itemAdapter.set(position, items[position])
//            super.onBackPressed()
        }
        GlobalScope.launch {
            val a = doSomeWork()
            Log.e("TAG", a.toString())
        }.start()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_second, menu)
        return super.onCreateOptionsMenu(menu)
    }
    suspend fun aaaa() {
        doSomeWork()
    }

    suspend fun doSomeWork(): Long {
        return coroutineScope {
            val answer1 = async { sum(1, 10000) }
            val answer2 = async { sum(2, 100000) }
            answer1.await() + answer2.await()
        }
    }

    fun sum(id: Int, num: Long): Long {
        var n: Long = 0
        for (i in 0..num) {
            println("COUNT$id : " + n.toString())
            n += i
        }
        return n
    }
}
