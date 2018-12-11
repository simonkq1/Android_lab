package com.jetec.bleproj.Global

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Suppress("UNREACHABLE_CODE")
class Preference<T>(context: Context, val key: String, val default: T): ReadWriteProperty<Any?, T> {
    val prefs: SharedPreferences by lazy { context.getSharedPreferences("DEFAULT", Context.MODE_PRIVATE) }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreference(key, default)
    }


    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(key, value)
    }

    private fun<T> findPreference(key: String, default: T): T = with(prefs) {
        val res: Any = when (default) {
            is Long -> getLong(key, default)
            is String -> getString(key, default)
            is Int -> getInt(key, default)
            is Boolean -> getBoolean(key, default)
            is Float -> getFloat(key, default)
            else -> throw IllegalArgumentException("This type cannot be saved into preference!")
        }
        return res as T
    }

    private fun<T> putPreference(key: String, value: T) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(key, value)
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Float -> putFloat(key, value)
            else -> throw IllegalArgumentException("This type cannot be saved into preference!")
        }.apply()
    }



}