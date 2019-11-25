package com.example.jackz.adapters

import android.content.Context
import android.content.SharedPreferences
import com.example.jackz.R
import kotlinx.android.synthetic.main.activity_main.*

class SaveSettings(context: Context) {
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