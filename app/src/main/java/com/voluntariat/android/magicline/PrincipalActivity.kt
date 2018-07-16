package com.voluntariat.android.magicline

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.voluntariat.android.magicline.utils.MyCounter
import java.text.SimpleDateFormat
import java.util.*
import android.widget.LinearLayout


class PrincipalActivity : AppCompatActivity() {


    //CountDown widgets
    lateinit var txtDies: TextView
    lateinit var txtHores: TextView
    lateinit var txtMin: TextView
    lateinit var txtSeg: TextView
    private val dateCursaString: String = "24.02.2019, 10:00"


    //Programming section widgets
    lateinit var progRecyclerView: RecyclerView

    //News section widgets
    lateinit var newsRecyclerView: RecyclerView


    //Bottom Toolbar
    lateinit var bottomBarView: com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)


        //Init TextViews, etc
        var counterTexts = initWidgets()


        initCountDown(counterTexts)


        initProgrammingCards()

        initNewsRecycler()

        initBottomBar()




    }

    private fun initWidgets(): Array<TextView>{

        //Countdown
        txtDies=findViewById(R.id.countdown_dies)
        txtHores=findViewById(R.id.countdown_hores)
        txtMin=findViewById(R.id.countdown_min)
        txtSeg=findViewById(R.id.countdown_seg)


        //Programming cards
        progRecyclerView= findViewById(R.id.rv)

        //News
        newsRecyclerView=findViewById(R.id.news_recycler)

        //BottomBar
        bottomBarView = findViewById(R.id.bottom_navigation)


        return arrayOf(txtDies, txtHores, txtMin, txtSeg)
    }

    private fun initProgrammingCards(){

        //data Test
        val events = ArrayList<ProgrammingModel>()
        events.add(ProgrammingModel("TOT EL DIA","Museus Oberts"))
        events.add(ProgrammingModel("10:30","Concert"))
        events.add(ProgrammingModel("Avui","Dinar"))
        events.add(ProgrammingModel("Avui","Dinar"))


        //Setting up the adapter and the layout manager for the recycler view
        progRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
        val adapter = ProgrammingAdapter(events)
        progRecyclerView.adapter = adapter
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


    private fun initNewsRecycler() {
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

    private fun initBottomBar(){

        bottomBarView.enableShiftingMode(false)
        bottomBarView.enableItemShiftingMode(false)
        bottomBarView.setTextSize(9.0f)

    }
}
