package com.obrasocialsjd.magicline.activities.main.fragments

import android.content.Intent
import android.content.res.TypedArray
import android.graphics.Rect
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.data.kml.KmlLayer
import com.obrasocialsjd.magicline.R
import com.obrasocialsjd.magicline.R.drawable.user_location_icon
import com.obrasocialsjd.magicline.activities.main.adapters.CardKm
import com.obrasocialsjd.magicline.activities.main.adapters.KmAdapter
import kotlinx.android.synthetic.main.layout_map_km.*
import kotlinx.android.synthetic.main.toolbar_appbar_top.*
import kotlinx.android.synthetic.main.toolbar_map_top.view.*
import mumayank.com.airlocationlibrary.AirLocation
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream
import kotlin.math.absoluteValue
import kotlin.math.sign


class MapFragment : BaseFragment(), OnMapReadyCallback {

    private lateinit var mapView: View
    private lateinit var map: GoogleMap
    private var airLocation: AirLocation? = null
    private var arrayKmlLayers: ArrayList<KmlLayer> = arrayListOf()
    private lateinit var kmlMarkers: KmlLayer
    private var arrayListCoordinates = arrayListOf<LatLng>()
    private lateinit var userLocation: Location
    private var isLocationActive: Boolean = false
    private var areMarkersActive: Boolean = true
    private lateinit var arrayKml: TypedArray
    private lateinit var kmAdapter: KmAdapter

    private var userMarker : Marker? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        airLocation?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        airLocation?.onRequestPermissionsResult(requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mapView = inflater.inflate(R.layout.fragment_map, container, false)
        initToolbar()
        initListeners()
        initCoordinates()
        return mapView

    }

