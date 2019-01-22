package com.obrasocialsjd.magicline.activities.main.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.obrasocialsjd.magicline.R
import com.obrasocialsjd.magicline.models.ScheduleCardModel
import com.obrasocialsjd.magicline.models.ScheduleGeneralModel
import com.obrasocialsjd.magicline.models.ScheduleTextModel
import com.obrasocialsjd.magicline.utils.*
import kotlinx.android.synthetic.main.model_schedule_card.view.*
import kotlinx.android.synthetic.main.model_schedule_text.view.*

class ScheduleAdapter(private var dataSet: List<ScheduleGeneralModel>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    class ViewHolderText(itemView: View) : RecyclerView.ViewHolder(itemView){
        val hour = itemView.scheduleHour
        val text = itemView.scheduleText
    }

    class ViewHolderCard(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val subtitle = itemView.scheduleCardSubtitle

        fun bind(scheduleCardModel: ScheduleCardModel) {
            itemView.scheduleCardHour.text = scheduleCardModel.hour
            itemView.scheduleCardTitle.text = scheduleCardModel.title
            itemView.scheduleCardSubtitle.text = scheduleCardModel.subtitle
            itemView.scheduleCardDescription.text = scheduleCardModel.description
            itemView.seeMoreBtn.setOnClickListener{ scheduleCardModel.listener.invoke(scheduleCardModel.detailModel) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(true) {
            (dataSet[position].type == TYPE_SCHEDULE_TITLE_FIRST) -> TYPE_SCHEDULE_TITLE_FIRST
            (dataSet[position].type == TYPE_SCHEDULE_TITLE_COMMON) -> TYPE_SCHEDULE_TITLE_COMMON
            (dataSet[position].type == TYPE_COMMON_CARD) -> TYPE_COMMON_CARD
            else  -> TYPE_LAST_CARD
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {
        return when(viewType) {
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
        when(getItemViewType(position)) {
            TYPE_SCHEDULE_TITLE_FIRST-> {
                setInfo(holder, position, false)
            }
            TYPE_COMMON_CARD-> {
                setInfo(holder, position, true)
            }
            TYPE_SCHEDULE_TITLE_COMMON-> {
                setInfo(holder, position, false)
            }
            else -> {
                setInfo(holder, position, true)
            }
        }
    }

    private fun setInfo(holder: RecyclerView.ViewHolder, position: Int, isCard: Boolean) {
        if(!isCard) {
            val textViewHolder = holder as ViewHolderText
            val textModel = dataSet[position] as ScheduleTextModel
            textViewHolder.hour.text = textModel.hour
            textViewHolder.text.text = textModel.text
            if(textModel.isSelected){
                textViewHolder.hour.setTextColor(Color.RED)
                textViewHolder.itemView.isSelected = true
            }
        }else {
            val cardModel = dataSet[position] as ScheduleCardModel
            if(holder is ViewHolderCard) holder.bind(cardModel)
            holder.itemView.scheduleCardTitle.text = cardModel.title
            holder.itemView.scheduleCardHour.text = cardModel.hour
            holder.itemView.scheduleCardSubtitle.text = cardModel.subtitle
            holder.itemView.scheduleCardDescription.text = cardModel.description
            if(cardModel.isSelected) {
                holder.itemView.scheduleCardHour.setTextColor(Color.RED)
                holder.itemView.isSelected = true
            }
        }
    }

    override fun getItemCount() = dataSet.size
}