package com.obrasocialsjd.magicline.activities.main.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.obrasocialsjd.magicline.activities.main.general.MainActivity
import com.obrasocialsjd.magicline.utils.SHOW_BOTTOM_BAR_TAG

abstract class BaseFragment : Fragment() {

    private var isModal : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { arguments ->
            isModal = arguments.getBoolean(SHOW_BOTTOM_BAR_TAG, false)
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).manageBottomBar(isModal)
    }

    fun openActivity(shareIntent: Intent){
        startActivity(Intent.createChooser(shareIntent, "Share with"))
    }
}


