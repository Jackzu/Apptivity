package com.example.jackz.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.jackz.R
import com.example.jackz.adapters.SaveSettings
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.result.*

class ResultActivity : AppCompatActivity() {

    lateinit var phoneNumber : TextView
    lateinit var website : TextView

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

        //set headline according to result
        result_headline.setText(intent.getStringExtra("title"))

        //set picture according to result
        var piclink = intent.getStringExtra("picture")
        Picasso.with(this).load(piclink).into(imageView)

        //open the website in the standard browser
        website = findViewById(R.id.result_website_link) as TextView

        website.setOnClickListener{
            val website = "http://"+website.text.toString()
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(website))

            startActivity(intent)
        }

        //open the phone number in Phone App
        phoneNumber = findViewById(R.id.result_phone_number) as TextView

        phoneNumber.setOnClickListener{
            val phoneString = phoneNumber.text.toString()
            dialPhoneNumber(phoneString)
        }

        //set value and onclickliistener to open result in google maps
        var resultAdressBtn = findViewById(R.id.result_adress_btn) as Button
        resultAdressBtn.setOnClickListener {

            var location = intent.getStringExtra("title")
            location = "http://maps.google.co.in/maps?q="+location
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(location))

            startActivity(intent)
        }

        //set value and onClickListener to share the result
        var resultShare = findViewById(R.id.result_share) as Button
        resultShare.setOnClickListener {
            var location = intent.getStringExtra("title")

            location = "http://maps.google.co.in/maps?q="+location
            location = location.replace("\\s".toRegex(),"")
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_SUBJECT,"$location This is where Apptivity lead us! Download now!")
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, "$location This is where Apptivity lead us! Download now!")
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Share with your friends"))
        }

    }

    //fun to open the phone app and paste the phone number
    fun dialPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

}