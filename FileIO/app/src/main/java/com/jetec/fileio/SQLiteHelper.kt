package com.jetec.fileio

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper
import org.jetbrains.anko.db.update

class SQLiteHelper(var context: Context, private var DB_VERSION: Int = CURRENT_VRESION) : ManagedSQLiteOpenHelper(context, DB_NAME, null, CURRENT_VRESION) {
    companion object {
        private val TAG = "SQLiteHelper"
        var DB_NAME = "sqlite.db"
        var TABLE_NAME = "member"
        var CURRENT_VRESION = 1
        private var instance: SQLiteHelper? = null
        @Synchronized
        fun getInstance(ctx: Context, version: Int = 0): SQLiteHelper {
            if (instance == null) {
                // 如果沒傳版本編號，就使用預設的版本編號
                instance = if (version > 0) SQLiteHelper(ctx.applicationContext, version) else SQLiteHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        if (db == null) return
        val drop_sql = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(drop_sql)
        val createSQL = "create table if not exists $TABLE_NAME(id integer primary key autoincrement, account varchar(30), password varchar(50))"
        db.execSQL(createSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (newVersion > 1) {
//            var alter_sql = "alter table $TABLE_NAME add column "
        }
    }

    fun selectQuery(sql: String): MutableList<HashMap<String, Any>> {
        var dataList: MutableList<HashMap<String, Any>> = ArrayList<HashMap<String, Any>>()
        use {
            val res = this.rawQuery(sql, null)
            if (res.moveToFirst()) {
                while (true) {
                    for (i in 0..(res.columnCount - 1))
                        dataList.add(hashMapOf(res.getColumnName(i) to if (i == 0) res.getInt(i) else res.getString(i)))
                    if (res.isLast) break
                    res.moveToNext()
                }
                res.close()
            }
        }
        return dataList
    }

    fun delete(table: String = TABLE_NAME, condition: String): Int {

        var count: Int = 0
        use {
            count = delete(table, condition, null)
        }
        return count
    }

    fun update(table: String = TABLE_NAME, keyValue: Map<String, *>, condition: String): Int {
        var cv = ContentValues()
        for ((k, v) in keyValue) {
            when (v) {
                is String ->
                    cv.put(k, v)
                is Int ->
                    cv.put(k, v)
                is Long ->
                    cv.put(k, v)
                is Float ->
                    cv.put(k, v)
                is Double ->
                    cv.put(k, v)
                is Boolean ->
                    cv.put(k, v)
                is Byte ->
                    cv.put(k, v)
                is Short ->
                    cv.put(k, v)
                is ByteArray ->
                    cv.put(k, v)

            }
        }
        var count = 0
        use {
            count = update(table, cv, condition, null)
        }
        return count

    }

}

