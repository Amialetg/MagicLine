package com.voluntariat.android.magicline.activities.main.fragments

import android.content.Intent
import android.graphics.Bitmap
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
import com.voluntariat.android.magicline.R.drawable.user_location
import com.voluntariat.android.magicline.activities.main.adapters.KmAdapter
import mumayank.com.airlocationlibrary.AirLocation
import java.util.ArrayList
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.location.Location
import com.google.android.gms.maps.model.BitmapDescriptor
import com.voluntariat.android.magicline.R.drawable.user_location_icon


class MapFragment : BaseFragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private var airLocation: AirLocation? = null // ADD THIS LINE ON TOP
    private lateinit var kmlLayer: KmlLayer

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        airLocation?.onActivityResult(requestCode, resultCode, data) // ADD THIS LINE INSIDE onActivityResult
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        airLocation?.onRequestPermissionsResult(requestCode, permissions, grantResults) // ADD THIS LINE INSIDE onRequestPermissionResult
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var mapView: View = inflater.inflate(R.layout.fragment_map, container, false)
        kmlLayer  = KmlLayer(map, R.raw.ml_bcn_placemarkers, context)
        return mapView

    }

    override fun onStart() {

        super.onStart()
        airLocation = AirLocation(requireActivity(), true, true, object: AirLocation.Callbacks {
            override fun onSuccess(location: Location) {
                setMarker("", "", location.latitude, location.longitude, user_location_icon)
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 17.0f))
            }

            override fun onFailed(locationFailedEnum: AirLocation.LocationFailedEnum) {
                // couldn't fetch location due to reason available in locationFailedEnum
                // you may optionally do something to inform the user, even though the reason may be obvious
            }

        })
        val mapFragment: SupportMapFragment? = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {

        //SETUP
        map = googleMap
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.style_map))
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(41.390205, 2.154007), 12.0f))

        //wait for map to load
        initKmCards()

        //INTEREST POINTS
        loadMarkers()
        kmlLayer.addLayerToMap()

    }

    private fun setMarker(title: String, text: String, lat: Double, lon: Double, resourceId: Int) {
        setMarker(title, text, lat, lon, BitmapDescriptorFactory.fromResource(resourceId))
    }

    private fun setMarker(title: String, text: String, lat: Double, lon: Double, icon: BitmapDescriptor) {
        val markerOptions = MarkerOptions().position(LatLng(lat, lon)).title(title)
                .snippet(text).icon(icon)
        map.addMarker(markerOptions)

    }

    private fun getMarkerIconFromDrawable(drawable: Drawable): BitmapDescriptor {
        val canvas = Canvas()
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        canvas.setBitmap(bitmap)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
    private fun initKmCards() {

        val kmRecyclerView = view?.findViewById<RecyclerView>(R.id.rv_map)
        kmRecyclerView?.addItemDecoration(MarginItemDecoration(resources.getDimension(R.dimen.margin_km).toInt()))
        val kmList = ArrayList<Int>()

        kmList.add(10)
        kmList.add(15)
        kmList.add(20)
        kmList.add(30)
        kmList.add(30)


        //Setting up the adapter and the layout manager for the recycler view
        kmRecyclerView?.layoutManager = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
        val adapter = KmAdapter(kmList, map, this.requireContext())
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
