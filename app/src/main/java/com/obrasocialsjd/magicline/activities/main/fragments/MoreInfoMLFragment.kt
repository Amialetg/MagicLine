package com.obrasocialsjd.magicline.activities.main.fragments

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.obrasocialsjd.magicline.R
import com.obrasocialsjd.magicline.R.array.arrayMoreInfoML
import com.obrasocialsjd.magicline.R.color.light_red
import com.obrasocialsjd.magicline.R.color.mesque_background
import com.obrasocialsjd.magicline.R.drawable.ic_black_cross
import com.obrasocialsjd.magicline.data.MagicLineRepositoryImpl
import com.obrasocialsjd.magicline.db.MagicLineDB
import com.obrasocialsjd.magicline.utils.*
import com.obrasocialsjd.magicline.viewModel.MoreInfoViewModel
import com.obrasocialsjd.magicline.viewModel.MoreInfoViewModelFactory
import kotlinx.android.synthetic.main.fragment_more_info_ml.*
import kotlinx.android.synthetic.main.fragment_more_info_ml.view.*
import kotlinx.android.synthetic.main.toolbar_appbar_top.*
import kotlinx.android.synthetic.main.toolbar_appbar_top.view.*

class MoreInfoMLFragment : BaseFragment() {

    private lateinit var moreInfoMLView: View
    private lateinit var moreInfoViewModel : MoreInfoViewModel

    private var totalParticipants: Double = 0.0
    private var availableSpots: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        moreInfoMLView = inflater.inflate(R.layout.fragment_more_info_ml, container, false)

        val repository = MagicLineRepositoryImpl(MagicLineDB.getDatabase(requireActivity().applicationContext))
        val factory = MoreInfoViewModelFactory(requireActivity().application, repository)
        moreInfoViewModel = ViewModelProviders.of(this, factory).get(MoreInfoViewModel::class.java)
        initToolbar()
        initViews()
        initRrss()
        initListeners()

        return moreInfoMLView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requestChartData()
        createBarChart()
    }

    private fun requestChartData() {
        moreInfoViewModel.getTotalParticipants().observe(this, androidx.lifecycle.Observer { participants ->
            totalParticipants = participants.totalParticipants.toDouble()
            availableSpots = ((participants.spots + 99) / 100) * 100
            val currentAvailablePlaces = availableSpots - totalParticipants
            currentParticipants.text = totalParticipants.addThousandsSeparator()
            configurePieChart(totalParticipants.toFloat(), currentAvailablePlaces.toFloat())
        })
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(topToolbar)
        moreInfoMLView.topToolbar.title = getString(R.string.ml)
        moreInfoMLView.topToolbar.navigationIcon = ContextCompat.getDrawable(this.requireContext(), ic_black_cross)
        moreInfoMLView.topToolbar.navigationIcon?.setColorFilter(ContextCompat.getColor(this.requireContext(), R.color.black), android.graphics.PorterDuff.Mode.SRC_ATOP)
        moreInfoMLView.topToolbar.setNavigationOnClickListener { this.requireActivity().onBackPressed() }
    }

    private fun initViews() {
        val stringArray  = resources.getStringArray(arrayMoreInfoML)
        var endString = ""
        for (string in stringArray){
            endString += string
        }
        moreInfoMLView.walkText.text = getString(R.string.walkText)
        moreInfoMLView.firstWalkText.text = endString.htmlToSpanned()
    }

    private fun initRrss() {
        moreInfoMLView.rrssView.fbListener = { activity?.openShareActivity(shareApp(getString(R.string.fb_pkg))) }
        moreInfoMLView.rrssView.instaListener = { activity?.openShareActivity(shareApp(getString(R.string.insta_pkg))) }
        moreInfoMLView.rrssView.twitterListener = { activity?.openShareActivity(shareApp(getString(R.string.twitter_pkg))) }
    }

    private fun initListeners() { moreInfoMLView.moreInfoWeb.setOnClickListener{ activity?.openUrl(getString(R.string.moreInfoWeb)) } }

    private fun createBarChart() {
        setUpPieChartData()
    }

    private fun setUpPieChartData() {
        configurePieChart(20f, 80f)
    }

    private fun configurePieChart(pieValue: Float, pieTotal: Float) {
        val chartValues = ArrayList<PieEntry>()
        chartValues.add(PieEntry(pieValue))
        chartValues.add(PieEntry(pieTotal))

        val dataSet = PieDataSet(chartValues, "")
        dataSet.valueTextSize = 0f

        val colors = java.util.ArrayList<Int>()
        colors.add(ContextCompat.getColor(requireContext(), light_red))
        colors.add(ContextCompat.getColor(requireContext(), mesque_background))
        dataSet.colors = colors

        val data = PieData(dataSet)
        pieChart.data = data
        pieChart.rotationAngle = 0f
        pieChart.isHighlightPerTapEnabled = false
        pieChart.isRotationEnabled = false
        pieChart.holeRadius = 80f
        pieChart.centerText = availableSpots.toString().addThousandsSeparator() + "\n" + getString(R.string.vacancy)
        pieChart.setCenterTextSize(25.0f)
        pieChart.animateY(2000)

        val typeFace: Typeface? = ResourcesCompat.getFont(this.requireContext(), R.font.lato_light)
        pieChart.setCenterTextTypeface(typeFace)
        pieChart.legend.isEnabled = false
        pieChart.description.isEnabled = false
        pieChart.onChartGestureListener = null
    }

    companion object {
        fun newInstance(): BaseFragment {
            return MoreInfoMLFragment()
        }
    }
}




