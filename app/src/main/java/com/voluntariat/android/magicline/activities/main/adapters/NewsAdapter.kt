package com.voluntariat.android.magicline.activities.main.adapters

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.voluntariat.android.magicline.R
import com.voluntariat.android.magicline.models.NewsModel

class NewsAdapter(private var dataSet : List<NewsModel> = listOf()) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.findViewById(R.id.news_item_title)
        val desc : TextView = itemView.findViewById<TextView>(R.id.news_item_desc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.model_news, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //Get information from the new item
        holder.title.text = dataSet[position].title
        holder.desc.text = Html.fromHtml(dataSet[position].description)

    }
    override fun getItemCount() = dataSet.size

    fun loadItems (news: List<NewsModel>) {
        dataSet = news
    }
}
