package com.obrasocialsjd.magicline.activities.main.fragments

import android.content.Intent
import android.graphics.Rect
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
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
import com.obrasocialsjd.magicline.activities.main.adapters.KmAdapter
import mumayank.com.airlocationlibrary.AirLocation
import org.xmlpull.v1.XmlPullParserException
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.data.kml.KmlPoint
import com.obrasocialsjd.magicline.activities.main.adapters.CardKm
import com.obrasocialsjd.magicline.utils.*
import kotlinx.android.synthetic.main.layout_map_km.*
import kotlinx.android.synthetic.main.toolbar_appbar_top.*
import kotlinx.android.synthetic.main.toolbar_map_top.view.*
import java.io.IOException
import java.io.InputStream


class MapFragment : BaseFragment(), OnMapReadyCallback {

    private lateinit var mapView: View
    private lateinit var map: GoogleMap
    private var airLocation: AirLocation? = null
    private lateinit var kmlLayer: KmlLayer
    private val pathPoints = arrayListOf<LatLng>()
    private lateinit var userLocation: Location

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        airLocation?.onActivityResult(requestCode, resultCode, data) // ADD THIS LINE INSIDE onActivityResult
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        airLocation?.onRequestPermissionsResult(requestCode, permissions, grantResults) // ADD THIS LINE INSIDE onRequestPermissionResult
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mapView = inflater.inflate(R.layout.fragment_map, container, false)
        initToolbar()
        initListeners()
        return mapView
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(topToolbar)
        mapView.mapToolbar.title = getString(R.string.toolbar_map)
    }

    private fun initListeners() {
        mapView.userLocationBtn.setOnClickListener {  }
        mapView.showMarkersBtn.setOnClickListener {  }
    }

    override fun onStart() {

        super.onStart()
        airLocation = AirLocation(requireActivity(), true, true, object: AirLocation.Callbacks {
            override fun onSuccess(location: Location) {
                userLocation = location
                setMarker("", "", location.latitude, location.longitude, user_location_icon)
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 17.0f))
            }

            override fun onFailed(locationFailedEnum: AirLocation.LocationFailedEnum) {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(pathPoints[0], 17.0f))
            }

        })
        val mapFragment: SupportMapFragment? = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {

        map = googleMap
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.style_map))

        //Prepare km cards
        initBcnKmCards()

        //ADD KML & INTEREST POINTS
        addKML()
        kmlLayer  = KmlLayer(map, R.raw.ml_bcn_placemarkers, context)
    }

    private fun addKML() {
        try {
            val arrayKml  = resources.obtainTypedArray(R.array.arrayKml)
            for (i in 0 until arrayKml.length()){
                var kml: InputStream = resources.openRawResource(arrayKml.getResourceId(i, -1))
                val kmlLayer = KmlLayer(map, kml, requireContext())
                kmlLayer.addLayerToMap()
                if (kmlLayer.containers != null){
                    for (container in kmlLayer.containers) {
                        if (container.hasPlacemarks()) {
                            for (placemark in container.placemarks) {
                                val geometry = placemark.geometry
                                if (geometry.geometryType == KML_POINT) {
                                    val point = placemark.geometry as KmlPoint
                                    val latLng = LatLng(point.geometryObject.latitude, point.geometryObject.longitude)
                                    pathPoints.add(latLng)
                                }
                            }
                        }
                    }
                }
                kmlLayer.removeLayerFromMap()
            }
            arrayKml.recycle()

        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun setMarker(title: String, text: String, lat: Double, lon: Double, resourceId: Int) {
        setMarker(title, text, lat, lon, BitmapDescriptorFactory.fromResource(resourceId))
    }

    private fun setMarker(title: String, text: String, lat: Double, lon: Double, icon: BitmapDescriptor) {
        val markerOptions = MarkerOptions().position(LatLng(lat, lon)).title(title)
                .snippet(text).icon(icon)
        map.addMarker(markerOptions)

    }

    //bcn flavor
    private fun initBcnKmCards() {

        recyclerViewMap?.addItemDecoration(MarginItemDecoration(resources.getDimension(R.dimen.margin_km).toInt()))
        val kmListInt = resources.getIntArray(R.array.arrayKm)
        val kmList: ArrayList<CardKm> = arrayListOf()
        for (i in 0 until kmListInt.size) {
            kmList.add(CardKm(kmListInt[i]))
            //Setting up the adapter and the layout manager for the recycler view
        }

        recyclerViewMap?.layoutManager = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
        val adapter = KmAdapter(pathPoints, kmList, map, this.requireContext())
        recyclerViewMap?.adapter = adapter
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
