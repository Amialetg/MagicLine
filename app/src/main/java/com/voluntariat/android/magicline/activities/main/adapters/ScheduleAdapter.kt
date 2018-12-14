package com.voluntariat.android.magicline.activities.main.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kingfisher.easyviewindicator.R.attr.subtitle
import com.voluntariat.android.magicline.R
import com.voluntariat.android.magicline.R.id.*
import com.voluntariat.android.magicline.models.ScheduleCardModel
import com.voluntariat.android.magicline.models.ScheduleGeneralModel
import com.voluntariat.android.magicline.models.ScheduleTextModel
import kotlinx.android.synthetic.main.model_programming.view.*
import kotlinx.android.synthetic.main.model_schedule_card.view.*
import kotlinx.android.synthetic.main.model_schedule_text.view.*

class ScheduleAdapter(private val dataSet: Array<ScheduleGeneralModel>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    class ViewHolderText(itemView: View) : RecyclerView.ViewHolder(itemView){
        val hour = itemView.scheduleHour
        val text = itemView.scheduleText

    }

    class ViewHolderCard(itemView: View) : RecyclerView.ViewHolder(itemView){
        val scheduleCardHour = itemView.scheduleCardHour
        val scheduleCardTitle = itemView.scheduleCardTitle
        val subtitle = itemView.scheduleCardSubtitle
        val scheduleCardDescription = itemView.scheduleCardDescription

    }


    override fun getItemViewType(position: Int): Int {
        if(dataSet[position].type==1) return 1
        else return 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when(viewType){
            1->{
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.model_schedule_text, parent, false)
                ViewHolderText(itemView)
            }

            else->{
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.model_schedule_card, parent, false)
                ViewHolderCard(itemView)
            }
        }
    }

    // Replace the contents of a view
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //Get information from the new item
        when(getItemViewType(position)){
            1->{
                val textViewHolder = holder as ViewHolderText
                val textModel = dataSet[position] as ScheduleTextModel
                textViewHolder.hour.text = textModel.hour
                textViewHolder.text.text = textModel.text
            }
            else->{
                val cardViewHolder = holder as ViewHolderCard
                val cardModel = dataSet[position] as ScheduleCardModel
                cardViewHolder.scheduleCardHour.text = cardModel.hour
                cardViewHolder.scheduleCardTitle.text = cardModel.title
                cardViewHolder.subtitle.text = cardModel.subtitle
                cardViewHolder.scheduleCardDescription.text = cardModel.description
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}