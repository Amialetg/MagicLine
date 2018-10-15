package com.voluntariat.android.magicline.activities.main.fragments

import android.graphics.Rect
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
import com.google.android.gms.maps.model.*
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

        //INTEREST POINTS
        loadMarkers()

    }

    private fun setMarker(title:String,text:String,lat:Double,lon:Double,resourceId:Int){
        val markerOptions = MarkerOptions().position(LatLng(lat,lon)).title(title)
                .snippet(text).icon(BitmapDescriptorFactory.fromResource(resourceId))
        mMap.addMarker(markerOptions)

    }

    private fun initKmCards() {

        val kmRecyclerView = view?.findViewById<RecyclerView>(R.id.rv_map)
        kmRecyclerView?.addItemDecoration(MarginItemDecoration(resources.getDimension(R.dimen.margin_km).toInt()))
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

    private fun loadMarkers(){
        //TODO("waiting for official info to fill")
        setMarker("Font", "", 41.41649, 2.15293, R.drawable.group_2)
        setMarker("Font", "", 41.42317, 2.12254, R.drawable.group_3)
        setMarker("Font", "", 41.41949, 2.16376, R.drawable.group_2)
        setMarker("Font", "", 41.37281, 2.15025, R.drawable.group_4)
        setMarker("Font", "", 41.36102, 2.16168, R.drawable.group_2)
        setMarker("Font", "", 41.38462, 2.12367, R.drawable.group_3)
        setMarker("Font", "", 41.38219, 2.12768, R.drawable.group_4)
    }

    class MarginItemDecoration(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View,
                                    parent: RecyclerView, state: RecyclerView.State) {
            with(outRect) {
                if(parent.getChildAdapterPosition(view) == 4){
                    right = spaceHeight*2
                    left = spaceHeight
                }
                else if(parent.getChildAdapterPosition(view) == 0){
                    left = spaceHeight*2
                    right = spaceHeight
                }
                else{
                left =  spaceHeight
                right = spaceHeight
                }
            }
        }
    }
}
