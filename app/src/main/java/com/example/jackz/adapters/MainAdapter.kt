package com.example.jackz.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jackz.R
import com.example.jackz.activities.*
import com.example.jackz.models.ResultData
import kotlinx.android.synthetic.main.list_item.view.*


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
        // holder?.view.imgLocation?. = location.photos[0].photo_reference
    }

}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view){

}