package com.example.jackz.activities

import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jackz.R
import com.example.jackz.adapters.MainAdapter
import com.example.jackz.adapters.SaveSettings
import com.example.jackz.models.ResultData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_locations.*
import okhttp3.*
import java.io.IOException

        /* Class to show results in a list */

class ActivitiesQuestions : AppCompatActivity() {

    private lateinit var saveSetting: SaveSettings
    lateinit var seekbar : SeekBar
    lateinit var kmCount : TextView
    var longitude : String ="13.628245"
    var latitude : String ="52.343136"
    var radius : Int =1000
    var type : String ="type=lodging"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        //SharedPreferences state to load a theme
        saveSetting = SaveSettings(this)
        if (saveSetting.loadThemeState() == true) {
            setTheme(R.style.darkTheme)
        }else
            setTheme(R.style.AppTheme)

        //set content to xml file
        setContentView(R.layout.activity_activitiesquestions)

        //set custom toolbar.xml
        setSupportActionBar(findViewById(R.id.toolbar))

        // get the type of the search through extended intent
        type = intent.getStringExtra("type").toString()
        println(type+" übergabe")

        // find the seekBar and the textview that it wants to update
        seekbar = findViewById(R.id.seekBar) as SeekBar
        kmCount = findViewById(R.id.kmRadius) as TextView

        //set the seekBar to a maximum and its default value
        seekBar.max = 5
        seekBar.setProgress(1)

        //set the OnChangeListener on the seekbar to update results
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            // this function updates the textview
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {

                // Display the current progress of SeekBar and set it to <1 if its 0
                if (progress == 0) {
                    var progress2 = progress + 1
                    kmCount.text = "<$progress2 km"
                } else
                    kmCount.text = "$progress km"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            //this functions is looking for new Results as soon as you let go
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                var progress = seekBar.progress
                if(progress == 0){
                    progress = 999
                }
                else {
                    progress = progress * 1000
                }
                println(progress)

                //call to get updated results with the new radius in form of progress
                fetchJson(longitude, latitude,progress,type)

            }
        })

        //set up the RecyclerView
        var recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        //load results with the default value
        fetchJson(longitude,latitude,radius,type)

    }

    /**
     * JSON API CALL
     */
    fun fetchJson(longitude: String, latitude: String, radius: Int, type: String){
        println(type)
        println("Attempting to Fetch Json")

        //our personal token string
        var apiTokenString = "&key=AIzaSyA8YAmNesahwa9H3EJJVs9DrfQ6MbHyIRg"

        //custom url we build to fetch customized result based on position, radius, and the type
        var  url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+latitude+","+longitude+"&radius="+radius+""+apiTokenString+"&"+type
        println(url)

        var request = Request.Builder().url(url).build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body : String? = response?.body()?.string()
                //println(body)

                val gson:Gson = GsonBuilder().create()
                val resultData = gson.fromJson(body, ResultData::class.java)
                //println(resultData.results[0].place_id)

                runOnUiThread{
                    recyclerView.adapter = MainAdapter(resultData)
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to execute request")
                println(call)
                println(e)
            }
        })
    }

}


