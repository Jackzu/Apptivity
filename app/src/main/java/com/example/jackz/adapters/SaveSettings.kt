package com.example.jackz.adapters

import android.content.Context
import android.content.SharedPreferences

class SaveSettings(context: Context) {

    //variabes
    private var sharedPreferences: SharedPreferences = context.getSharedPreferences("Action", Context.MODE_PRIVATE)

    //Save Theme Mode State: True or False
    fun setThemeState(state: Boolean?){
        val editor= sharedPreferences.edit()
        editor.putBoolean("Dark",state!!)
        editor.apply()
    }

    // Load the Theme state
    fun loadThemeState(): Boolean?{
        val state:Boolean = sharedPreferences.getBoolean("Dark", false)
        return(state)
    }
}