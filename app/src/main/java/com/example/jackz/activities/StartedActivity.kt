package com.example.jackz.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.jackz.R
import com.example.jackz.adapters.SaveSettings
import kotlinx.android.synthetic.main.activity_main.*

/* display different categories */
class StartedActivity : AppCompatActivity() {

    //variables
    private lateinit var saveSetting: SaveSettings

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        //SharedPreferences state to load a theme
        saveSetting = SaveSettings(this)
        if (saveSetting.loadThemeState() == true) {
            setTheme(R.style.darkTheme)
        }else
            setTheme(R.style.AppTheme)

        //set content to xml file
        setContentView(R.layout.activity_started)

        // toolbar
        setSupportActionBar(findViewById(R.id.toolbar))

        /** call category views with specific extra intent */
        val categoryAcitvity = findViewById(R.id.category_activity) as CardView
        val categoryFood = findViewById(R.id.category_restaurant) as CardView
        val categoryAccomendation = findViewById(R.id.category_sleeping) as CardView
        val categoryShopping = findViewById(R.id.category_shopping) as CardView

        categoryAcitvity.setOnClickListener {
            val intent = Intent(this, StartedRefineActivity::class.java)

            intent.putExtra("type","type=activity")

            startActivity(intent)
        }
        categoryFood.setOnClickListener {
            val intent = Intent(this, StartedRefineActivity::class.java)

            intent.putExtra("type","type=restaurant")

            startActivity(intent)
        }
        categoryAccomendation.setOnClickListener {
            val intent = Intent(this, ActivitiesQuestions::class.java)

            intent.putExtra("type","type=lodging")

            startActivity(intent)
        }
        categoryShopping.setOnClickListener {
            val intent = Intent(this, StartedRefineActivity::class.java)

            intent.putExtra("type","type=shops")

            startActivity(intent)
        }
    }

}