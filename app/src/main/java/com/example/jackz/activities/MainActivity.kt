package com.example.jackz.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toolbar
import com.example.jackz.Constants
import com.example.jackz.R
import com.example.jackz.adapters.SaveSettings
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var saveSetting: SaveSettings
    val CAMERA_REQUEST_CODE = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        //SharedPreferences state
        saveSetting = SaveSettings(this)
        if (saveSetting.loadThemeState() == true) {
            setTheme(R.style.darkTheme)
        }else
            setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // toolbar

        setSupportActionBar(findViewById(R.id.toolbar))

        //camera
        val camera = findViewById(R.id.main_open_camera) as LinearLayout

        camera.setOnClickListener{
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }

        btnSend.setOnClickListener {

            val message: String = userMessage.text.toString()
            val intent = Intent(this, SecondActivity::class.java)

            intent.putExtra(Constants.USER_MSG_KEY, message)
            startActivity(intent)
        }

        btnShare.setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java)
            startActivity(intent)
        }

        btnRecycleView.setOnClickListener {
            val intent = Intent(this, LocationsActivity::class.java)
            startActivity(intent)
        }

        /** call activity_info.xml */

        main_btn_info.setOnClickListener {
            val intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)
        }

        /** call activity_started.xml */

        main_btn_start.setOnClickListener {
            val intent = Intent(this, SearchTypeActivity::class.java)
            startActivity(intent)
        }

    }

    // Code to switch Design

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.main_menu_theme -> {
                if (saveSetting.loadThemeState()!!) {
                    saveSetting.setThemeState(false)
                    restartApplication()
                } else {
                    saveSetting.setThemeState(true)
                    restartApplication()

                }
            }
        }
        return true
    }

    //restart Application
    private fun restartApplication(){
        val i = Intent(applicationContext, MainActivity::class.java)
        startActivity(i)
        finish()
    }
}

