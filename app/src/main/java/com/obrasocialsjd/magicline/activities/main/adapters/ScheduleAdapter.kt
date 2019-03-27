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

    class ViewHolderText(itemView: View) : RecyclerView.ViewHolder(itemView)

    class ViewHolderCard(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(scheduleCardModel: ScheduleCardModel) {
            itemView.scheduleCardHour.text = scheduleCardModel.hour
            itemView.scheduleCardTitle.text = scheduleCardModel.title
            itemView.scheduleCardSubtitle.text = scheduleCardModel.subtitle
            itemView.scheduleCardDescription.text = scheduleCardModel.body
            itemView.seeMoreBtn.setOnClickListener{ scheduleCardModel.listener.invoke(scheduleCardModel.detailModel) }
        }
    }
    class ViewHolderCardNoButton(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(scheduleCardModel: ScheduleCardModel) {
            itemView.scheduleCardHour.text = scheduleCardModel.hour
            itemView.scheduleCardTitle.text = scheduleCardModel.title
            itemView.scheduleCardSubtitle.text = scheduleCardModel.subtitle
            itemView.scheduleCardDescription.text = scheduleCardModel.body
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(true) {
            (dataSet[position].type == TYPE_SCHEDULE_TITLE_FIRST) -> TYPE_SCHEDULE_TITLE_FIRST
            (dataSet[position].type == TYPE_SCHEDULE_TITLE_COMMON) -> TYPE_SCHEDULE_TITLE_COMMON
            (dataSet[position].type == TYPE_COMMON_CARD) -> TYPE_COMMON_CARD
            (dataSet[position].type == TYPE_LAST_CARD) -> TYPE_LAST_CARD
            (dataSet[position].type == TYPE_FIRST_CARD) -> TYPE_FIRST_CARD
            (dataSet[position].type == TYPE_SCHEDULE_TITLE_COMMON_NO_BUTTON) -> TYPE_SCHEDULE_TITLE_COMMON_NO_BUTTON
            (dataSet[position].type == TYPE_SCHEDULE_TITLE_COMMON_LAST) -> TYPE_SCHEDULE_TITLE_COMMON_LAST

            else  -> TYPE_SCHEDULE_TITLE_COMMON_LAST
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
            TYPE_LAST_CARD -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.model_schedule_card_last, parent, false)
                ViewHolderCard(itemView)
            }
            TYPE_FIRST_CARD -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.model_schedule_card_first, parent, false)
                ViewHolderCard(itemView)
            }
            TYPE_SCHEDULE_TITLE_COMMON_NO_BUTTON -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.model_schedule_card_nobutton, parent, false)
                ViewHolderCardNoButton(itemView)
            }
            TYPE_SCHEDULE_TITLE_COMMON_LAST ->{
                // DEFAULT TYPE_COMMON_TEXT_LAST
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.model_schedule_text_last, parent, false)
                ViewHolderText(itemView)
            } else -> {
                // DEFAULT
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.model_schedule_text_last, parent, false)
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
            TYPE_LAST_CARD -> {
                setInfo(holder, position, true)
            }
            TYPE_FIRST_CARD -> {
                setInfo(holder, position, true)
            }
            TYPE_SCHEDULE_TITLE_COMMON_NO_BUTTON -> {
                setInfo(holder, position, true)
            }
            TYPE_SCHEDULE_TITLE_COMMON_LAST-> {
                setInfo(holder, position, false)
            }
            else  -> {
                // DEFAULT TYPE COMMON CARD
                setInfo(holder, position, true)
            }
        }
    }

    private fun setInfo(holder: RecyclerView.ViewHolder, position: Int, isCard: Boolean) {
        if(!isCard) {
            val textViewHolder = holder as ViewHolderText
            val textModel = dataSet[position] as ScheduleTextModel
            textViewHolder.itemView.scheduleHour.text = textModel.hour
            textViewHolder.itemView.scheduleText.text = textModel.text
            if(textModel.isSelected){
                textViewHolder.itemView.scheduleHour.setTextColor(Color.RED)
                textViewHolder.itemView.isSelected = true
            }
        }else {
            val cardModel = dataSet[position] as ScheduleCardModel
            if(holder is ViewHolderCard) holder.bind(cardModel)
            holder.itemView.scheduleCardTitle.text = cardModel.title
            holder.itemView.scheduleCardHour.text = cardModel.hour
            holder.itemView.scheduleCardSubtitle.text = cardModel.subtitle
            holder.itemView.scheduleCardDescription.text = cardModel.body
            if(cardModel.isSelected) {
                holder.itemView.scheduleCardHour.setTextColor(Color.RED)
                holder.itemView.isSelected = true
            }
        }
    }

    override fun getItemCount() = dataSet.size
}