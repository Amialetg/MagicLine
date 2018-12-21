package com.voluntariat.android.magicline.activities.main.fragments

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.data.*
import com.voluntariat.android.magicline.R
import com.voluntariat.android.magicline.R.color.light_red
import com.voluntariat.android.magicline.R.color.mesque_background
import com.voluntariat.android.magicline.R.drawable.ic_black_cross
import com.voluntariat.android.magicline.R.string.*
import com.voluntariat.android.magicline.data.MagicLineRepositoryImpl
import com.voluntariat.android.magicline.db.MagicLineDB
import com.voluntariat.android.magicline.models.MoreInfoMLModel
import com.voluntariat.android.magicline.utils.htmlToSpanned
import com.voluntariat.android.magicline.viewModel.MoreInfoViewModel
import com.voluntariat.android.magicline.viewModel.MoreInfoViewModelFactory
import kotlinx.android.synthetic.main.fragment_more_info_ml.*
import kotlinx.android.synthetic.main.fragment_more_info_ml.view.*
import kotlinx.android.synthetic.main.toolbar_appbar_top.*
import kotlinx.android.synthetic.main.toolbar_appbar_top.view.*

class MoreInfoMLFragment : BaseFragment() {

    private lateinit var moreInfoMLView: View
    private lateinit var moreInfoMLDataModel: MoreInfoMLModel
    private lateinit var moreInfoViewModel : MoreInfoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        moreInfoMLView = inflater.inflate(R.layout.fragment_more_info_ml, container, false)


        var repository = MagicLineRepositoryImpl(MagicLineDB.getDatabase(requireActivity().applicationContext))
        val factory = MoreInfoViewModelFactory(requireActivity().application, repository)
        moreInfoViewModel = ViewModelProviders.of(this, factory).get(MoreInfoViewModel::class.java)
        initToolbar()

        return moreInfoMLView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        requestChartData()
        createBarChart()
    }

    private fun requestChartData() {

        moreInfoViewModel.getTotalParticipants().observe(this, androidx.lifecycle.Observer { participants ->
            val total = participants.totalParticipants
            val places = 13000
            val currentConsumedPlaces = (total * 100) / places
            val currentAvailablePlaces = 100 - currentConsumedPlaces

            currentParticipants.text = total.toString()
            configurePieChart(currentConsumedPlaces.toFloat(), currentAvailablePlaces.toFloat())

        })
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(topToolbar)
        moreInfoMLView.topToolbar.title = getString(R.string.ml)
        moreInfoMLView.topToolbar.navigationIcon = ContextCompat.getDrawable(this.requireContext(), ic_black_cross)
        moreInfoMLView.topToolbar.setNavigationOnClickListener { this.requireActivity().onBackPressed() }

        val text: String = getString(walk_text_1) + " "
        val text2: String = "<b>" + getString(walk_text_2) + "</b>" + " "
        val text3: String = getString(walk_text_3) + " "
        val text4: String = "<b>" + getString(walk_text_4) + "</b>"
        val textView = moreInfoMLView.firstWalkText

        var infoText = text + text2 + text3 + text4
        textView.text = infoText.htmlToSpanned()
    }

    private fun createBarChart() {
        setupBarChartData()
        setUpPieChartData()
    }

    private fun setUpPieChartData() {

        configurePieChart(20f, 80f)

    }

    private fun configurePieChart(pieValue: Float, pieTotal: Float) {
        val yVals = ArrayList<PieEntry>()
        yVals.add(PieEntry(pieValue))
        yVals.add(PieEntry(pieTotal))
        val dataSet = PieDataSet(yVals, "")
        dataSet.valueTextSize = 0f
        val colors = java.util.ArrayList<Int>()
        colors.add(ContextCompat.getColor(requireContext(), light_red))
        colors.add(ContextCompat.getColor(requireContext(), mesque_background))
        dataSet.colors = colors
        pieChart.animateY(2000)
        val data = PieData(dataSet)
        pieChart.data = data
        pieChart.rotationAngle = 0f
        pieChart.isHighlightPerTapEnabled = false
        pieChart.isRotationEnabled = false
        pieChart.holeRadius = 80f
        pieChart.centerText = "13.000" + "\n" + getString(R.string.vacancy)
        pieChart.setCenterTextSize(25.0f)
        var typeFace: Typeface? = ResourcesCompat.getFont(this.requireContext(), R.font.lato_light)
        pieChart.setCenterTextTypeface(typeFace)
        pieChart.legend.isEnabled = false
        pieChart.description.isEnabled = false
        pieChart.onChartGestureListener = null
    }

    private fun setupBarChartData() {

        val entries = arrayListOf<BarEntry>()
        entries.add(BarEntry(0f, 30f))
        entries.add(BarEntry(1f, 80f))
        entries.add(BarEntry(2f, 60f))
        entries.add(BarEntry(3f, 50f))

        val dataSet = BarDataSet(entries, "")
        val data = BarData(dataSet)
        dataSet.color = ContextCompat.getColor(requireContext(), light_red)
        data.barWidth = 0.15f // set custom bar width
        barChart.data = data
        barChart.legend.isEnabled = false
        barChart.setGridBackgroundColor(Color.BLUE)
        barChart.setDrawBorders(false)
        barChart.description.isEnabled = false
        barChart.setFitBars(true) // make the x-axis fit exactly all bars
        barChart.isHighlightPerTapEnabled = false
        barChart.isDoubleTapToZoomEnabled = false
        barChart.isDragEnabled = false
        barChart.isScaleXEnabled = false
        barChart.isScaleYEnabled = false
        barChart.animateY(2000)
        barChart.invalidate() // refresh
    }
    companion object {
        fun newInstance(): BaseFragment {
            return MoreInfoMLFragment()
        }
    }
}




