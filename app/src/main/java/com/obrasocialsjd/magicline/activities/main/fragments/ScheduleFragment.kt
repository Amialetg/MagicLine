package com.obrasocialsjd.magicline.activities.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obrasocialsjd.magicline.R
import com.obrasocialsjd.magicline.activities.main.adapters.ScheduleAdapter
import com.obrasocialsjd.magicline.utils.TrackingUtils
import com.obrasocialsjd.magicline.models.DetailModel
import com.obrasocialsjd.magicline.models.ScheduleGeneralModel
import com.obrasocialsjd.magicline.utils.Fragment
import com.obrasocialsjd.magicline.utils.transitionWithModalAnimation
import kotlinx.android.synthetic.main.fragment_schedule.view.*
import java.io.Serializable

class ScheduleFragment : BaseFragment() {

    //recycler widgets
    private lateinit var scheduleModel: List<ScheduleGeneralModel>
    private lateinit var scheduleLayoutView: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scheduleModel = arguments?.get(Fragment.Schedule.toString()) as List<ScheduleGeneralModel>
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        scheduleLayoutView = inflater.inflate(R.layout.fragment_schedule, container, false)
        initScheduleRecycler(scheduleLayoutView)
        return scheduleLayoutView
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
                            analyticsScreen = TrackingUtils.Screens.Detail
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
                            analyticsScreen = TrackingUtils.Screens.Detail
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
                            analyticsScreen = TrackingUtils.Screens.Detail
                    )
                }
        )
    }

    private fun initScheduleRecycler(view: View) {
        val myScheduleManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        val myScheduleAdapter = ScheduleAdapter(scheduleModel)

        view.scheduleRecyclerView.layoutManager = myScheduleManager
        view.scheduleRecyclerView.adapter = myScheduleAdapter
    }

    companion object {
        fun newInstance(scheduleModel: List<ScheduleGeneralModel>): BaseFragment {
            val myFragment = ScheduleFragment()
            val args = Bundle()
            args.putSerializable(Fragment.Schedule.toString(), scheduleModel as Serializable)
            myFragment.arguments = args
            return myFragment
        }
    }
}