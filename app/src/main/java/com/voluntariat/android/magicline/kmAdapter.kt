package com.voluntariat.android.magicline

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


/**
 * Created by hector on 27/06/18.
 */

class kmAdapter (val kmList : ArrayList<Int>) : RecyclerView.Adapter<kmAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.km_cards, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return kmList.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val km : Int =  kmList[position]

        holder?.km?.text = km.toString()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val km = itemView.findViewById(R.id.map_km) as TextView
    }
}