    private fun initToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(topToolbar)
        mapView.mapToolbar.title = getString(R.string.toolbar_map)
    }

    private fun initListeners() {
        mapView.userLocationBtn.setOnClickListener {
            if (isLocationActive){
//                map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(userLocation.latitude, userLocation.longitude), 12.5f))
                hideUserLocation()
            } else {
                showUserLocation()
            }
            changeUserLocation()
        }
        mapView.showMarkersBtn.setOnClickListener {
            //changeMarkers(areMarkersActive)

            if (areMarkersActive) {
                hideInterestMarkers()
            } else {
                showInterestMarkers()
            }
        }
    }

    private fun hideInterestMarkers() {
        // remove all markers
        map.clear()
        // Show route
        addCurrentModalityLayerToMap()
        // TODO Show markers
    }

    private fun showInterestMarkers() {
        // TODO ask Andrea
    }

    private fun addCurrentModalityLayerToMap() {
        kmAdapter.let { map.moveCamera(CameraUpdateFactory.newLatLngZoom(arrayListCoordinates[it.selectedPosition], 12.5f))
            if (!arrayKmlLayers[it.selectedPosition].isLayerOnMap) arrayKmlLayers[it.selectedPosition].addLayerToMap() }

    }

    private fun initCoordinates() {
        val arrayLat: TypedArray = resources.obtainTypedArray(R.array.arrayLatitude)
        val arrayLon: TypedArray = resources.obtainTypedArray(R.array.arrayLongitude)

        for (i in 0 until arrayLat.length()){
            val lat = arrayLat.getFloat(i, Float.MAX_VALUE)
            val lon = arrayLon.getFloat(i, Float.MAX_VALUE)
            val latLon = LatLng(lat.toDouble(), lon.toDouble())
            arrayListCoordinates.add(latLon)
        }

        arrayLat.recycle()
        arrayLon.recycle()
    }

    private fun changeMarkers(active: Boolean) {

        kmlMarkers  = KmlLayer(map, R.raw.ml_placemarkers, requireContext())

        if (active) {
            kmlMarkers.addLayerToMap()
            areMarkersActive = false
        }
        else {
            areMarkersActive = true
            map.clear()
            //initKmCards()
        }
        tintMarkerButton()
    }

    private fun changeUserLocation() {
        isLocationActive = !isLocationActive
        tintUserLocationButton()
    }

    private fun tintUserLocationButton() {
        if (isLocationActive){
            mapView.userLocationBtn.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.colorPrimary)

        }else{
            mapView.userLocationBtn.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.grey)
        }
    }

    private fun tintMarkerButton() {
        if (!areMarkersActive){
            mapView.showMarkersBtn.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.colorPrimary)

        }else{
            mapView.showMarkersBtn.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.grey)
        }
    }

    override fun onStart() {

        super.onStart()
        airLocation = AirLocation(requireActivity(), true, true, object: AirLocation.Callbacks {
            override fun onSuccess(location: Location) {
                isLocationActive = true
                tintUserLocationButton()
                userLocation = location
                showUserLocation()
            }

            override fun onFailed(locationFailedEnum: AirLocation.LocationFailedEnum) {
                isLocationActive = false
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(arrayListCoordinates[0], 17.0f))
                tintUserLocationButton()
            }

        })
        val mapFragment: SupportMapFragment? = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    private fun showUserLocation() {
        userLocation.let { location ->
            userMarker = setMarker("", "", location.latitude, location.longitude, user_location_icon)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 17.0f))
        }
    }

    private fun hideUserLocation() {
        userMarker?.remove()
        userMarker = null
        kmAdapter.let {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(arrayListCoordinates[it.selectedPosition], 12.5f))
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {

        map = googleMap
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.style_map))

        initKmCards()

        //ADD KML & PLACEMARKERS (INTEREST POINTS)
        addKML()
        changeMarkers(true)
    }

    private fun addKML() {
        try {
            for (i in 0 until arrayKml.length()){
                val kml: InputStream = resources.openRawResource(arrayKml.getResourceId(i, -1))
                val kmlLayer = KmlLayer(map, kml, requireContext())
                arrayKmlLayers.add(kmlLayer)
            }
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun setMarker(title: String, text: String, lat: Double, lon: Double, resourceId: Int): Marker? {
        return setMarker(title, text, lat, lon, BitmapDescriptorFactory.fromResource(resourceId))
    }

    private fun setMarker(title: String, text: String, lat: Double, lon: Double, icon: BitmapDescriptor): Marker? {
        val markerOptions = MarkerOptions().position(LatLng(lat, lon)).title(title)
                .snippet(text).icon(icon)
        return map.addMarker(markerOptions)
    }

    private fun initKmCards() {
        arrayKml  = resources.obtainTypedArray(R.array.arrayKml)
        recyclerViewMap?.addItemDecoration(MarginItemDecoration(resources.getDimension(R.dimen.margin_km).toInt()))
        val kmListInt = resources.getIntArray(R.array.arrayKm)
        val kmList: ArrayList<CardKm> = arrayListOf()
        for (i in 0 until kmListInt.size) {
            //to diff st boi, we use negative sign on xml file (bcn flavor)
            if(kmListInt[i].sign < 0 ) kmList.add(CardKm(kmListInt[i].absoluteValue, getString(R.string.st_boi)))
            else kmList.add(CardKm(kmListInt[i]))
        }

        recyclerViewMap?.layoutManager = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)

        kmAdapter = KmAdapter(arrayKmlLayers, arrayListCoordinates, kmList, map, this.requireContext())
        recyclerViewMap?.adapter = kmAdapter
    }

    class MarginItemDecoration(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View,
                                    parent: RecyclerView, state: RecyclerView.State) {
            with(outRect) {
                when {
                    parent.getChildAdapterPosition(view) == 4 -> {
                        right = spaceHeight*2
                        left = spaceHeight
                    }
                    parent.getChildAdapterPosition(view) == 0 -> {
                        left = spaceHeight*2
                        right = spaceHeight
                    }
                    else -> {
                        left =  spaceHeight
                        right = spaceHeight
                    }
                }
            }
        }
    }
}
