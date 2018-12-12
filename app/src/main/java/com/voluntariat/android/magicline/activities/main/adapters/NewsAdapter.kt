package com.voluntariat.android.magicline.activities.main.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import com.voluntariat.android.magicline.models.NewsModel
import com.voluntariat.android.magicline.R

class NewsAdapter(private val dataSet: Array<NewsModel>): androidx.recyclerview.widget.RecyclerView.Adapter<NewsAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView){
        val title = itemView.findViewById<TextView>(R.id.news_item_title)
        val desc = itemView.findViewById<TextView>(R.id.news_item_desc)

    }

    // Create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // create a new view
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.model_news, parent, false)

        return ViewHolder(itemView)
    }

    // Replace the contents of a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //Get information from the new item

        holder.title.text = dataSet[position].title
        holder.desc.text = dataSet[position].description

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
