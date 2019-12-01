package com.example.jackz.activities

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.jackz.R
import com.example.jackz.adapters.SaveSettings
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class SearchTypeActivity : AppCompatActivity() {

    private lateinit var saveSetting: SaveSettings

    override fun onCreate(savedInstanceState: Bundle?) {

        //SharedPreferences state
        saveSetting = SaveSettings(this)
        if (saveSetting.loadThemeState() == true) {
            setTheme(R.style.darkTheme)
        }else
            setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
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

    /**
     * JSON API CALL TEST
     */
    fun fetchjson() {
        println("testing")
        val url = "https://api.letsbuildthatapp.com/youtube/home_feed"

        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call?, response: Response?){
                val body = response?.body()?.string()
                println(body)
            }

            override fun onFailure(call: Call?, e:IOException?){
                println("Failed to execute request")
            }
        })
    }

}