package com.voluntariat.android.magicline.activities.main.adapters

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.maps.android.data.kml.KmlLayer
import com.voluntariat.android.magicline.R


/**
 * Created by hector on 27/06/18.
 */

class kmAdapter (val kmList : ArrayList<Int>, val googleMap: GoogleMap,
                 val context: Context) : RecyclerView.Adapter<kmAdapter.ViewHolder>() {

    var selectedPosition : Int = 0
    var kmlLayers : ArrayList<KmlLayer> = arrayListOf(KmlLayer(googleMap,R.raw.ml_barcelona_2018_10,context),
            KmlLayer(googleMap,R.raw.ml_barcelona_2018_15,context),
            KmlLayer(googleMap,R.raw.ml_barcelona_2018_20,context),
            KmlLayer(googleMap,R.raw.ml_barcelona_2018_25,context)
            ,KmlLayer(googleMap,R.raw.ml_barcelona_2018_30,context))


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.km_cards, parent, false)
        return ViewHolder(v, this)
    }

    override fun getItemCount(): Int {
        return kmList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val km : Int =  kmList[position]
        val colorBg : Int
        val colorTxt : Int

        //DO ACTIONS WHEN BUTTON SELECTED
        if(selectedPosition == position){
            colorBg = ContextCompat.getColor(holder?.itemView?.context!!, R.color.colorPrimary)
            colorTxt = Color.WHITE
            if(!kmlLayers.get(selectedPosition).isLayerOnMap) kmlLayers.get(selectedPosition).addLayerToMap()
        }
        else{
            colorBg = Color.WHITE
            colorTxt = Color.parseColor("#80000000")
            for(i:Int in 0..4){
                if( i != selectedPosition && kmlLayers.get(i).isLayerOnMap) kmlLayers.get(i).removeLayerFromMap()
            }
        }

        holder?.km?.text = km.toString()
        holder?.card?.setCardBackgroundColor(colorBg)
        holder?.km?.setTextColor(colorTxt)
        holder?.km_text?.setTextColor(colorTxt)

        //UPDATE ROUTES

    }

    class ViewHolder(itemView: View, adapter: kmAdapter) : RecyclerView.ViewHolder(itemView) {

        val km = itemView.findViewById<TextView>(R.id.map_km) as TextView
        val card = itemView.findViewById<CardView>(R.id.card)
        val km_text = itemView.findViewById<TextView>(R.id.map_km_text)
        
        init {

            itemView.setOnClickListener{

                with(adapter){

                    notifyItemChanged(selectedPosition)
                    selectedPosition = adapterPosition
                    notifyItemChanged(selectedPosition)

                }


            }

        }

    }
}