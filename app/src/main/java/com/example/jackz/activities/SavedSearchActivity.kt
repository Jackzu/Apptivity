package com.example.jackz.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.jackz.R
import com.example.jackz.adapters.SaveSettings

 /* only displays activity_savedsearch so far */
class SavedSearchActivity : AppCompatActivity() {

     //variables
    private lateinit var saveSetting: SaveSettings

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        //SharedPreferences state to load a theme
        saveSetting = SaveSettings(this)
        if (saveSetting.loadThemeState() == true) {
            setTheme(R.style.darkTheme)
        } else
            setTheme(R.style.AppTheme)

        //set content to xml file
        setContentView(R.layout.activity_savedsearch)

        // toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
    }
}