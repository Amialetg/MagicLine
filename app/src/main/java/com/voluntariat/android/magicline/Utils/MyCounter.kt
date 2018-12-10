package com.voluntariat.android.magicline.utils

import android.os.CountDownTimer
import android.widget.TextView
import java.text.DecimalFormat
import java.util.concurrent.TimeUnit

class MyCounter(millis:Long, interval:Long, var texts: Array<TextView>): CountDownTimer(millis, interval){


    override fun onFinish() {
        //Missatge del dia de la cursa
    }

    override fun onTick(millisToFinish: Long){

        var totalSeg = TimeUnit.MILLISECONDS.toSeconds(millisToFinish)

        val days = TimeUnit.SECONDS.toDays(totalSeg)
        val hours = TimeUnit.SECONDS.toHours(totalSeg) - days * 24
        val min = TimeUnit.SECONDS.toMinutes(totalSeg) - TimeUnit.SECONDS.toHours(totalSeg) * 60
        val sec = TimeUnit.SECONDS.toSeconds(totalSeg) - TimeUnit.SECONDS.toMinutes(totalSeg) * 60

        //Format to have: "03" instead of "3"
        val nf = DecimalFormat("#00")
        texts[0].setText(days.toString())
        texts[1].setText(nf.format(hours).toString())
        texts[2].setText(nf.format(min).toString())
        texts[3].setText(nf.format(sec).toString())

        //Log.d("PrincipalActivity", "dies $days, hores $h, min $min, seg $seg")
    }
}