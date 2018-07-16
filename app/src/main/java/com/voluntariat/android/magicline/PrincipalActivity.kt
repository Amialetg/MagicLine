package com.voluntariat.android.magicline

import android.graphics.drawable.GradientDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.TextView
import com.voluntariat.android.magicline.utils.MyCounter
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit




class PrincipalActivity : AppCompatActivity() {


    //CountDown widgets
    lateinit var txtDies: TextView
    lateinit var txtHores: TextView
    lateinit var txtMin: TextView
    lateinit var txtSeg: TextView
    private val dateCursaString: String = "24.02.2019, 10:00"

    //News section widgets
    lateinit var newsRecyclerView: RecyclerView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        //Init TextViews, etc
        var counterTexts = initWidgets()


        initCountDown(counterTexts)

        initNewsRecycler()



    }

    private fun initWidgets(): Array<TextView>{

        //Countdown
        txtDies=findViewById(R.id.countdown_dies)
        txtHores=findViewById(R.id.countdown_hores)
        txtMin=findViewById(R.id.countdown_min)
        txtSeg=findViewById(R.id.countdown_seg)

        //News
        newsRecyclerView=findViewById(R.id.news_recycler)


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

    private fun initNewsRecycler(){
        val dataSet = arrayOf(NewsItem("Nou event en la programació", "In recent years people have realized the importance of proper diet and exercise, and recent surveys show that over the  otal ruta."), NewsItem("Segon event", "qewhvroeuyvfwouryvfowuer"))

        val myNewsManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val myNewsAdapter = NewsAdapter(dataSet)

        newsRecyclerView.layoutManager = myNewsManager
        newsRecyclerView.adapter = myNewsAdapter

        //Adding pager behaviour
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(newsRecyclerView)

        //Adding the page indicators



    }
}
