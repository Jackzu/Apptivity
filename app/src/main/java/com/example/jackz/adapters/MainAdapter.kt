package com.example.jackz.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.jackz.R
import com.example.jackz.activities.*
import com.example.jackz.models.Coordinates
import com.example.jackz.models.PlaceResultObject
import com.example.jackz.models.ResultData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item.view.*
import okhttp3.*
import java.io.IOException
import java.util.*


class MainAdapter(val ResultData: ResultData) : RecyclerView.Adapter<CustomViewHolder>(){

    //number of items
    override fun getItemCount(): Int {
        return ResultData.results.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        //how create?
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.list_item, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val location = ResultData.results.get(position)
        holder.view.txvTitle?.text = location.name


        val entryPicture = holder?.view?.imgLocation

        var piclink:String
        if(location.photos != null){
            piclink = buildPictureString(location.photos[0]!!.photo_reference!!,"AIzaSyA8YAmNesahwa9H3EJJVs9DrfQ6MbHyIRg","400","400")
        }else{
            piclink = "https://www.cvent-assets.com/brand-page-guestside-site/assets/images/venue-card-placeholder.png"
        }

        Picasso.with(holder.view.context).load(piclink).into(entryPicture)

        var mapsString = buildMapsLink(location.geometry.location)
        holder.view.txvLink.text = mapsString
        holder.view.txvPicture.text =piclink

    }

    fun buildPictureString(photoReference: String, apiToken: String, maxWidth: String, maxHeight:String): String {

        var baseString = "https://maps.googleapis.com/maps/api/place/photo?"

        baseString = baseString + "key=" + apiToken
        baseString = baseString + "&photo_reference=" + photoReference
        baseString = baseString + "&maxwidth=" + maxWidth
        baseString = baseString + "&maxheight=" + maxHeight

        return baseString
    }

    fun buildMapsLink(coordinates: Coordinates? ):String{

        var baseString = "http://maps.google.com/maps?q="
        baseString = baseString + "" + coordinates?.lat
        baseString = baseString + "," + coordinates?.lng

        return baseString

    }

}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view){
    init {
        view.setOnClickListener{
            val intent = Intent(view.context, ResultActivity::class.java)

            val valueTitle = view.txvTitle.getText().toString()
            val valueLink = view.txvLink.getText().toString()
            val valuePicture = view.txvPicture.getText().toString()

            intent.putExtra("title","$valueTitle")
            intent.putExtra("link","$valueLink")
            intent.putExtra("picture","$valuePicture")

            view.context.startActivity(intent)
        }
    }


}
