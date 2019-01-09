package com.voluntariat.android.magicline.activities.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.obrasocialsjd.magicline.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_details.view.*

class SlideViewAdapter(private var dataSet : List<Int> = listOf()) : RecyclerView.Adapter<SlideViewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.imgDetail
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_details, parent, false)

        return ViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(dataSet[position]).resize(0,350).centerInside().into(holder.image)
    }
    override fun getItemCount() = dataSet.size
    fun loadItems (images: List<Int>) {
        dataSet = images
    }
}
