package com.voluntariat.android.magicline.activities.main.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.data.kml.KmlLayer
import com.voluntariat.android.magicline.R
import com.voluntariat.android.magicline.activities.main.adapters.kmAdapter
import java.util.ArrayList


class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var kmlLayer : KmlLayer


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_map, container, false)

    }

    override fun onStart() {

        super.onStart()
        val mapFragment: SupportMapFragment? = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {

        //SETUP
        mMap = googleMap
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.style_map))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(41.390205, 2.154007), 12.0f))

        //wait for map to load
        initKmCards()

        //ROUTES

    }

    private fun initKmCards() {

        val kmRecyclerView = view?.findViewById<RecyclerView>(R.id.rv_map)
        val kmList = ArrayList<Int>()

        kmList.add(10)
        kmList.add(15)
        kmList.add(20)
        kmList.add(25)
        kmList.add(30)


        //Setting up the adapter and the layout manager for the recycler view
        kmRecyclerView?.layoutManager = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
        val adapter = kmAdapter(kmList,mMap,context)
        kmRecyclerView?.adapter = adapter
    }
}
