package com.example.jackz.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jackz.R
import com.example.jackz.adapters.MainAdapter
import com.example.jackz.adapters.SaveSettings
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_locations.*
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class ActivitiesQuestions : AppCompatActivity() {

    private lateinit var saveSetting: SaveSettings

    

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

        //this.recyclerView.setBackgroundColor(Color.BLUE)

        var recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MainAdapter()

        fetchJson()


    }


    /**
     * JSON API CALL TEST
     */
    fun fetchJson(){
        println("Attempting to Fetch Json")

        var  url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=52.343136,13.628245&radius=100&key=AIzaSyA8YAmNesahwa9H3EJJVs9DrfQ6MbHyIRg"

        var request = Request.Builder().url(url).build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body : String? = response?.body()?.string()
                println(body)

                val gson:Gson = GsonBuilder().create()
                val resultData = gson.fromJson(body,ResultData::class.java)
                println(resultData.results[0].place_id)
            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to execute request")
                println(call)
                println(e)
            }
        })
    }


}

class ResultData(val results: List<Results>)

class Results(val geometry: Geometry, val icon: String, val id: String, val name: String, val photos: List<PhotoData>, val place_id: String, val reference: String, val scope: String, val types: List<String>, val vicinity: String)

class Geometry(val location: Location, val viewport: Viewport)

class Location(val lat: Double, val lng: Double)
class Viewport(val northeast: Location, val southwest: Location)

class PhotoData(val height: Int, val html_attributions: List<String>, val photo_reference: String, val width :Int)
