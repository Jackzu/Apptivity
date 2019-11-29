package com.example.jackz.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.jackz.R
import com.example.jackz.adapters.SaveSettings
import kotlinx.android.synthetic.main.activity_main.*

class StartedActivity : AppCompatActivity() {

    private lateinit var saveSetting: SaveSettings

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        //SharedPreferences state

        saveSetting = SaveSettings(this)
        if (saveSetting.loadThemeState() == true) {
            setTheme(R.style.darkTheme)
        }else
            setTheme(R.style.AppTheme)

        setContentView(R.layout.activity_started)

        setSupportActionBar(findViewById(R.id.toolbar))
    }
}