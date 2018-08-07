package com.voluntariat.android.magicline.activities.main.adapters

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.voluntariat.android.magicline.R


/**
 * Created by hector on 27/06/18.
 */

class kmAdapter (val kmList : ArrayList<Int>) : RecyclerView.Adapter<kmAdapter.ViewHolder>() {

    var selectedPosition : Int = 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.km_cards, parent, false)
        return ViewHolder(v, this)
    }

    override fun getItemCount(): Int {
        return kmList.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        val km : Int =  kmList[position]
        val colorBg : Int
        val colorTxt : Int

        if(selectedPosition == position){
            colorBg = ContextCompat.getColor(holder?.itemView?.context, R.color.colorPrimary)
            colorTxt = Color.WHITE
        }
        else{
            colorBg = Color.WHITE
            colorTxt = Color.parseColor("#80000000")
        }

        holder?.km?.text = km.toString()
        holder?.card?.setCardBackgroundColor(colorBg)
        holder?.km?.setTextColor(colorTxt)


    }

    class ViewHolder(itemView: View, adapter: kmAdapter) : RecyclerView.ViewHolder(itemView) {

        val km = itemView.findViewById<TextView>(R.id.map_km) as TextView
        val card = itemView.findViewById<CardView>(R.id.card)
        
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