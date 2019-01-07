package com.obrasocialsjd.magicline.activities.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.obrasocialsjd.magicline.activities.main.general.MainActivity
import com.obrasocialsjd.magicline.utils.SHOW_BOTTOM_BAR_TAG

abstract class BaseFragment : Fragment() {

    private var isModal : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null && arguments!!.getBoolean(SHOW_BOTTOM_BAR_TAG)) {
            isModal = arguments!!.getBoolean(SHOW_BOTTOM_BAR_TAG)
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).manageBottomBar(isModal)
    }
}


