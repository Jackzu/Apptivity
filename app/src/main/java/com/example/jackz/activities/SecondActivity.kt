package com.example.jackz.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jackz.Constants
import com.example.jackz.R
import com.example.jackz.adapters.SaveSettings
import com.example.jackz.showToast
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity(){

    private lateinit var saveSetting: SaveSettings

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val bundle: Bundle? = intent.extras

        bundle?.let{
            val msg = bundle.getString(Constants.USER_MSG_KEY)
            if (msg != null) {
                showToast(msg)
            }
            txtUserMessage.text = msg
        }
    }
}