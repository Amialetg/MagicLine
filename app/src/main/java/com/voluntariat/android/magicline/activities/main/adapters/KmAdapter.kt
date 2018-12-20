package com.voluntariat.android.magicline.activities.main.adapters

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.android.gms.maps.GoogleMap
import com.google.maps.android.data.kml.KmlLayer
import com.voluntariat.android.magicline.R
import kotlinx.android.synthetic.main.km_cards.view.*


/**
 * Created by hector on 27/06/18.
 */

class KmAdapter (private val kmList : ArrayList<Int>, private val googleMap: GoogleMap,
                 val context: Context) : RecyclerView.Adapter<KmAdapter.ViewHolder>() {

    var selectedPosition : Int = 0
    var kmlLayers : ArrayList<KmlLayer> = arrayListOf(
            KmlLayer(googleMap, R.raw.ml_bcn_10km,context),
            KmlLayer(googleMap, R.raw.ml_bcn_15km,context),
            KmlLayer(googleMap,R.raw.ml_bcn_20km,context),
            KmlLayer(googleMap,R.raw.ml_bcn_30km,context),
            KmlLayer(googleMap,R.raw.ml_bcn_30km_ll,context),
            KmlLayer(googleMap,R.raw.ml_bcn_40km,context))


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.km_cards, parent, false)
        return ViewHolder(v, this)
    }

    override fun getItemCount(): Int {
        return kmList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val km : Int =  kmList[position]
        val colorBg : Int
        val colorTxt : Int

        if (selectedPosition == position) {
            colorBg = ContextCompat.getColor(holder.itemView.context, R.color.colorPrimary)
            colorTxt = ContextCompat.getColor(context, R.color.white)
            if (!kmlLayers[selectedPosition].isLayerOnMap) kmlLayers[selectedPosition].addLayerToMap()
        } else {
            colorBg = ContextCompat.getColor(context, R.color.white)
            colorTxt = Color.parseColor("#80000000")
            for(i:Int in 0 until kmList.size){
                if( i != selectedPosition && kmlLayers[i].isLayerOnMap) kmlLayers[i].removeLayerFromMap()
            }
        }

        holder.itemView.TextViewMapNumKm.text = km.toString()
        holder.itemView.TextViewMapNumKm.setTextColor(colorTxt)
        holder.itemView.TextViewMapTextKm.setTextColor(colorTxt)
        holder.itemView.CardRoute.setCardBackgroundColor(colorBg)
    }

    class ViewHolder(itemView: View, adapter: KmAdapter) : RecyclerView.ViewHolder(itemView) {
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