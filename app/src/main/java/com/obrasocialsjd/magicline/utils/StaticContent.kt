package com.obrasocialsjd.magicline.utils

import android.content.Context
import android.content.res.TypedArray
import com.obrasocialsjd.magicline.R
import com.obrasocialsjd.magicline.models.DetailModel
import com.obrasocialsjd.magicline.models.ScheduleCardModel
import com.obrasocialsjd.magicline.models.ScheduleGeneralModel
import com.obrasocialsjd.magicline.models.ScheduleTextModel
import java.util.*
import kotlin.collections.ArrayList

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

        if (arrayScheduleSubtitle[i].isNotEmpty()){
            type = when {
                i!= arrayScheduleTitle.size-1 -> TYPE_COMMON_CARD
                else -> TYPE_LAST_CARD
            }
            if (i == arrayScheduleTitle.size-1) { type = TYPE_LAST_CARD }
            scheduleGeneralModel = ScheduleCardModel(arrayScheduleHour[i], arrayScheduleTitle[i], arrayScheduleSubtitle[i], arrayScheduleBody[i],
                    detailModel = DetailModel(title = arrayScheduleTitle[i], subtitle = arrayScheduleSubtitle[i], textBody = arrayScheduleBody[i], link = context.getString(R.string.essentials_viewOnWeb)),
                    thisType = type,
                    isSelected = isTheMagicLineDateAndHour(context, arrayScheduleHour[i]),
                    listener = onClickListener
            )

        }else {
            type = when {
                i!= TYPE_SCHEDULE_TITLE_FIRST -> TYPE_SCHEDULE_TITLE_COMMON
                else -> TYPE_SCHEDULE_TITLE_FIRST
            }
            if (i == arrayScheduleTitle.size-1) { type = TYPE_LAST_CARD }

            scheduleGeneralModel = ScheduleTextModel(arrayScheduleHour[i], arrayScheduleTitle[i], type, isTheMagicLineDateAndHour(context, hour = arrayScheduleHour[i]))
        }
        listSchedule.add(scheduleGeneralModel)
    }
    return listSchedule
//    when (getFlavor()) {
//        BARCELONA -> {
//            return listOf(
//                ScheduleTextModel(arrayScheduleHour[0], arrayScheduleTitle[0], 0, isTheMagicLineDateAndHour(context, hour = arrayScheduleHour[0])),
//                ScheduleCardModel(arrayScheduleHour[1], arrayScheduleTitle[1], arrayScheduleSubtitle[1], arrayScheduleBody[1],
//                        detailModel = DetailModel(
//                                title = arrayScheduleTitle[1],
//                                subtitle = arrayScheduleSubtitle[1],
//                                textBody = arrayScheduleBody[1],
//                                link = context.getString(R.string.essentials_viewOnWeb)
//                        ),
//                        type = 1,
//                        isSelected = isTheMagicLineDateAndHour(context, arrayScheduleHour[1]),
//                        listener = onClickListener
//                ),
//                ScheduleTextModel(arrayScheduleHour[3], arrayScheduleTitle[3], 2, isTheMagicLineDateAndHour(context,arrayScheduleHour[3])),
//                ScheduleCardModel(
//                        arrayScheduleHour[4],
//                        "Espectacle",
//                        "Equipaments culturals obren les portes",
//                        "In recent years people have realized the importance of proper diet and exercise, and recent surveys",
//                        DetailModel(
//                                title = context.getString(R.string.essentials_title),
//                                subtitle = context.getString(R.string.essentials_subtitle),
//                                textBody = context.getString(R.string.essentials_body),
//                                link = context.getString(R.string.essentials_viewOnWeb)),
//                        type = 1,
//                        isSelected = isTheMagicLineDateAndHour(context, arrayScheduleHour[4]),
//                        listener = onClickListener
//                ),
//                ScheduleTextModel(arrayScheduleHour[5], "Caminar una mica més", 2, isTheMagicLineDateAndHour(context, arrayScheduleHour[5])), // DEBERÍA DE HABER SIDO TRUE
//                ScheduleCardModel(
//                        arrayScheduleHour[6],
//                        "Concerts",
//                        "Equipaments culturals obren les portes",
//                        "In recent years people",
//                        detailModel = DetailModel(
//                                title = context.getString(R.string.essentials_title),
//                                subtitle = context.getString(R.string.essentials_subtitle),
//                                textBody = context.getString(R.string.essentials_body),
//                                link = context.getString(R.string.essentials_viewOnWeb)),
//                        type = 3,
//                        isSelected = isTheMagicLineDateAndHour(context, arrayScheduleHour[6]),
//                        listener = onClickListener)
//        )}
//        VALENCIA -> return listOf()
//        MALLORCA -> return listOf()
//        else -> { return emptyList() }
//    }
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

