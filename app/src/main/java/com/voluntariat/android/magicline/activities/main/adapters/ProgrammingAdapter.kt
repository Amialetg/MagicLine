package com.voluntariat.android.magicline.activities.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.voluntariat.android.magicline.R
import com.voluntariat.android.magicline.models.ProgrammingModel
import kotlinx.android.synthetic.main.model_programming.view.*

/**
 * Created by hector on 27/06/18.
 */

class ProgrammingAdapter (private val programmingEvents : ArrayList<ProgrammingModel>) : RecyclerView.Adapter<ProgrammingAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.model_programming, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return programmingEvents.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event : ProgrammingModel =  programmingEvents[position]

        holder.name.text = event.name
        holder.hour.text = event.hour
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hour = itemView.hour as TextView
        val name = itemView.name as TextView
    }
}