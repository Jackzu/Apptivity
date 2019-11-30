package com.example.jackz.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.jackz.models.Location
import com.example.jackz.R
import com.example.jackz.showToast
import kotlinx.android.synthetic.main.list_item.view.*

class LocationsAdapter(val context: Context, private val locations: List<Location>, private val locationsTime: List<Location>, private val locationsPicture: List<Location>  ) : RecyclerView.Adapter<LocationsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val location = locations[position]
        val locationTime = locationsTime[position]
        val locationPicture = locationsPicture[position]
        holder.setData(location, locationTime, locationPicture, position)

    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var currentLocation: Location? = null
        var currentPosition: Int = 0

        init {
            itemView.setOnClickListener {
                currentLocation?.let {
                    context.showToast(currentLocation!!.title + " ist schon geil!")
                }
            }

            itemView.imgShare.setOnClickListener {

                currentLocation?.let {
                    val message: String = "Bruder lass mal zu " + currentLocation!!.title

                    val intent = Intent()
                    intent.action = Intent.ACTION_SEND
                    intent.putExtra(Intent.EXTRA_TEXT, message)
                    intent.type = "text/plain"

                    context.startActivity(Intent.createChooser(intent, "Share to:"))
                }
            }
        }

        fun setData(
            location: Location?,
            locationTime: Location?,
            locationPicture: Location?,
            pos: Int
        ) {

            location?.let {
                itemView.txvTitle.text = location.title
                itemView.txvTimes.text = locationTime!!.title

                val drawaleResource = R.drawable.mcdonalds
                itemView.imgLocation.setImageResource(drawaleResource)

                this.currentLocation = location
                this.currentPosition = pos
            }

        }

    }
}