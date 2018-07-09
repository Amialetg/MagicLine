package com.voluntariat.android.magicline

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.TextView
import com.voluntariat.android.magicline.utils.MyCounter
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit




class PrincipalActivity : AppCompatActivity() {

    lateinit var txtDies: TextView
    lateinit var txtHores: TextView
    lateinit var txtMin: TextView
    lateinit var txtSeg: TextView
    private val dateCursaString: String = "24.02.2019, 10:00"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        //Init TextViews, etc
        var counterTexts = initWidgets()


        initCountDown(counterTexts)



    }

    private fun initWidgets(): Array<TextView>{
        txtDies=findViewById(R.id.countdown_dies)
        txtHores=findViewById(R.id.countdown_hores)
        txtMin=findViewById(R.id.countdown_min)
        txtSeg=findViewById(R.id.countdown_seg)

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
