package com.voluntariat.android.magicline.activities.main.fragments

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.voluntariat.android.magicline.R
import com.voluntariat.android.magicline.utils.MyCounter
import java.text.SimpleDateFormat
import java.util.*

class CountdownFragment:Fragment(){

    //CountDown widgets
    lateinit var txtDies: TextView
    lateinit var txtHores: TextView
    lateinit var txtMin: TextView
    lateinit var txtSeg: TextView
    private lateinit var dateCursaString: String


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater?.inflate(R.layout.fragment_countdown, container, false)!!
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val txtArray = initWidgets()
        initCountDown(txtArray)
    }

    private fun initWidgets():Array<TextView>{
        //Countdown
        txtDies=view!!.findViewById(R.id.countdown_dies)
        txtHores=view!!.findViewById(R.id.countdown_hores)
        txtMin=view!!.findViewById(R.id.countdown_min)
        txtSeg=view!!.findViewById(R.id.countdown_seg)

        //cursa date
        dateCursaString= getString(R.string.cursa_date)

        return arrayOf(txtDies, txtHores, txtMin, txtSeg)
    }

    private fun initCountDown(txtDies: Array<TextView>){

        //Utilitzem el formatter per aconseguir l'objecte Date
        var formatter = SimpleDateFormat("dd.MM.yyyy, HH:mm")

        //Data actual y data de la cursa
        var currentTime: Date = Calendar.getInstance().time
        var dateCursa: Date = formatter.parse(dateCursaString)

        //pasem a long les dates
        var currentLong:Long=currentTime.time
        var cursaLong:Long=dateCursa.time

        //trobem el temps restant en long
        var diff:Long=cursaLong-currentLong


        MyCounter(diff, 1000, txtDies).start()

    }
}