package com.jetec.fileio

import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivity
import java.lang.IllegalArgumentException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main_button.setOnClickListener {
            startActivity<FilePathActivity>()
        }

        val db = SQLiteHelper(this)
        db.use {
//            insert("member", "account" to "Simon", "password" to "s40119")
/*
            val res = db.selectQuery("select * from member")
            for (i in res) {
                for ((k, v) in i) {
                    Log.e("LOG", "Key: $k, Val: $v")
                }
            }*/

        }
//        db.delete("member", mapOf("id" to 2, "account" to "Simon"))
    }


    fun <T> putPreference(key: String, value: T) {

        val prefs: SharedPreferences.Editor = this.getSharedPreferences("default", Context.MODE_PRIVATE).edit()
        when (value) {
            is Long -> prefs.putLong(key, value)
            is String -> prefs.putString(key, value)
            is Int -> prefs.putInt(key, value)
            is Boolean -> prefs.putBoolean(key, value)
            is Float -> prefs.putFloat(key, value)
            else -> throw IllegalArgumentException("This type can be saved into Preferences")
        }
        prefs.apply()
    }


    private fun <T> findPreference(key: String, default: T): T {
        val prefs: SharedPreferences = this.getSharedPreferences("default", Context.MODE_PRIVATE)
        val res: Any = when (default) {
            is Long -> prefs.getLong(key, default)
            is String -> prefs.getString(key, default)
            is Int -> prefs.getInt(key, default)
            is Boolean -> prefs.getBoolean(key, default)
            is Float -> prefs.getFloat(key, default)
            else -> throw IllegalArgumentException("This type can be saved into Preferences")
        }
        return res as T
    }

}
