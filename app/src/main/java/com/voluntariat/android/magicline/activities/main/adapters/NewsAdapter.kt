package com.voluntariat.android.magicline.activities.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import com.voluntariat.android.magicline.R
import com.voluntariat.android.magicline.data.apimodels.Post
import com.voluntariat.android.magicline.data.apimodels.PostsItem

class NewsAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<NewsAdapter.ViewHolder>(){

    lateinit var data: List<PostsItem>

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

        holder.title.text = data[position].title
        holder.desc.text = data[position].text

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = data.size

    fun loadItems (posts : List<PostsItem>) {
        data = posts
    }
}
