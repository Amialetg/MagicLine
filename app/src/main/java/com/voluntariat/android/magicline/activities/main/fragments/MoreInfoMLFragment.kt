package com.voluntariat.android.magicline.activities.main.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.voluntariat.android.magicline.R
import com.voluntariat.android.magicline.R.drawable.*
import com.voluntariat.android.magicline.models.MoreInfoMLModel
import kotlinx.android.synthetic.main.toolbar_appbar_top.view.*

class MoreInfoMLFragment : Fragment() {

    private lateinit var moreInfoMLView: View
    private lateinit var moreInfoMLDataModel: MoreInfoMLModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        moreInfoMLView =  inflater.inflate(R.layout.fragment_more_info_ml, container, false)
        initToolbar()
        return moreInfoMLView
    }

    private fun initToolbar() {
        moreInfoMLView.topToolbar.title = getString(R.string.magic_line_var_title)
        moreInfoMLView.topToolbar.navigationIcon = ContextCompat.getDrawable(context, ic_black_cross)
    }

}
