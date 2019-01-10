package com.obrasocialsjd.magicline.activities.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.obrasocialsjd.magicline.R
import com.obrasocialsjd.magicline.data.models.posts.PostImageItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_details.view.*

class SlideViewNewsImgAdapter(private var dataSet: List<PostImageItem> ) : RecyclerView.Adapter<SlideViewNewsImgAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideViewNewsImgAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_details, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SlideViewNewsImgAdapter.ViewHolder, position: Int) {
        val uri: String? = dataSet[position].img
        Picasso.get().load(uri).resize(0,350).centerInside().into(holder.image)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.imgDetail
    }

    override fun getItemCount(): Int = dataSet.size
}
