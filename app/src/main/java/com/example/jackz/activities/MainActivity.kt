package com.example.jackz.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toolbar
import com.example.jackz.Constants
import com.example.jackz.R
import com.example.jackz.adapters.SaveSettings
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var saveSetting: SaveSettings

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

        btnSend.setOnClickListener {

            val message: String = userMessage.text.toString()
            val intent = Intent(this, SecondActivity::class.java)

            intent.putExtra(Constants.USER_MSG_KEY, message)
            startActivity(intent)
        }

        btnShare.setOnClickListener {

            val message: String = userMessage.text.toString()

            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, message)
            intent.type = "text/plain"

            startActivity(Intent.createChooser(intent, "Share to:"))
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
