package com.example.jackz.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.jackz.R
import com.example.jackz.adapters.SaveSettings
import kotlinx.android.synthetic.main.activity_main.*

class StartedActivity : AppCompatActivity() {

    private lateinit var saveSetting: SaveSettings

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        //SharedPreferences state

        saveSetting = SaveSettings(this)
        if (saveSetting.loadThemeState() == true) {
            setTheme(R.style.darkTheme)
        }else
            setTheme(R.style.AppTheme)

        setContentView(R.layout.activity_started)

        setSupportActionBar(findViewById(R.id.toolbar))

        /** call category views */
        val categoryAcitvity = findViewById(R.id.category_activity) as CardView
        val categoryFood = findViewById(R.id.category_restaurant) as CardView
        val categoryAccomendation = findViewById(R.id.category_sleeping) as CardView
        val categoryShopping = findViewById(R.id.category_shopping) as CardView

        categoryAcitvity.setOnClickListener {
            val intent = Intent(this, ActivitiesQuestions::class.java)
            startActivity(intent)
        }
        categoryFood.setOnClickListener {
            val intent = Intent(this, ActivitiesQuestions::class.java)
            startActivity(intent)
        }
        categoryAccomendation.setOnClickListener {
            val intent = Intent(this, ActivitiesQuestions::class.java)
            startActivity(intent)
        }
        categoryShopping.setOnClickListener {
            val intent = Intent(this, ActivitiesQuestions::class.java)
            startActivity(intent)
        }
    }


}