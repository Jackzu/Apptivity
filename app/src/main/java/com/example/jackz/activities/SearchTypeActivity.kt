package com.example.jackz.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.jackz.R
import com.example.jackz.adapters.SaveSettings

/* displays activity_seachtype and leads to next activitys */
class SearchTypeActivity : AppCompatActivity() {

    //variables
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
        setContentView(R.layout.activity_searchtype)

        // toolbar
        setSupportActionBar(findViewById(R.id.toolbar))

        /** call activity_started.xml */
        val textviewNewSearch = findViewById(R.id.searchtype_newsearch) as CardView

        textviewNewSearch.setOnClickListener {
            val intent = Intent(this, StartedActivity::class.java)
            startActivity(intent)
        }

        /** call activity_savedsearch.xml */
        val textviewSavedSearch = findViewById(R.id.searchtype_savedsearch) as CardView

        textviewSavedSearch.setOnClickListener {
            val intent = Intent(this, SavedSearchActivity::class.java)
            startActivity(intent)
        }

    }

}