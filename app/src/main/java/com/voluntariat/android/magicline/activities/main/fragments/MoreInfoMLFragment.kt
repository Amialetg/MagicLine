package com.voluntariat.android.magicline.activities.main.fragments

import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.voluntariat.android.magicline.R
import com.voluntariat.android.magicline.R.drawable.*
import com.voluntariat.android.magicline.models.MoreInfoMLModel
import kotlinx.android.synthetic.main.toolbar_appbar_top.*
import kotlinx.android.synthetic.main.toolbar_appbar_top.view.*
import com.voluntariat.android.magicline.R.string.*
import kotlinx.android.synthetic.main.fragment_more_info_ml.view.*
import android.text.Spannable
import android.text.style.StyleSpan




class MoreInfoMLFragment : Fragment() {

    private lateinit var moreInfoMLView: View
    private lateinit var moreInfoMLDataModel: MoreInfoMLModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        moreInfoMLView = inflater.inflate(R.layout.fragment_more_info_ml, container, false)
        initToolbar()
        return moreInfoMLView
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(topToolbar)
        moreInfoMLView.topToolbar.title = getString(R.string.ml)
        moreInfoMLView.topToolbar.navigationIcon = ContextCompat.getDrawable(context, ic_black_cross)
        moreInfoMLView.topToolbar.setNavigationOnClickListener { activity.onBackPressed() }

        val text: String = getString(walk_text_1) + " "
        val text2: String =  "<b>"+ getString(walk_text_2)+ "</b>" + " "
        val text3: String = getString(walk_text_3) + " "
        val text4: String = "<b>"+ getString(walk_text_4) +"</b>"
        val textView = moreInfoMLView.firstWalkText
        textView.text = Html.fromHtml(text + text2 + text3 + text4)
    }
}
