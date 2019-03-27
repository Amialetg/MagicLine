package com.obrasocialsjd.magicline.utils

import android.content.Context
import android.content.res.TypedArray
import androidx.core.content.res.TypedArrayUtils.getString
import com.obrasocialsjd.magicline.R
import com.obrasocialsjd.magicline.models.DetailModel
import com.obrasocialsjd.magicline.models.ScheduleCardModel
import com.obrasocialsjd.magicline.models.ScheduleGeneralModel
import com.obrasocialsjd.magicline.models.ScheduleTextModel
import java.util.*
import kotlin.collections.ArrayList

fun getListeners(context: Context, onClickListener: (DetailModel) -> Unit): List<ScheduleGeneralModel> {

    val arraySchedulePhoto: TypedArray = context.resources.obtainTypedArray(R.array.arraySchedulePhoto)
    val arrayScheduleHour: Array<String> = context.resources.getStringArray(R.array.arrayScheduleHour)
    val arrayScheduleTitle: Array<String> = context.resources.getStringArray(R.array.arrayScheduleTitle)
    val arrayScheduleSubtitle: Array<String> = context.resources.getStringArray(R.array.arrayScheduleSubTitle)
    val arrayScheduleBody: Array<String> = context.resources.getStringArray(R.array.arrayScheduleBody)
    val listSchedule: ArrayList<ScheduleGeneralModel> = arrayListOf()
    val arrayScheduleHasPhotos: Array<String> = context.resources.getStringArray(R.array.arrayhasPhoto)
    var count = 0

    arrayScheduleTitle.withIndex().forEach { (i) ->

        val listToolbarImg: MutableList<Int> = mutableListOf()
        var scheduleGeneralModel:ScheduleGeneralModel
        val type: Int

        val isLast = i == arrayScheduleTitle.size - 1
        val isFirst  = i == 0
        val isCard = arrayScheduleBody[i].length > 1
        val hasPhoto = arrayScheduleHasPhotos[i].length > 1


        type = when (true) {
            hasPhoto && !isFirst && isCard && !isLast -> TYPE_COMMON_CARD
            !isFirst && isCard && !hasPhoto-> TYPE_SCHEDULE_TITLE_COMMON_NO_BUTTON
            isLast && isCard && hasPhoto -> TYPE_LAST_CARD
            isFirst && !isCard && !hasPhoto -> TYPE_SCHEDULE_TITLE_FIRST// the last one
            isFirst && isCard && !hasPhoto -> TYPE_FIRST_CARD
            !isFirst && !isCard && !hasPhoto && !isLast -> TYPE_SCHEDULE_TITLE_COMMON

            else -> TYPE_SCHEDULE_TITLE_COMMON_LAST
        }
        //get id of each photosArray
        if(hasPhoto) {
            val id = arraySchedulePhoto.getResourceId(count, 0)
            if (id != 0) {
                val arrayDrawableId = context.resources.obtainTypedArray(id) ?: null
                arrayDrawableId?.let {
                    for (item in 0 until arrayDrawableId.length()) {
                        listToolbarImg.add(arrayDrawableId.getResourceId(item, 0))
                    }
                }
            }
            count ++
        }

        scheduleGeneralModel = if (isCard) {
            ScheduleCardModel(arrayScheduleHour[i], arrayScheduleTitle[i], arrayScheduleSubtitle[i], arrayScheduleBody[i],
                    detailModel = DetailModel(listToolbarImg = listToolbarImg, title = arrayScheduleTitle[i], subtitle = arrayScheduleSubtitle[i], textBody = arrayScheduleBody[i], link = context.getString(R.string.essentials_viewOnWeb), titleToolbar = context.resources.getString(R.string.scheduleDetailTitle)),
                    thisType = type,
                    isSelected = isTheMagicLineDateAndHour(context, arrayScheduleHour[i]),
                    listener = onClickListener
            )
        } else {
            ScheduleTextModel(arrayScheduleHour[i], arrayScheduleTitle[i], type, isTheMagicLineDateAndHour(context, hour = arrayScheduleHour[i]))
        }
        listSchedule.add(scheduleGeneralModel)
    }

    return listSchedule
}

fun isTheMagicLineDateAndHour (context: Context, hour :String): Boolean {
    val hoursArray = context.resources.obtainTypedArray(R.array.arrayScheduleHoursTimeStamp)
    val scheduleHour = hour.toString().replace(COLON, PERIOD)

    val now = System.currentTimeMillis() / MILLIS

    val timeInMillis = java.text.SimpleDateFormat("HH:mm").format(java.util.Date(now * MILLIS))

    var currentTime = timeInMillis.toString().replace(COLON, PERIOD)
   //currentTime = "9.30" //TODO : para hacer testing - Cambiar currentTime type a VAR

    var isTheOne = hoursArray.getFloat(0, Float.MAX_VALUE)/MILLISECONDS
    var auxiliar = 0.0f

    for(index in 1 until hoursArray.length()) {
        val value = hoursArray.getFloat(index, Float.MAX_VALUE)
        auxiliar = value/MILLISECONDS

        if(currentTime.toFloat() >= auxiliar) {
            isTheOne = auxiliar
        }
    }
    val cTime: Long = Calendar.getInstance().timeInMillis
    val dateLong: Long = context.resources.getString(R.string.magicLineDate).toLong()
    var diff: Long = dateLong - cTime
     // diff = 0 //TODO : para hacer testing - Cambiar diff type a VAR

    if(scheduleHour.toFloat() <= isTheOne && currentTime.toFloat() <= (hoursArray.getFloat(hoursArray.length()-1, Float.MAX_VALUE)/MILLISECONDS) + 2 && diff == 0L && scheduleHour.toFloat() <= currentTime.toFloat() && isTheOne == scheduleHour.toFloat()) return true
    return false
}

