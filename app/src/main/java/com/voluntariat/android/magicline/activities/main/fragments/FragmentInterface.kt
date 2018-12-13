package com.voluntariat.android.magicline.activities.main.fragments

import com.voluntariat.android.magicline.activities.main.DataModelInterface

interface FragmentInterface {
    fun newInstance(detailModel: DataModelInterface): BaseFragment
}
