package com.obrasocialsjd.magicline.activities.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.obrasocialsjd.magicline.activities.main.general.MainActivity

abstract class BaseFragment : Fragment() {

    var isModal : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null && arguments!!.getBoolean("SHOW_BOTTOM_BAR")) {
            isModal = arguments!!.getBoolean("SHOW_BOTTOM_BAR")
        }
    }

    override fun onStart() {
        super.onStart()

        (activity as MainActivity).manageBottomBar(isModal)
    }
}


