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

class ActivitiesQuestions : AppCompatActivity() {

    private lateinit var saveSetting: SaveSettings
    lateinit var seekbar : SeekBar
    lateinit var kmCount : TextView
    var longitude : String ="13.628245"
    var latitude : String ="52.343136"
    var radius : Int =250


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        //SharedPreferences state

        saveSetting = SaveSettings(this)
        if (saveSetting.loadThemeState() == true) {
            setTheme(R.style.darkTheme)
        }else
            setTheme(R.style.AppTheme)

        setContentView(R.layout.activity_activitiesquestions)

        setSupportActionBar(findViewById(R.id.toolbar))

        seekbar = findViewById(R.id.seekBar) as SeekBar
        kmCount = findViewById(R.id.kmRadius) as TextView

        seekBar.max = 20
        seekBar.setProgress(1)

        val type = intent.getStringExtra("type").toString()

        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // Display the current progress of SeekBar
                if (progress == 0) {
                    var progress2 = progress + 1
                    kmCount.text = "<$progress2 km"
                } else
                    kmCount.text = "$progress km"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                var progress = seekBar.progress
                progress= progress*1000
                println(progress)
                fetchJson(longitude, latitude, progress, type)

            }
        })

        //this.recyclerView.setBackgroundColor(Color.BLUE)

        var recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = MainAdapter()

        fetchJson(longitude,latitude,radius, type)


    }

    /**
     * JSON API CALL TEST
     */
    fun fetchJson(longitude: String, latitude: String, radius: Int, type: String){
        println("Attempting to Fetch Json")

        var apiTokenString = "&key=AIzaSyA8YAmNesahwa9H3EJJVs9DrfQ6MbHyIRg"

        var  url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+latitude+","+longitude+"&radius="+radius+""+apiTokenString+"&"+type

        var request = Request.Builder().url(url).build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body : String? = response?.body()?.string()
                println(body)

                val gson:Gson = GsonBuilder().create()
                val resultData = gson.fromJson(body, ResultData::class.java)
                println(resultData.results[0].place_id)



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


