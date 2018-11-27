package com.voluntariat.android.magicline.activities.main.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.voluntariat.android.magicline.R
import com.voluntariat.android.magicline.activities.main.MainActivity
import com.voluntariat.android.magicline.models.DetailModel
import kotlinx.android.synthetic.main.layout_share.*


class DetailFragment : Fragment() {

    lateinit var backButton: ImageButton
    lateinit var viewOnWebButton: TextView
    lateinit var fbButton: ImageButton
    lateinit var instaButton: ImageButton
    lateinit var twitterButton: ImageButton

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
            activity.onBackPressed()
//            var i:Int = 0
//            while ( i  <  activity.supportFragmentManager.backStackEntryCount){
//                i++
//                activity.supportFragmentManager.popBackStack()
//            }
        }

        viewOnWebButton.setOnClickListener{
            openNewTabWindow("www.google.es", context)
        }
        fbButton.setOnClickListener{
            startActivity(Intent())
            openNewTabWindow("www.facebook.es", context)
        }
        instaButton.setOnClickListener{
            openNewTabWindow("www.instagram.es", context)
        }
        twitterButton.setOnClickListener{
            openNewTabWindow("www.twitter.es", context)
        }
    }

    private fun initWidgets(){
        backButton = view!!.findViewById(R.id.backArrow)
        viewOnWebButton = view!!.findViewById(R.id.viewOnWeb)
        fbButton = view!!.findViewById(R.id.fb_button)
        instaButton = view!!.findViewById(R.id.insta_button)
        twitterButton = view!!.findViewById(R.id.twitter_button)
    }

    fun openNewTabWindow(url: String, context : Context) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))

//        val uris = Uri.parse(urls)
//        val intents = Intent(Intent.ACTION_VIEW, uris)
//        val b = Bundle()
//        b.putBoolean("new_window", true)
//        intents.putExtras(b)
//        context.startActivity(intents)
    }

    private fun initDetailLayout(){
        val dataSet = getDataset()
    }


    private fun getDataset(): DetailModel{
        return DetailModel(title = "", link = "", subtitle = "", text = "")
    }
}


