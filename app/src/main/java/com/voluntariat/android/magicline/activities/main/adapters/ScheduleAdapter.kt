package com.voluntariat.android.magicline.activities.main.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.voluntariat.android.magicline.R
import com.voluntariat.android.magicline.models.ScheduleCardModel
import com.voluntariat.android.magicline.models.ScheduleGeneralModel
import com.voluntariat.android.magicline.models.ScheduleTextModel
import kotlinx.android.synthetic.main.model_schedule_text.view.*

class ScheduleAdapter(private val dataSet: Array<ScheduleGeneralModel>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    class ViewHolderText(itemView: View) : RecyclerView.ViewHolder(itemView){
        val hour = itemView.findViewById<TextView>(R.id.schedule_hour)
        val text = itemView.findViewById<TextView>(R.id.schedule_text)

    }

    class ViewHolderCard(itemView: View) : RecyclerView.ViewHolder(itemView){
        val hour = itemView.findViewById<TextView>(R.id.schedule_card_hour)
        val title = itemView.findViewById<TextView>(R.id.schedule_card_title)
        val subtitle = itemView.findViewById<TextView>(R.id.schedule_card_subtitle)
        val description = itemView.findViewById<TextView>(R.id.schedule_card_description)

    }


    override fun getItemViewType(position: Int): Int {
        if(dataSet[position].type==1) return 1
        else return 2
    }

    // Create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // create a new view

        when(viewType){
            1->{
                val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.model_schedule_text, parent, false)
                return ViewHolderText(itemView)
            }

            else->{
                val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.model_schedule_card, parent, false)
                return ViewHolderCard(itemView)
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

                Log.d("Schedule Adapter", cardModel.toString())


                cardViewHolder.hour.text = cardModel.hour
                cardViewHolder.title.text = cardModel.title
                cardViewHolder.subtitle.text = cardModel.subtitle
                cardViewHolder.description.text = cardModel.description

            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}