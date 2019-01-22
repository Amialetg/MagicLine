package com.obrasocialsjd.magicline.activities.main.fragments

import android.annotation.SuppressLint
import android.content.res.TypedArray
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obrasocialsjd.magicline.R
import com.obrasocialsjd.magicline.activities.main.adapters.ScheduleAdapter
import com.obrasocialsjd.magicline.utils.TrackingUtil
import com.obrasocialsjd.magicline.models.DetailModel
import com.obrasocialsjd.magicline.models.ScheduleGeneralModel
import com.obrasocialsjd.magicline.utils.transitionWithModalAnimation
import kotlinx.android.synthetic.main.fragment_schedule.*
import kotlinx.android.synthetic.main.fragment_schedule.view.*
import kotlinx.android.synthetic.main.model_schedule_card.*
import kotlinx.android.synthetic.main.model_schedule_card.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class ScheduleFragment : BaseFragment() {

    //recycler widgets
    private lateinit var scheduleModel: Array<ScheduleGeneralModel>
    private lateinit var scheduleLayoutView: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scheduleModel = arguments?.get("scheduleFragment") as Array<ScheduleGeneralModel>
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        scheduleLayoutView = inflater.inflate(R.layout.fragment_schedule, container, false)
        initScheduleRecycler(scheduleLayoutView)

        return scheduleLayoutView
    }

    override fun onStart() {
        super.onStart()
    }

    private fun getListeners() : List<View.OnClickListener> {
        //todo: mejorar la maner de añadir los listeners
        return listOf(
                View.OnClickListener{},
                View.OnClickListener {
                    (activity as AppCompatActivity).transitionWithModalAnimation(
                            context = requireContext(),
                            fragment = DetailFragment.newInstance(dataModel = DetailModel(
                                    title = getString(R.string.essentials_title),
                                    subtitle = getString(R.string.essentials_subtitle),
                                    textBody = getString(R.string.essentials_body),
                                    link = getString(R.string.essentials_viewOnWeb))),
                            useModalAnimation = true,
                            addToBackStack = true,
                            analyticsScreen = TrackingUtil.Screens.Detail
                    )
                },
                View.OnClickListener{},
                View.OnClickListener {
                    (activity as AppCompatActivity).transitionWithModalAnimation(
                            context = requireContext(),
                            fragment = DetailFragment.newInstance(dataModel = DetailModel(
                                    title = getString(R.string.essentials_title),
                                    subtitle = getString(R.string.essentials_subtitle),
                                    textBody = getString(R.string.essentials_body),
                                    link = getString(R.string.essentials_viewOnWeb))),
                            useModalAnimation = true,
                            addToBackStack = true,
                            analyticsScreen = TrackingUtil.Screens.Detail
                    )
                },
                View.OnClickListener{},
                View.OnClickListener {
                    (activity as AppCompatActivity).transitionWithModalAnimation(
                            context = requireContext(),
                            fragment = DetailFragment.newInstance(dataModel = DetailModel(
                                    title = getString(R.string.essentials_title),
                                    subtitle = getString(R.string.essentials_subtitle),
                                    textBody = getString(R.string.essentials_body),
                                    link = getString(R.string.essentials_viewOnWeb))),
                            useModalAnimation = true,
                            addToBackStack = true,
                            analyticsScreen = TrackingUtil.Screens.Detail
                    )
                }
        )
    }

    private fun initScheduleRecycler(view: View) {
        //pintar en el schedule la bolita de roja dependiendo si nos encontramos en la hora
        val myScheduleManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        val myScheduleAdapter = ScheduleAdapter(scheduleModel, getListeners())

        view.scheduleRecyclerView.layoutManager = myScheduleManager
        view.scheduleRecyclerView.adapter = myScheduleAdapter
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