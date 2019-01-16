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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

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
        paintTheCurrentTime()
        return scheduleLayoutView
    }

    override fun onStart() {
        super.onStart()
        initScheduleRecycler()
    }

    /**
     * Primero miramos el contador de días actual, si este se encuentra a 0(fecha caminataa). Entonces podemos mirar a la hora en la que nos encontramos actualmente, por rangos y
     * cambiamos el selector, en caso de que el selector esté checked pintamos la hora también (...uno de los IDs scheduleCardHour)
     **/

    private fun paintTheCurrentTime(){

        //know the current time
        var currentDateTime= LocalDateTime.now()

        var time= currentDateTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)).removeSuffix(" AM").removeSuffix(" PM")


        println(time)


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
        //pintar en el schedule la bolita de roja dependiendo si nos encontramos en la hora
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