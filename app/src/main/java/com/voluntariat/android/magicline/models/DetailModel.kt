package com.voluntariat.android.magicline.models

import android.graphics.drawable.Drawable
import java.io.Serializable

//By default the colors of the toolbar are black, if you want to set it to white you have to use the "isBlack" boolean when you call the DetailModel()
data class DetailModel(val title: String, val subtitle: String, val textBody: String, val link: String, val toolbarImg: Drawable, val isBlack: Boolean = true) : Serializable