package com.voluntariat.android.magicline.activities.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.voluntariat.android.magicline.R
import com.voluntariat.android.magicline.activities.main.adapters.ScheduleAdapter
import com.voluntariat.android.magicline.models.ScheduleCardModel
import com.voluntariat.android.magicline.models.ScheduleGeneralModel
import com.voluntariat.android.magicline.models.ScheduleTextModel

class ScheduleFragment: Fragment(){

    //recycler widgets
    lateinit var scheduleRecyclerView: RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_schedule, container,  false)
    }

    override fun onStart() {
        super.onStart()

        initWidgets()

        initScheduleRecycler()
    }

    private fun initWidgets(){
        scheduleRecyclerView = view!!.findViewById(R.id.schedule_recycler)
    }

    private fun initScheduleRecycler(){
        val dataSet = getDataset()

        val myScheduleManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        val myScheduleAdapter = ScheduleAdapter(dataSet)

        scheduleRecyclerView.layoutManager = myScheduleManager
        scheduleRecyclerView.adapter = myScheduleAdapter
    }

    private fun getDataset(): Array<ScheduleGeneralModel>{
        return arrayOf(ScheduleTextModel("9:30", "Salida"), ScheduleCardModel("10:30", "Picnik", "Equipaments culturals obren les portes", "In recent years people have realized the importance of proper diet and exercise, and recent surveys"),ScheduleTextModel("12:30", "Tornar a caminar"), ScheduleCardModel("13:30", "Espectacle", "Equipaments culturals obren les portes", "In recent years people have realized the importance of proper diet and exercise, and recent surveys"),ScheduleTextModel("15:00", "Caminar una mica més"), ScheduleCardModel("16:30", "Concerts", "Equipaments culturals obren les portes", "In recent years people"))
    }
}