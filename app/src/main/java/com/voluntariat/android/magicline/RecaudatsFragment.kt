package com.voluntariat.android.magicline

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class RecaudatsFragment: Fragment() {

    private lateinit var recaudatsText:TextView
    private var recaudats:Int=0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater?.inflate(R.layout.fragment_recaudats, container, false)!!

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWidgets()
        recaudats = getRecaudats()
        setRecaudats()
    }

    private fun initWidgets(){
        recaudatsText=view!!.findViewById(R.id.recaudats_num)
    }

    private fun getRecaudats():Int{
        //Internet implementation.....
        return 274543
    }
    private fun setRecaudats(){
        var recaudatsString=String.format("%,d", recaudats)
        recaudatsString+="€"
        recaudatsText.text = recaudatsString
    }


}