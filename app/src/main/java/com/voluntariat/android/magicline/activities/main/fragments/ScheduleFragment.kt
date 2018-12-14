package com.voluntariat.android.magicline.activities.main.fragments

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.voluntariat.android.magicline.R
import com.voluntariat.android.magicline.activities.main.adapters.ScheduleAdapter
import com.voluntariat.android.magicline.models.ScheduleGeneralModel
import com.voluntariat.android.magicline.utils.transitionWithModalAnimation
import kotlinx.android.synthetic.main.fragment_schedule.*
import kotlinx.android.synthetic.main.model_schedule_card.view.*

class ScheduleFragment: BaseFragment() {

    //recycler widgets
    private lateinit var scheduleModel: Array<ScheduleGeneralModel>
    private lateinit var scheduleLayoutView: View
    private lateinit var scheduleCardView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scheduleModel = arguments?.get("scheduleFragment") as Array<ScheduleGeneralModel>
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        scheduleLayoutView = inflater.inflate(R.layout.fragment_schedule, container,  false)
        scheduleCardView = inflater.inflate(R.layout.model_schedule_card, container,  false)
        initWidgets()
        return scheduleLayoutView
    }

    override fun onStart() {
        super.onStart()
        initScheduleRecycler()
    }

    private fun initWidgets() {
        scheduleCardView.seeMoreBtn.setOnClickListener {
            (activity as AppCompatActivity).transitionWithModalAnimation(fragment = DetailFragment(), useModalAnimation = false, addToBackStack = false)
        }
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