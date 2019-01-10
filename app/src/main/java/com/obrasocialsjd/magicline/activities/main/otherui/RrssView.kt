package com.obrasocialsjd.magicline.activities.main.otherui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.obrasocialsjd.magicline.R
import kotlinx.android.synthetic.main.layout_rrss.view.fbButton
import kotlinx.android.synthetic.main.layout_rrss.view.instaButton
import kotlinx.android.synthetic.main.layout_rrss.view.twitterButton

class RrssView constructor(
        context: Context,
        attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var fbListener: (() -> Unit)? = null
    var instaListener: (() -> Unit)? = null
    var twitterListener: (() -> Unit)? = null

    init {
        View.inflate(context, R.layout.layout_rrss, this)
        orientation = LinearLayout.VERTICAL

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.RrssView)
        background = attributes.getDrawable(R.styleable.RrssView_background)
        attributes.recycle()

        fbButton.setOnClickListener { fbListener?.invoke() }
        instaButton.setOnClickListener { instaListener?.invoke() }
        twitterButton.setOnClickListener { twitterListener?.invoke() }
    }
}