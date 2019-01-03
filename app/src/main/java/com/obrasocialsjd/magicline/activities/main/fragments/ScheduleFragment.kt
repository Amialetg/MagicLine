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
import com.obrasocialsjd.magicline.models.DetailModel
import com.obrasocialsjd.magicline.models.ScheduleGeneralModel
import com.obrasocialsjd.magicline.utils.transitionWithModalAnimation
import kotlinx.android.synthetic.main.fragment_schedule.*

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
        return scheduleLayoutView
    }

    override fun onStart() {
        super.onStart()
        initScheduleRecycler()
    }

    private fun getListeners() : List<View.OnClickListener> {
        //todo: mejorar la maner de añadir los listeners
        return listOf(
                View.OnClickListener{},
                View.OnClickListener {
                    (activity as AppCompatActivity).transitionWithModalAnimation(
                            fragment = DetailFragment.newInstance(dataModel = DetailModel(
                                    title = getString(R.string.essentials_title),
                                    subtitle = getString(R.string.essentials_subtitle),
                                    textBody = getString(R.string.essentials_body),
                                    link = getString(R.string.essentials_viewOnWeb))),
                            useModalAnimation = true,
                            addToBackStack = true
                    )
                },
                View.OnClickListener{},
                View.OnClickListener {
                    (activity as AppCompatActivity).transitionWithModalAnimation(
                            fragment = DetailFragment.newInstance(dataModel = DetailModel(
                                    title = getString(R.string.essentials_title),
                                    subtitle = getString(R.string.essentials_subtitle),
                                    textBody = getString(R.string.essentials_body),
                                    link = getString(R.string.essentials_viewOnWeb))),
                            useModalAnimation = true,
                            addToBackStack = true
                    )
                },
                View.OnClickListener{},
                View.OnClickListener {
                    (activity as AppCompatActivity).transitionWithModalAnimation(
                            fragment = DetailFragment.newInstance(dataModel = DetailModel(
                                    title = getString(R.string.essentials_title),
                                    subtitle = getString(R.string.essentials_subtitle),
                                    textBody = getString(R.string.essentials_body),
                                    link = getString(R.string.essentials_viewOnWeb))),
                            useModalAnimation = true,
                            addToBackStack = true
                    )
                }
        )
    }

    private fun initScheduleRecycler() {
        val myScheduleManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        val myScheduleAdapter = ScheduleAdapter(scheduleModel, getListeners())

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