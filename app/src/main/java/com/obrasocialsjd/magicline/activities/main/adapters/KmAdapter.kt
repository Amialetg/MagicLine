package com.obrasocialsjd.magicline.activities.main.adapters

import android.content.Context
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.data.kml.KmlLayer
import com.obrasocialsjd.magicline.R
import android.graphics.Typeface
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.maps.CameraUpdateFactory
import kotlinx.android.synthetic.main.km_cards.view.*



/**
 * Created by hector on 27/06/18.
 */

class KmAdapter (private val kmlPoints: ArrayList<LatLng>, private val kmList: ArrayList<CardKm>, private val googleMap: GoogleMap,
                 val context: Context) : RecyclerView.Adapter<KmAdapter.ViewHolder>() {

    var selectedPosition : Int = 0
    private var kmlLayers : ArrayList<KmlLayer> = arrayListOf(
            KmlLayer(googleMap, R.raw.ml_bcn_10km,context),
            KmlLayer(googleMap, R.raw.ml_bcn_15km,context),
            KmlLayer(googleMap,R.raw.ml_bcn_20km,context),
            KmlLayer(googleMap,R.raw.ml_bcn_30km_ll,context),
            KmlLayer(googleMap,R.raw.ml_bcn_30km,context),
            KmlLayer(googleMap,R.raw.ml_bcn_40km,context))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cardView = LayoutInflater.from(parent.context).inflate(R.layout.km_cards, parent, false)
        return ViewHolder(cardView, this)
    }

    override fun getItemCount(): Int {
        return kmList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val km : CardKm =  kmList[position]
        var colorBg : Int = ContextCompat.getColor(context, R.color.white)
        var colorTxt : Int = Color.parseColor("#80000000")
        var typeFace: Typeface? = ResourcesCompat.getFont(context, R.font.lato_light)

        if (selectedPosition == position) {
            colorBg = ContextCompat.getColor(holder.itemView.context, R.color.colorPrimary)
            colorTxt = ContextCompat.getColor(context, R.color.white)
            typeFace = ResourcesCompat.getFont(context, R.font.lato_bold)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kmlPoints[selectedPosition], 11.5f))

            if (!kmlLayers[selectedPosition].isLayerOnMap) kmlLayers[selectedPosition].addLayerToMap()

        } else {
            for(i:Int in 0 until kmList.size){
                if( i != selectedPosition && kmlLayers[i].isLayerOnMap) kmlLayers[i].removeLayerFromMap()
            }
        }

        holder.itemView.TextViewMapNumKm.setTextColor(colorTxt)
        holder.itemView.TextViewMapTextKm.setTextColor(colorTxt)
        holder.itemView.TextViewMapTextKm.typeface = typeFace
        holder.itemView.TextViewMapNumKm.typeface = typeFace
        holder.itemView.TextViewMapNumKm.text = km.numKm.toString()
        holder.itemView.CardRoute.setCardBackgroundColor(colorBg)
        holder.itemView.ExtraTxtView.setTextColor(colorTxt)
        holder.itemView.ExtraTxtView.typeface = typeFace
        holder.itemView.ExtraTxtView.text = km.extraTextKm
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