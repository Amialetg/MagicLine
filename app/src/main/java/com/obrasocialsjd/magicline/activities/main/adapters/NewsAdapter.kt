package com.obrasocialsjd.magicline.activities.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.obrasocialsjd.magicline.R
import com.obrasocialsjd.magicline.models.NewsModel
import com.obrasocialsjd.magicline.utils.htmlToSpanned
import kotlinx.android.synthetic.main.model_news.view.*

class NewsAdapter(private var dataSet : List<NewsModel> = listOf()) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.newsItemTitle
        val desc: TextView = itemView.newsItemDesc
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.model_news, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = dataSet[position].title
        holder.desc.text = dataSet[position].description?.htmlToSpanned()
    }
    override fun getItemCount() = dataSet.size

    fun loadItems (news: List<NewsModel>) {
        dataSet = news
    }
}
