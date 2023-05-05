package com.example.hwk4project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HouseAdapter (private var HouseList: List<House>) :
    RecyclerView.Adapter<HouseAdapter.MyViewHolder>() {
    /*private lateinit var listener: HouseAdapterListener

    fun setOnItemClickListener(_listener: HouseAdapterListener){
        listener = _listener
    }*/

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val textViewAddress: TextView = itemView.findViewById(R.id.houseAddress)
        val textViewBedroom: TextView = itemView.findViewById(R.id.bedroomCount)
        val textViewBathroom: TextView = itemView.findViewById(R.id.bathroomCount)
        val textViewLatitude: TextView = itemView.findViewById(R.id.houseLatitude)
        val textViewLongitude: TextView = itemView.findViewById(R.id.houseLongitude)
        val textViewPrice: TextView = itemView.findViewById(R.id.housePrice)

        /*
        init{
            itemView.setOnClickListener(){
                if(listener != null){
                    var position = adapterPosition
                    if (position != RecyclerView.NO_POSITION){
                        listener.onClick(position)
                    }
                }
            }
        }*/
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.houses_individual_items_recycler_view, parent, false)
        return MyViewHolder(view)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val house = HouseList[position]
        holder.textViewAddress.setText(house.address)
        holder.textViewBedroom.setText(house.bedroom.toString())
        holder.textViewBathroom.setText(house.bathroom.toString())
        holder.textViewLatitude.setText(house.latitude.toString())
        holder.textViewLongitude.setText(house.longitude.toString())
        val priceToString = String.format("$%,.2f", house.price)
        holder.textViewPrice.setText(priceToString)
    }
    override fun getItemCount(): Int
    {
        return HouseList.size
    }
    fun setData(list: List<House>)
    {
        HouseList = list
        notifyDataSetChanged()
    }
    /*interface HouseAdapterListener{
        fun onClick(position: Int)
    }*/

}