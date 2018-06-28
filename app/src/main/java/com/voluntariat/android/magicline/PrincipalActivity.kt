package com.voluntariat.android.magicline

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class PrincipalActivity : AppCompatActivity() {

    private val dateCursaString: String = "24.02.2019, 10:00"

    lateinit var txtDies: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        txtDies=findViewById(R.id.countdown_dies)

        initCountDown()



    }

    private fun setTextCountDown(dades: Array<Long>){
        val txtDies = findViewById<TextView>(R.id.countdown_dies)
        val txtHores = findViewById<TextView>(R.id.countdown_hores)
        val txtMin = findViewById<TextView>(R.id.countdown_min)
        val txtSeg = findViewById<TextView>(R.id.countdown_seg)

        txtDies.setText(dades[0].toString())
        txtHores.setText(dades[1].toString())
        txtMin.setText(dades[2].toString())
        txtSeg.setText(dades[3].toString())


    }

    class MyCount(millis:Long, interval:Long): CountDownTimer(millis, interval){


        override fun onFinish() {
            //Missatge del dia de la cursa
        }

        override fun onTick(millisToFinish: Long){

            var dies: Long = TimeUnit.MILLISECONDS.toDays(millisToFinish)
            var hores: Long = TimeUnit.MILLISECONDS.toHours(millisToFinish)
            var min: Long = TimeUnit.MILLISECONDS.toMinutes(millisToFinish)
            var seg: Long = TimeUnit.MILLISECONDS.toSeconds(millisToFinish)


            Log.d("PrincipalActivity", "dies $seg, hores $hores, min $min, seg $seg")
        }
    }

    public fun initCountDown(){
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

        //Definim l'array on guardarem els dies, hores, min i seg
        var dades = Array<Long>(4, {_->0})

        Log.d("PrincipalActivity", diff.toString())
        MyCount(diff, 1000).start()

        setTextCountDown(dades)

    }
}
