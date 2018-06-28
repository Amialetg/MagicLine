package com.voluntariat.android.magicline

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout

/**
 * Created by hector on 27/06/18.
 */



class RecyclerTest : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.programming_recycler)

        val recyclerView = findViewById(R.id.rv) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
        val events = ArrayList<ProgrammingModel>()
        events.add(ProgrammingModel("Avui","Dinar"))
        events.add(ProgrammingModel("Avui","Dinar"))
        events.add(ProgrammingModel("Avui","Dinar"))
        events.add(ProgrammingModel("Avui","Dinar"))

        val adapter = ProgrammingAdapter(events)
        recyclerView.adapter = adapter

    }
}
