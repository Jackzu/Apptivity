package com.example.jackz.activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.jackz.R
import com.example.jackz.adapters.SaveSettings
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class ActivitiesQuestions : AppCompatActivity() {

    private lateinit var saveSetting: SaveSettings

    private lateinit var mTextViewResult: TextView

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


        fetchJson()


        /*var Request = new Request.Builder()
            .url(url)
            .build();
        client.newCall(request).enqueue(new Callback() {

        })*/


    }


    /**
     * JSON API CALL TEST
     */
    fun fetchJson(){
        println("Attempting to Fetch Json")
        this.mTextViewResult = findViewById(R.id.text_view_result)
        var  url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=52.343136,13.628245&radius=100&key=AIzaSyA8YAmNesahwa9H3EJJVs9DrfQ6MbHyIRg"

        var request = Request.Builder().url(url).build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body : String? = response?.body()?.string()
                println(body)
            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to execute request")
                println(call)
                println(e)
            }
        })
    }


}