package com.example.jackz

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.example.jackz.adapters.SaveSettings
import java.time.Duration

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, message, duration).show()
}