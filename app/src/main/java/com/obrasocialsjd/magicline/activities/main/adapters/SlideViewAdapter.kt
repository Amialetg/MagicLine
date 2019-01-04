package com.voluntariat.android.magicline.activities.main.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.obrasocialsjd.magicline.R
import kotlinx.android.synthetic.main.fragment_detail.view.*

class SlideViewAdapter(private var dataSet : List<ImageView> = listOf()) : RecyclerView.Adapter<SlideViewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.imgDetail
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_detail, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image = dataSet[position]
    }
    override fun getItemCount() = dataSet.size

    fun loadItems (images: List<ImageView>) {
        dataSet = images
    }
}
