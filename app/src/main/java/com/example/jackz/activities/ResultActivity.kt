package com.example.jackz.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.jackz.R
import com.example.jackz.adapters.SaveSettings

class ResultActivity : AppCompatActivity() {

    lateinit var phoneNumber : TextView

    private lateinit var saveSetting: SaveSettings

    override fun onCreate(savedInstanceState: Bundle?) {

        //SharedPreferences state
        saveSetting = SaveSettings(this)
        if (saveSetting.loadThemeState() == true) {
            setTheme(R.style.darkTheme)
        } else
            setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.result)

        // toolbar

        setSupportActionBar(findViewById(R.id.toolbar))

        phoneNumber = findViewById(R.id.result_phone_number) as TextView

        phoneNumber.setOnClickListener{
            val phoneString = phoneNumber.text.toString()
            dialPhoneNumber(phoneString)
        }
    }

    fun dialPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

}