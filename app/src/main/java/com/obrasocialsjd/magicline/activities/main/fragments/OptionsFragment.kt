package com.obrasocialsjd.magicline.activities.main.fragments

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.obrasocialsjd.magicline.R
import com.obrasocialsjd.magicline.R.drawable.about_us
import com.obrasocialsjd.magicline.R.string.*
import com.obrasocialsjd.magicline.utils.TrackingUtil
import com.obrasocialsjd.magicline.models.DetailModel
import com.obrasocialsjd.magicline.utils.PREF_LANGUAGE
import com.obrasocialsjd.magicline.utils.SPANISH
import com.obrasocialsjd.magicline.utils.transitionWithModalAnimation
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.android.synthetic.main.layout_checkboxs_info.*


class OptionsFragment: BaseFragment() {

    // 1--> SPANISH 2-->CATALAN

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_info, container,  false)
    }

    override fun onStart() {

        super.onStart()

        initLanguageSettings()

        listener()
    }

    private fun initLanguageSettings(){
        val prefs : SharedPreferences = this.requireContext().getSharedPreferences("Settings", Activity.MODE_PRIVATE )

        if(prefs.getString(PREF_LANGUAGE, "") != SPANISH){
            checkbox_catalan_text.isChecked = true
        }else{
            checkbox_spanish_text.isChecked = true
        }
        checkbox_spanish_text.setOnCheckedChangeListener { checkbox_spanish_text, isChecked ->

            if(checkbox_spanish_text.isChecked){

                if (checkbox_catalan_text.isChecked){
                    checkbox_catalan_text.isChecked = false
                }
                val editor = prefs.edit()
                editor.putString(PREF_LANGUAGE, SPANISH)
                editor.apply()
                refresh()
            }
        }
        checkbox_catalan_text.setOnCheckedChangeListener { checkbox_catalan_text, isChecked ->

            if(checkbox_catalan_text.isChecked){
                if (checkbox_spanish_text.isChecked){
                    checkbox_spanish_text.isChecked = false
                }
                val editor = prefs.edit()
                editor.putString(PREF_LANGUAGE, "_")
                editor.apply()
                refresh()
            }
        }
    }

    private fun refresh() { this.requireActivity().recreate() }

    private fun listener(){
        val urlMagicLine= getString(R.string.magicLineWeb)

        moreInfoFriendsTextView.setOnClickListener{
            (activity as AppCompatActivity).transitionWithModalAnimation(requireContext(), InviteFriendsFragment.newInstance(), analyticsScreen = TrackingUtil.Screens.InviteFriends)
        }
        webMagicLineTextView.setOnClickListener{
            callIntent(urlMagicLine)
        }
        aboutMLTextView.setOnClickListener{

            val dataModelAboutMLApp = DetailModel(
                    title = getString(aboutTheAppTitle),
                    subtitle = getString(aboutTheAppSubTitle),
                    textBody = getString(aboutTheAppBody),
                    link = getString(aboutTheAppLink),
                    listToolbarImg = listOf(about_us),
                    isBlack = false,
                    hasToolbarImg = true)

            (activity as AppCompatActivity).transitionWithModalAnimation(context = requireContext(), fragment = DetailFragment.newInstance(dataModelAboutMLApp), analyticsScreen = TrackingUtil.Screens.AboutApp)
        }
    }

    private fun callIntent(url : String){

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        this.requireContext().startActivity(intent)
    }

    companion object {
        fun newInstance(): BaseFragment {
            return OptionsFragment()
        }
    }

}