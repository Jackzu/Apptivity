package com.example.jackz.activities

import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jackz.adapters.LocationsAdapter
import com.example.jackz.R
import com.example.jackz.adapters.SaveSettings
import com.example.jackz.models.Supplier
import kotlinx.android.synthetic.main.activity_locations.*

class LocationsActivity : AppCompatActivity() {

    private lateinit var saveSetting: SaveSettings
    lateinit var seekbar : SeekBar
    lateinit var kmCount : TextView


    override fun onCreate(savedInstanceState: Bundle?) {

        //SharedPreferences state
        saveSetting = SaveSettings(this)
        if (saveSetting.loadThemeState() == true) {
            setTheme(R.style.darkTheme)
        } else
            setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locations)

        setupRecyclerView()

        // toolbar

        setSupportActionBar(findViewById(R.id.toolbar))

        // Radius Slider

        seekbar = findViewById(R.id.seekBar) as SeekBar
        kmCount = findViewById(R.id.kmRadius) as TextView

        seekBar.max = 20
        seekBar.setProgress(4)

        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // Display the current progress of SeekBar
                if (progress == 0) {
                    kmCount.text = "<$progress km"
                } else
                    kmCount.text = "$progress km"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do something

            }
        })
    }

    private fun setupRecyclerView() {

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager

        val adapter= LocationsAdapter(this, Supplier.Locations, Supplier.LocationsTime, Supplier.LocationsPicture)
        recyclerView.adapter = adapter

    }
}

