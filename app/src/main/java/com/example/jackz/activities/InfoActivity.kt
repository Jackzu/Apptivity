package com.example.jackz.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.jackz.R
import com.example.jackz.adapters.SaveSettings

    /* Just the Info/explanation  site nothing happens here */
class InfoActivity : AppCompatActivity() {

    private lateinit var saveSetting: SaveSettings

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        //SharedPreferences state to load a theme
        saveSetting = SaveSettings(this)
        if (saveSetting.loadThemeState() == true) {
            setTheme(R.style.darkTheme)
        }else
            setTheme(R.style.AppTheme)

        //set content to xml file
        setContentView(R.layout.activity_info)

        // toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
    }

}
