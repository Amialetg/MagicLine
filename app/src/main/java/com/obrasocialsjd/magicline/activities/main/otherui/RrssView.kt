package com.obrasocialsjd.magicline.activities.main.otherui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.obrasocialsjd.magicline.R
import kotlinx.android.synthetic.main.layout_rrss.view.*

class RrssView constructor(
        context: Context,
        attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var fbListener: (() -> Unit)? = null
    var instaListener: (() -> Unit)? = null
    var twitterListener: (() -> Unit)? = null

    var DEFAULT_MODE = 0
    var SHARE_DETAIL_MODE = 1

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.RrssView)
        background = attributes.getDrawable(R.styleable.RrssView_background)
        val mode = attributes.getInt(R.styleable.RrssView_shareMode, DEFAULT_MODE)

        var layout = when(mode) {
            SHARE_DETAIL_MODE -> R.layout.layout_rrss_detail
            else -> R.layout.layout_rrss
        }

        View.inflate(context, layout, this)
        orientation = LinearLayout.VERTICAL

        attributes.recycle()

        fbButton.setOnClickListener { fbListener?.invoke() }
        instaButton.setOnClickListener { instaListener?.invoke() }
        twitterButton.setOnClickListener { twitterListener?.invoke() }
    }
}