package com.example.jackz.activities

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jackz.R
import com.example.jackz.adapters.MainAdapter
import com.example.jackz.adapters.SaveSettings
import com.example.jackz.models.ResultData
import com.google.android.gms.location.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_activitiesquestions.*
import okhttp3.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/* Class to show results in a list */

class ActivitiesQuestions : AppCompatActivity() {

    private lateinit var saveSetting: SaveSettings
    lateinit var seekbar : SeekBar
    lateinit var kmCount : TextView
    //var longitude : String ="13.628245"
    var longitude : String = ""
    //var latitude : String ="52.343136"
    var latitude : String = ""
    var radius : Int =1000
    var type : String ="type=lodging"


    var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    val INTERVAL: Long = 120000
    val FASTEST_INTERVAL: Long = 60000
    lateinit var mLastLocation: Location
    internal lateinit var mLocationRequest: LocationRequest
    val REQUEST_PERMISSION_LOCATION = 10


    lateinit var txtLat: String
    lateinit var txtLong: String
    lateinit var txtTime: String

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
                    radius = progress
                }
                else {
                    progress = progress * 1000
                    radius = progress
                }
                println(progress)

                //call to get updated results with the new radius in form of progress
                fetchJson(longitude, latitude,progress,type)

            }
        })

        /*** GPS Action ON Create ***/
        mLocationRequest = LocationRequest()


        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps()
        }

        println("gps incomming")

        if (checkPermissionForLocation(this)) {
            startLocationUpdates()
            println("gps next")
            //println(gps)
            //btnStartupdate.isEnabled = false
            //btnStopUpdates.isEnabled = true
        }
        //println(mLastLocation.latitude)


        /*** GPS Action On Create ***/

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
        println(latitude)
        println(longitude)

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

    fun onLocationChanged(location: Location) {
        // New location has now been determined

        mLastLocation = location
        val date: Date = Calendar.getInstance().time
        val sdf = SimpleDateFormat("hh:mm:ss a")
        txtTime = "Updated at : " + sdf.format(date)
        txtLat = "LATITUDE : " + mLastLocation.latitude
        txtLong = "LONGITUDE : " + mLastLocation.longitude
        // You can now create a LatLng Object for use with maps
    }

    fun buildAlertMessageNoGps() {

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                startActivityForResult(
                    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    , 11)
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.cancel()
                finish()
            }
        val alert: AlertDialog = builder.create()
        alert.show()


    }

    fun checkPermissionForLocation(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
                true
            } else {
                // Show the permission request
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSION_LOCATION)
                false
            }
        } else {
            true
        }
    }

    fun startLocationUpdates() {

        // Create the location request to start receiving updates

        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest!!.setInterval(INTERVAL)
        mLocationRequest!!.setFastestInterval(FASTEST_INTERVAL)

        // Create LocationSettingsRequest object using location request
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest!!)
        val locationSettingsRequest = builder.build()

        val settingsClient = LocationServices.getSettingsClient(this)
        settingsClient.checkLocationSettings(locationSettingsRequest)

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            return
        }
        mFusedLocationProviderClient!!.requestLocationUpdates(mLocationRequest, mLocationCallback,
            Looper.myLooper())
    }

    val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            // do work here
            locationResult.lastLocation
            latitude  = locationResult.lastLocation.latitude.toString()
            longitude = locationResult.lastLocation.longitude.toString()
            onLocationChanged(locationResult.lastLocation)
            fetchJson(longitude,latitude,radius,type)
        }
    }


}


