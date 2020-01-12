package com.example.jackz.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.jackz.R
import com.example.jackz.adapters.SaveSettings

/* Site that lists subcategories base on extras in intent */
class StartedRefineActivity : AppCompatActivity() {

    //variables
    private lateinit var saveSetting: SaveSettings

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        //SharedPreferences state to load a theme
        saveSetting = SaveSettings(this)
        if (saveSetting.loadThemeState() == true) {
            setTheme(R.style.darkTheme)
        } else
            setTheme(R.style.AppTheme)

        //read extra intent and set view accordingly
        val type = intent.getStringExtra("type").toString()

        /** type activity */

        if(type== "type=activity") {

            setContentView(R.layout.activity_started_refine)

            //now get every view, set an OnClickListener and give that view to startAct()
            //not ideal solution but at first it wasnt supposed this many options
            val amusement = findViewById(R.id.amusement_park) as CardView
            amusement.setOnClickListener{
                startAct(amusement)
            }

            val aquarium = findViewById(R.id.aquarium) as CardView
            aquarium.setOnClickListener{
                startAct(aquarium)
            }

            val art_gallery = findViewById(R.id.art_gallery) as CardView
            art_gallery.setOnClickListener{
                startAct(art_gallery)
            }

            val bowling_alley = findViewById(R.id.bowling_alley) as CardView
            bowling_alley.setOnClickListener{
                startAct(bowling_alley)
            }

            val casino = findViewById(R.id.casino) as CardView
            casino.setOnClickListener{
                startAct(casino)
            }
            val gym = findViewById(R.id.gym) as CardView
            gym.setOnClickListener{
                startAct(gym)
            }
            val library = findViewById(R.id.library) as CardView
            library.setOnClickListener{
                startAct(library)
            }
            val movie_rental = findViewById(R.id.movie_rental) as CardView
            movie_rental.setOnClickListener{
                startAct(movie_rental)
            }
            val movie_theater = findViewById(R.id.movie_theater) as CardView
            movie_theater.setOnClickListener{
                startAct(movie_theater)
            }
            val museum = findViewById(R.id.museum) as CardView
            museum.setOnClickListener{
                startAct(museum)
            }
            val night_club = findViewById(R.id.night_club) as CardView
            night_club.setOnClickListener{
                startAct(night_club)
            }
            val park = findViewById(R.id.park) as CardView
            park.setOnClickListener{
                startAct(park)
            }
            val stadium = findViewById(R.id.stadium) as CardView
            stadium.setOnClickListener{
                startAct(stadium)
            }
            val zoo = findViewById(R.id.zoo) as CardView
            zoo.setOnClickListener{
                startAct(zoo)
            }
        }

        /** type restaurant */

        if(type== "type=restaurant"){
            setContentView(R.layout.activity_started_refine_restaurant)

            val bakery = findViewById(R.id.bakery) as CardView
            bakery.setOnClickListener{
                startAct(bakery)
            }
            val bar = findViewById(R.id.bar) as CardView
            bar.setOnClickListener{
                startAct(bar)
            }
            val cafe = findViewById(R.id.cafe) as CardView
            cafe.setOnClickListener{
                startAct(cafe)
            }
            val meal_takeaway = findViewById(R.id.meal_takeaway) as CardView
            meal_takeaway.setOnClickListener{
                startAct(meal_takeaway)
            }
            val restaurant = findViewById(R.id.restaurant) as CardView
            restaurant.setOnClickListener{
                startAct(restaurant)
            }
        }

        /** type shops */

        if(type== "type=shops"){
            setContentView(R.layout.activity_started_refine_shops)

            val bicycle_store = findViewById(R.id.bicycle_store) as CardView
            bicycle_store.setOnClickListener{
                startAct(bicycle_store)
            }
            val book_store = findViewById(R.id.book_store) as CardView
            book_store.setOnClickListener{
                startAct(book_store)
            }
            val car_dealer = findViewById(R.id.car_dealer) as CardView
            car_dealer.setOnClickListener{
                startAct(car_dealer)
            }
            val clothing_store = findViewById(R.id.clothing_store) as CardView
            clothing_store.setOnClickListener{
                startAct(clothing_store)
            }
            val department_store = findViewById(R.id.department_store) as CardView
            department_store.setOnClickListener{
                startAct(department_store)
            }
            val drugstore = findViewById(R.id.drugstore) as CardView
            drugstore.setOnClickListener{
                startAct(drugstore)
            }
            val electronics_store = findViewById(R.id.electronics_store) as CardView
            electronics_store.setOnClickListener{
                startAct(electronics_store)
            }
            val florist = findViewById(R.id.florist) as CardView
            florist.setOnClickListener{
                startAct(florist)
            }
            val furniture_store = findViewById(R.id.furniture_store) as CardView
            furniture_store.setOnClickListener{
                startAct(furniture_store)
            }
            val grocery_or_supermarket = findViewById(R.id.grocery_or_supermarket) as CardView
            grocery_or_supermarket.setOnClickListener{
                startAct(grocery_or_supermarket)
            }
            val jewelry_store = findViewById(R.id.jewelry_store) as CardView
            jewelry_store.setOnClickListener{
                startAct(jewelry_store)
            }
            val pet_store = findViewById(R.id.pet_store) as CardView
            pet_store.setOnClickListener{
                startAct(pet_store)
            }
        }

        //toolbar
        setSupportActionBar(findViewById(R.id.toolbar))

    }

    //function to start ActivitiesQuestions and parse extra according to the views name
    fun startAct(view: CardView){
            val intent = Intent(this, ActivitiesQuestions::class.java)

            val viewid = view.id

            var value = view.context.resources.getResourceEntryName(viewid)
            value= "type="+value
            intent.putExtra("type","$value")

            startActivity(intent)
        }
}