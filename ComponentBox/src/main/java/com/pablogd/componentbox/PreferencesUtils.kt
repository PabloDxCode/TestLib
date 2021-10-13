package com.pablogd.componentbox

import android.content.Context
import android.content.SharedPreferences

class PreferencesUtils(private val context: Context, private var preferencesName: String? = null) {

    private val sharePreferences: SharedPreferences by lazy {
        context.getSharedPreferences(preferencesName ?: "MAIN_PREFERENCES", Context.MODE_PRIVATE)
    }

    fun save(key: String, value: Int) {
        val edit = sharePreferences.edit()
        edit.putInt(key, value)
        edit.apply()
    }

    fun save(key: String, value: String) {
        val edit = sharePreferences.edit()
        edit.putString(key, value)
        edit.apply()
    }

    fun save(key: String, value: Boolean) {
        val edit = sharePreferences.edit()
        edit.putBoolean(key, value)
        edit.apply()
    }

    fun getString(key: String, def: String = "") = sharePreferences.getString(key, def)

    fun getInt(key: String, def: Int = 0) = sharePreferences.getInt(key, def)

    fun getBoolean(key: String, def: Boolean = false) = sharePreferences.getBoolean(key, def)

}