package com.obrasocialsjd.magicline.activities.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.obrasocialsjd.magicline.R
import com.obrasocialsjd.magicline.models.ScheduleCardModel
import com.obrasocialsjd.magicline.models.ScheduleGeneralModel
import com.obrasocialsjd.magicline.models.ScheduleTextModel
import kotlinx.android.synthetic.main.model_schedule_card.view.*
import kotlinx.android.synthetic.main.model_schedule_text.view.*

class ScheduleAdapter(private var dataSet: Array<ScheduleGeneralModel>, private var listeners: List<View.OnClickListener>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val TYPE_SCHEDULE_TITLE_FIRST : Int = 0
    private val TYPE_SCHEDULE_TITLE_COMMON : Int = 2
    private val TYPE_COMMON_CARD : Int = 1
    private val TYPE_LAST_CARD : Int = 3


    class ViewHolderText(itemView: View) : RecyclerView.ViewHolder(itemView){
        val hour = itemView.scheduleHour
        val text = itemView.scheduleText
    }

    class ViewHolderCard(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val scheduleCardHour = itemView.scheduleCardHour
        val scheduleCardTitle = itemView.scheduleCardTitle
        val subtitle = itemView.scheduleCardSubtitle
        val scheduleCardDescription = itemView.scheduleCardDescription
        val seeMoreBtn = itemView.seeMoreBtn

        fun bind(cardModel: ScheduleCardModel, listener: View.OnClickListener) {
            itemView.scheduleCardHour.text = cardModel.hour
            itemView.scheduleCardTitle.text = cardModel.title
            itemView.scheduleCardSubtitle.text = cardModel.subtitle
            itemView.scheduleCardDescription.text = cardModel.description
            itemView.seeMoreBtn.setOnClickListener(listener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(true) {
            (dataSet[position].type == TYPE_SCHEDULE_TITLE_FIRST) -> TYPE_SCHEDULE_TITLE_FIRST
            (dataSet[position].type == TYPE_SCHEDULE_TITLE_COMMON) -> TYPE_SCHEDULE_TITLE_COMMON
            (dataSet[position].type == TYPE_COMMON_CARD) -> TYPE_COMMON_CARD
            //position == dataSet.size -1 -> 4
            else  -> TYPE_LAST_CARD
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {//OJO

        return when(viewType){
            TYPE_SCHEDULE_TITLE_FIRST -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.model_schedule_text_first, parent, false)
                ViewHolderText(itemView)
            }
            TYPE_SCHEDULE_TITLE_COMMON -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.model_schedule_text, parent, false)
                ViewHolderText(itemView)
            }
            TYPE_COMMON_CARD -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.model_schedule_card, parent, false)
                ViewHolderCard(itemView)
            }
            else -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.model_schedule_card_last, parent, false)
                ViewHolderText(itemView)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {//OJO

        when(getItemViewType(position)){
            0-> {
                val textViewHolder = holder as ViewHolderText
                val textModel = dataSet[position] as ScheduleTextModel
                textViewHolder.hour.text = textModel.hour
                textViewHolder.text.text = textModel.text
            }
            1-> {
                val cardModel = dataSet[position] as ScheduleCardModel
                if(holder is ViewHolderCard) holder.bind(cardModel, listeners[position])
                holder.itemView.scheduleCardTitle.text = cardModel.title
                holder.itemView.scheduleCardHour.text = cardModel.hour
                holder.itemView.scheduleCardSubtitle.text = cardModel.subtitle
                holder.itemView.scheduleCardDescription.text = cardModel.description
            }
            2-> {
                val textViewHolder = holder as ViewHolderText
                val textModel = dataSet[position] as ScheduleTextModel
                textViewHolder.hour.text = textModel.hour
                textViewHolder.text.text = textModel.text
            }
            else -> {
                val cardModel = dataSet[position] as ScheduleCardModel
                if(holder is ViewHolderCard) holder.bind(cardModel, listeners[position])
                holder.itemView.scheduleCardTitle.text = cardModel.title
                holder.itemView.scheduleCardHour.text = cardModel.hour
                holder.itemView.scheduleCardSubtitle.text = cardModel.subtitle
                holder.itemView.scheduleCardDescription.text = cardModel.description
            }
        }
    }

    override fun getItemCount() = dataSet.size
}