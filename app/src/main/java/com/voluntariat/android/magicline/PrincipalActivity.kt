package com.voluntariat.android.magicline

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout

class PrincipalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        val recyclerView = findViewById(R.id.rv) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
        val events = ArrayList<ProgrammingModel>()
        events.add(ProgrammingModel("TOT EL DIA","Museus Oberts"))
        events.add(ProgrammingModel("10:30","Concert"))
        events.add(ProgrammingModel("Avui","Dinar"))
        events.add(ProgrammingModel("Avui","Dinar"))

        val adapter = ProgrammingAdapter(events)
        recyclerView.adapter = adapter
    }
}
