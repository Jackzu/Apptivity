package com.example.jackz.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jackz.R
import com.example.jackz.activities.*
import com.example.jackz.models.Coordinates
import com.example.jackz.models.Location
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

    val locations = listOf<String>("mcdonals", "burgerking", "subway")

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
        //val location = locations.get(position)
        val location = ResultData.results.get(position)
        holder.view.txvTitle?.text = location.name


        val entryPicture = holder?.view?.imgLocation

        var piclink:String
        if(location.photos != null){
            piclink = buildPictureString(location.photos[0]!!.photo_reference!!,"AIzaSyA8YAmNesahwa9H3EJJVs9DrfQ6MbHyIRg","400","400")
            //println(piclink)
        }else{
            piclink = "https://www.cvent-assets.com/brand-page-guestside-site/assets/images/venue-card-placeholder.png"
            //println(piclink)
        }

        Picasso.with(holder.view.context).load(piclink).into(entryPicture)

        var mapsString = buildMapsLink(location.geometry.location)
        //println(mapsString)

       /* println(location.place_id)
        var myurl = GetApiData().buildUrl(location.place_id)

        GetApiData().fetchJson(myurl)
        var apiresponse = GetApiData().returnResult()
        println(apiresponse)*/

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

            view.context.startActivity(intent)
        }
    }


}

class GetApiData(){

    var url:String = ""
    var resultString:String? = null
    val apiToken = "AIzaSyA8YAmNesahwa9H3EJJVs9DrfQ6MbHyIRg"


    fun buildUrl(placeID:String):String{

        //Bauen des Call Strings für eine Location
        var url = "https://maps.googleapis.com/maps/api/place/details/json?"
        url = url + "key="      +   this.apiToken
        url = url + "&placeid=" +   placeID
        url = url + "&fields"

        return url
    }

    fun fetchJson(myurl:String){
        //this.onRequestCompleteListener = callback

        val request = Request.Builder().url(myurl).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback{

            override fun onFailure(call: Call, e: IOException) {
                //onRequestCompleteListener?.onError()
                println("error")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val body = response.body()!!.string()

                    val gson = GsonBuilder().create()
                    val placeResultObject = gson.fromJson(body, PlaceResultObject::class.java)

                    println(placeResultObject.result.place_id)

                    //parse(body)
                    //println("im done")
                }
                //onRequestCompleteListener?.onSuccess(resultString)
            }

        })
    }

    fun parse(response: String) {
        this.resultString = response  //when I debug this, it contains data I need.
    }

    fun returnResult():String?{

        return this.resultString
    }


}
