package com.voluntariat.android.magicline.activities.main.fragments

import com.voluntariat.android.magicline.R.color.light_red
import com.voluntariat.android.magicline.R.color.mesque_background
import com.voluntariat.android.magicline.R.drawable.ic_black_cross
import com.voluntariat.android.magicline.R.string.walk_text_1
import kotlinx.android.synthetic.main.toolbar_appbar_top.view.*

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.toolbar_appbar_top.*
import com.voluntariat.android.magicline.R.string.*
import kotlinx.android.synthetic.main.fragment_more_info_ml.view.*
import androidx.core.content.res.ResourcesCompat
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.voluntariat.android.magicline.R
import com.voluntariat.android.magicline.models.MoreInfoMLModel
import com.voluntariat.android.magicline.utils.htmlToSpanned
import kotlinx.android.synthetic.main.fragment_more_info_ml.*
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.BarData

class MoreInfoMLFragment : BaseFragment() {

    private lateinit var moreInfoMLView: View
    private lateinit var moreInfoMLDataModel: MoreInfoMLModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        moreInfoMLView = inflater.inflate(R.layout.fragment_more_info_ml, container, false)
        initToolbar()
        return moreInfoMLView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        createBarChart()
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
        setUpPieChartData()
    }

    private fun setUpPieChartData() {

        val yVals = ArrayList<PieEntry>()
        yVals.add(PieEntry(20f))
        yVals.add(PieEntry(80f))


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

    companion object {
        fun newInstance(): BaseFragment {
            return MoreInfoMLFragment()
        }
    }
}




