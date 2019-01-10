package com.obrasocialsjd.magicline.activities.main.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.obrasocialsjd.magicline.R
import com.obrasocialsjd.magicline.activities.main.fragments.DetailFragment
import com.obrasocialsjd.magicline.activities.main.general.MainActivity
import com.obrasocialsjd.magicline.models.DetailModel
import com.obrasocialsjd.magicline.models.NewsModel
import com.obrasocialsjd.magicline.models.ScheduleCardModel
import com.obrasocialsjd.magicline.utils.htmlToSpanned
import com.obrasocialsjd.magicline.utils.transitionWithModalAnimation
import kotlinx.android.synthetic.main.fragment_info.view.*
import kotlinx.android.synthetic.main.model_news.view.*

class NewsAdapter(private var dataSet : List<NewsModel> = listOf()) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.newsItemTitle
        val subtitle: TextView = itemView.newsItemDesc

        fun bind(newsModel: NewsModel, listener: View.OnClickListener) {
            itemView.newsItemTitle.text = newsModel.title
            itemView.newsItemDesc.text = newsModel.description
            itemView.setOnClickListener(listener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.model_news, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = dataSet[position].title
        holder.subtitle.text = dataSet[position].subtitle?.htmlToSpanned()
        val newsModel = dataSet[position]
        holder.bind(
                
        )

    }
    override fun getItemCount() = dataSet.size

    fun loadItems (news: List<NewsModel>) {
        dataSet = news
    }
}
