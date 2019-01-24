package com.obrasocialsjd.magicline.utils

import android.content.Context
import android.content.res.TypedArray
import com.obrasocialsjd.magicline.R
import com.obrasocialsjd.magicline.models.DetailModel
import com.obrasocialsjd.magicline.models.ScheduleCardModel
import com.obrasocialsjd.magicline.models.ScheduleGeneralModel
import com.obrasocialsjd.magicline.models.ScheduleTextModel
import java.util.*

fun getListeners(context: Context, onClickListener: (DetailModel) -> Unit): List<ScheduleGeneralModel> {
    val arraySchedule: TypedArray = context.resources.obtainTypedArray(R.array.arrayScheduleHoursTimeStamp)
    val arrayScheduleHour: Array<String> = context.resources.getStringArray(R.array.arrayScheduleHours)
    val arrayScheduleTitle: Array<String> = context.resources.getStringArray(R.array.arrayScheduleTitle)
    val arrayScheduleSubtitle: Array<String> = context.resources.getStringArray(R.array.arrayScheduleSubTitle)
    val arrayScheduleBody: Array<String> = context.resources.getStringArray(R.array.arrayScheduleBody)
    var listSchedule: ArrayList<ScheduleGeneralModel> = arrayListOf()

    arrayScheduleTitle.withIndex().forEach { (i, item) ->
        val scheduleGeneralModel:ScheduleGeneralModel
        var type: Int

        val isLast = i == arrayScheduleTitle.size -1
        val isFirst  = i == 0
        val isCard = arrayScheduleBody[i].isNotEmpty()

        type = when (true) {
            isLast && isCard     -> TYPE_LAST_CARD
            isFirst && !isCard   -> TYPE_SCHEDULE_TITLE_FIRST
            !isFirst && !isCard  -> TYPE_SCHEDULE_TITLE_COMMON
            !isFirst && isCard   -> TYPE_COMMON_CARD
            else -> TYPE_SCHEDULE_TITLE_COMMON
        }

        scheduleGeneralModel = if (isCard) {
            ScheduleCardModel(arrayScheduleHour[i], arrayScheduleTitle[i], arrayScheduleSubtitle[i], arrayScheduleBody[i],
                    detailModel = DetailModel(title = arrayScheduleTitle[i], subtitle = arrayScheduleSubtitle[i], textBody = arrayScheduleBody[i], link = context.getString(R.string.essentials_viewOnWeb)),
                    thisType = type,
                    isSelected = isTheMagicLineDateAndHour(context, arrayScheduleHour[i]),
                    listener = onClickListener
            )
        }else {
            ScheduleTextModel(arrayScheduleHour[i], arrayScheduleTitle[i], type, isTheMagicLineDateAndHour(context, hour = arrayScheduleHour[i]))
        }

        listSchedule.add(scheduleGeneralModel)
    }

    return listSchedule
}

fun isTheMagicLineDateAndHour (context: Context, hour :String): Boolean{
    val hoursArray = context.resources.obtainTypedArray(R.array.arrayScheduleHoursTimeStamp)
    val scheduleHour = hour.toString().replace(COLON, PERIOD)

    val now = System.currentTimeMillis() / MILLIS

    val timeInMillis = java.text.SimpleDateFormat("HH:mm").format(java.util.Date(now * MILLIS))

    val currentTime = timeInMillis.toString().replace(COLON, PERIOD)
    //currentTime = "12.0" //TODO : para hacer testing - Cambiar currentTime type a VAR

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
    val diff: Long = dateLong - cTime
    //  diff = 0 //TODO : para hacer testing - Cambiar diff type a VAR

    if(scheduleHour.toFloat() <= isTheOne && currentTime.toFloat() <= (hoursArray.getFloat(hoursArray.length()-1, Float.MAX_VALUE)/MILLISECONDS) + 2 && diff == 0L && scheduleHour.toFloat() <= currentTime.toFloat() && isTheOne == scheduleHour.toFloat()) return true
    return false
}

