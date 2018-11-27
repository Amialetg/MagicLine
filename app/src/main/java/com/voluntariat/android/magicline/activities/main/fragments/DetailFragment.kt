package com.voluntariat.android.magicline.activities.main.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.voluntariat.android.magicline.R
import com.voluntariat.android.magicline.activities.main.MainActivity
import com.voluntariat.android.magicline.models.DetailModel


class DetailFragment : Fragment() {

    lateinit var backButton: ImageButton

    override fun onStart() {
        super.onStart()
        initWidgets()
        initTopBar()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    private fun initTopBar() {
        backButton.setOnClickListener{
            var i:Int = 0
            while ( i  <  activity.supportFragmentManager.backStackEntryCount){
                i++
                activity.supportFragmentManager.popBackStack()
            }
        }
    }

    private fun initWidgets(){
        backButton = view!!.findViewById(R.id.backArrow)
    }





    private fun initDetailLayout(){
        val dataSet = getDataset()
    }


    private fun getDataset(): DetailModel{
        return DetailModel(title = "", link = "", subtitle = "", text = "")
    }
}


