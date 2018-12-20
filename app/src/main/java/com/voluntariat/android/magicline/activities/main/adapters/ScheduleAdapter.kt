package com.voluntariat.android.magicline.activities.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.voluntariat.android.magicline.R
import com.voluntariat.android.magicline.models.ScheduleCardModel
import com.voluntariat.android.magicline.models.ScheduleGeneralModel
import com.voluntariat.android.magicline.models.ScheduleTextModel
import kotlinx.android.synthetic.main.model_schedule_card.view.*
import kotlinx.android.synthetic.main.model_schedule_text.view.*

class ScheduleAdapter(private val dataSet: Array<ScheduleGeneralModel>): RecyclerView.Adapter<RecyclerView.ViewHolder> () {

    class ViewHolderText(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hour: TextView = itemView.scheduleHour
        val text: TextView = itemView.scheduleText
    }

    class ViewHolderCard(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hour: TextView = itemView.scheduleCardHour
        val title: TextView = itemView.scheduleCardTitle
        val subtitle: TextView = itemView.scheduleCardSubtitle
        val description: TextView = itemView.scheduleCardDescription
    }

    override fun getItemViewType(position: Int): Int = if (dataSet[position].type==1) 1 else 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {

        return when(viewType){
            1 -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.model_schedule_text, parent, false)
                ViewHolderText(itemView)
            } else -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.model_schedule_card, parent, false)
                ViewHolderCard(itemView)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            1 -> {
                val textViewHolder = holder as ViewHolderText
                val textModel = dataSet[position] as ScheduleTextModel

                textViewHolder.hour.text = textModel.hour
                textViewHolder.text.text = textModel.text
            } else -> {
                val cardViewHolder = holder as ViewHolderCard
                val cardModel = dataSet[position] as ScheduleCardModel
                cardViewHolder.hour.text = cardModel.hour
                cardViewHolder.title.text = cardModel.title
                cardViewHolder.subtitle.text = cardModel.subtitle
                cardViewHolder.description.text = cardModel.description
            }
        }
    }

    override fun getItemCount() = dataSet.size
}