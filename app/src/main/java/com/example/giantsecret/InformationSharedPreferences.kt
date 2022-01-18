package com.example.giantsecret

import android.content.Context
import android.content.SharedPreferences

class InformationSharedPreferences(context: Context) {
    val PREFS_FILENAME = "information_prefs"

    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME,0)

    fun setValue(key:String, defValue:String?) {
        prefs.edit().putString(key,defValue).apply()
    }
    fun getValue(key:String):String? {
        return prefs.getString(key,null)
    }

}