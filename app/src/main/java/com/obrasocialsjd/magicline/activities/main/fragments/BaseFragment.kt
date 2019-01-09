package com.obrasocialsjd.magicline.activities.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.obrasocialsjd.magicline.activities.main.general.MainActivity
import com.obrasocialsjd.magicline.utils.SHOW_BOTTOM_BAR_TAG
import com.obrasocialsjd.magicline.utils.SHOW_SHARE_VIEW_TAG

abstract class BaseFragment : Fragment() {

    private var isModal : Boolean = false

    private var hasShareView : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { arguments ->
            isModal = arguments.getBoolean(SHOW_BOTTOM_BAR_TAG, false)
            hasShareView = arguments.getBoolean(SHOW_SHARE_VIEW_TAG, false)
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).manageBottomBar(isModal)
        (activity as MainActivity).manageShareView(hasShareView)
    }
}


