package com.voluntariat.android.magicline.activities.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.voluntariat.android.magicline.R
import com.voluntariat.android.magicline.activities.main.adapters.ScheduleAdapter
import com.voluntariat.android.magicline.models.ScheduleCardModel
import com.voluntariat.android.magicline.models.ScheduleGeneralModel
import com.voluntariat.android.magicline.models.ScheduleTextModel

class ScheduleFragment: androidx.fragment.app.Fragment(){

    //recycler widgets
    lateinit var scheduleRecyclerView: androidx.recyclerview.widget.RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_schedule, container,  false)
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

        val myScheduleManager = androidx.recyclerview.widget.LinearLayoutManager(activity, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)
        val myScheduleAdapter = ScheduleAdapter(dataSet)

        scheduleRecyclerView.layoutManager = myScheduleManager
        scheduleRecyclerView.adapter = myScheduleAdapter
    }

    private fun getDataset(): Array<ScheduleGeneralModel>{
        return arrayOf(ScheduleTextModel("9:30", "Salida"), ScheduleCardModel("10:30", "Picnik", "Equipaments culturals obren les portes", "In recent years people have realized the importance of proper diet and exercise, and recent surveys"),ScheduleTextModel("12:30", "Tornar a caminar"), ScheduleCardModel("13:30", "Espectacle", "Equipaments culturals obren les portes", "In recent years people have realized the importance of proper diet and exercise, and recent surveys"),ScheduleTextModel("15:00", "Caminar una mica més"), ScheduleCardModel("16:30", "Concerts", "Equipaments culturals obren les portes", "In recent years people"))
    }
}