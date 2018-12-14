package com.voluntariat.android.magicline.activities.main.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.voluntariat.android.magicline.R
import com.voluntariat.android.magicline.activities.main.adapters.ScheduleAdapter
import com.voluntariat.android.magicline.models.ScheduleGeneralModel

class ScheduleFragment: BaseFragment() {

    //recycler widgets
    lateinit var scheduleRecyclerView:RecyclerView
    private lateinit var scheduleModel: Array<ScheduleGeneralModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scheduleModel = arguments?.get("scheduleFragment") as Array<ScheduleGeneralModel>
    }
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
        val myScheduleManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val myScheduleAdapter = ScheduleAdapter(scheduleModel)

        scheduleRecyclerView.layoutManager = myScheduleManager
        scheduleRecyclerView.adapter = myScheduleAdapter
    }

    companion object {
        fun newInstance(scheduleModel: Array<ScheduleGeneralModel>): BaseFragment {
            val myFragment = ScheduleFragment()
            val args = Bundle()
            args.putSerializable("scheduleFragment", scheduleModel)
            myFragment.arguments = args
            return myFragment
        }
    }
}