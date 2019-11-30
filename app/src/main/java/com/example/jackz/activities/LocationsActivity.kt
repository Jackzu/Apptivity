package com.example.jackz.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jackz.adapters.LocationsAdapter
import com.example.jackz.R
import com.example.jackz.adapters.SaveSettings
import com.example.jackz.models.Supplier
import kotlinx.android.synthetic.main.activity_locations.*

class LocationsActivity : AppCompatActivity() {

    private lateinit var saveSetting: SaveSettings

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
    }

    private fun setupRecyclerView() {

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager

        val adapter= LocationsAdapter(this, Supplier.Locations, Supplier.LocationsTime, Supplier.LocationsPicture)
        recyclerView.adapter = adapter

    }
}