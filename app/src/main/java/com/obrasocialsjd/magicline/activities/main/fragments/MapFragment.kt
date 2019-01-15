package com.obrasocialsjd.magicline.activities.main.fragments

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.TypedArray
import android.graphics.Rect
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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
import com.obrasocialsjd.magicline.utils.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
import com.obrasocialsjd.magicline.utils.funNotAvailableDialog
import com.obrasocialsjd.magicline.utils.gpsNotAvailableDialog
import kotlinx.android.synthetic.main.layout_map_km.*
import kotlinx.android.synthetic.main.toolbar_appbar_top.*
import kotlinx.android.synthetic.main.toolbar_map_top.view.*
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream
import kotlin.math.absoluteValue
import kotlin.math.sign


class MapFragment : BaseFragment(), OnMapReadyCallback {

    private lateinit var mapView: View
    private lateinit var map: GoogleMap
    private var arrayKmlLayers: ArrayList<KmlLayer> = arrayListOf()
    private lateinit var kmlMarkers: KmlLayer
    private var arrayListCoordinates = arrayListOf<LatLng>()
    private var userLocation: Location? = null
    private var isLocationActive: Boolean = false
    private var areMarkersActive: Boolean = false
    private lateinit var arrayKml: TypedArray
    private lateinit var kmAdapter: KmAdapter

    private var mapInitialized = false

    private var userMarker : Marker? = null

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationManager = activity?.getSystemService(LOCATION_SERVICE) as LocationManager
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mapView = inflater.inflate(R.layout.fragment_map, container, false)

        initToolbar()
        initCoordinates()

        tintUserLocationButton()
        tintMarkersButton()

        return mapView
    }

    override fun onStart() {
        super.onStart()

        initListeners()

        if (isGPSAvailable()) {
            initLocationClient()
        } else {
            isLocationActive = false
            tintUserLocationButton()
        }

        val mapFragment: SupportMapFragment? = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(topToolbar)
        mapView.mapToolbar.title = getString(R.string.toolbar_map)
    }



    private fun initListeners() {
        mapView.userLocationBtn.setOnClickListener {switchUserLocation()}
        mapView.showMarkersBtn.setOnClickListener {switchInterestMarkers()}

        locationListener  = object : LocationListener {
            override fun onLocationChanged(location: Location?) {
                userLocation = location
                userLocation.let {
                    updateUserLocation()
                }
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

            override fun onProviderEnabled(provider: String?) {}

            override fun onProviderDisabled(provider: String?) {}
        }
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

        var itemClickListener: (Int) -> Unit = { adapterPosition ->
            with(kmAdapter) {
                selectedPosition = adapterPosition
                addCurrentModalityLayerToMap()
                removeOtherModalityLayerForMap()
                notifyDataSetChanged()
            }
        }

        kmAdapter = KmAdapter(kmList, itemClickListener , this.requireContext() )
        recyclerViewMap?.adapter = kmAdapter
    }

    override fun onMapReady(googleMap: GoogleMap) {
        if (!mapInitialized) {
            map = googleMap
            map.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.style_map))

            initKmCards()
            addKML()

            addMapElements()
            mapInitialized = true
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

    private fun addMapElements() {
        // TODO: Uncomment when kml markers are updated to 2019
        //initInterestPlaces()
        //setInterestMarkersVisibility(true)

        addCurrentModalityLayerToMap()
    }

    private fun switchUserLocation() {
        if (isGPSAvailable()) {
            if (isPermissionGranted()) {
                if (isLocationActive) {
                    hideUserLocation()
                } else {
                    getUserLocation()
                }
                switchUserLocationButton()
            } else {
                requestPermissions()
            }
        } else {
            activity?.gpsNotAvailableDialog()
        }
    }

    private fun initLocationClient() {
        context?.let {context ->
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        }

        checkPermissions()
    }

    private fun switchUserLocationButton() {
        isLocationActive = !isLocationActive
        tintUserLocationButton()
    }

    private fun tintUserLocationButton() {
        // TODO Compat
        if (isLocationActive){
            mapView.userLocationBtn.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.colorPrimary)
        }else{
            mapView.userLocationBtn.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.grey)
        }
    }

    private fun showUserLocation() {
        if (mapInitialized) {
            if (userMarker != null) userMarker?.remove()
            userLocation?.let { location ->
                userMarker = setMarker("", "", location.latitude, location.longitude, user_location_icon)
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 17.0f))
            }
        }
    }

    private fun updateUserLocation() {
        if (mapInitialized) {
            if (userMarker != null) userMarker?.remove()
            userLocation?.let { location ->
                userMarker = setMarker("", "", location.latitude, location.longitude, user_location_icon)
            }
        }
    }

    private fun hideUserLocation() {
        userMarker?.remove()
        userMarker = null
        kmAdapter.let {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(arrayListCoordinates[it.selectedPosition], 12.5f))
        }

    }

    private fun setInterestMarkersVisibility(forceShow : Boolean = false) {
        if (forceShow) {
            kmlMarkers.addLayerToMap()
        } else {
            areMarkersActive = if (!areMarkersActive) {
                kmlMarkers.addLayerToMap()
                true
            } else {
                kmlMarkers.removeLayerFromMap()
                // adds the current route (deleted with map.clear)
                false
            }
            tintMarkersButton()
        }
    }

    private fun switchInterestMarkers() {
        activity?.funNotAvailableDialog()
        // TODO: Uncomment when kml markers are updated to 2019
        //setInterestMarkersVisibility()
    }

    private fun initInterestPlaces() {
        kmlMarkers  = KmlLayer(map, R.raw.ml_placemarkers, requireContext())
    }

    private fun tintMarkersButton() {
        // TODO compat
        if (areMarkersActive){
            mapView.showMarkersBtn.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.colorPrimary)
        }else{
            mapView.showMarkersBtn.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.grey)
        }
    }

    private fun addCurrentModalityLayerToMap() {
        try {
            kmAdapter.let {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(arrayListCoordinates[it.selectedPosition], 12.5f))
                if (!arrayKmlLayers[it.selectedPosition].isLayerOnMap) arrayKmlLayers[it.selectedPosition].addLayerToMap()
            }
        } catch (multitouch: IndexOutOfBoundsException) {
            // FIXME
        }
    }

    private fun removeOtherModalityLayerForMap() {
        try {
            kmAdapter.let {
                var selectedKmLayer = arrayKmlLayers[it.selectedPosition]
                for (kmLayer in arrayKmlLayers) {
                    if (kmLayer.isLayerOnMap && kmLayer != selectedKmLayer) {
                        kmLayer.removeLayerFromMap()
                    }
                }
            }
        } catch (multitouch : IndexOutOfBoundsException) {
            // FIXME
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (grantResults[0]) {
            PackageManager.PERMISSION_GRANTED -> {
                onPermissionGranted()
                initializePositionListener()
            }
            PackageManager.PERMISSION_DENIED -> {
                onPermissionNotGranted()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun requestPermissions() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
    }


    private fun getUserLocation() {
        try {
            fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        if (location != null) {
                            userLocation = location

                            map.let {
                                showUserLocation()
                                isLocationActive = true
                                tintUserLocationButton()
                            }
                        } else {
                            map.let {
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(arrayListCoordinates[0], 17.0f))
                            }
                        }
                    }
        } catch (exception : SecurityException) {
            // TODO do nothing
        }

    }

    private fun checkPermissions() {
        context?.let { context ->
            var permissionCheck = checkSelfPermission(context,
                    Manifest.permission.ACCESS_FINE_LOCATION)

            when (permissionCheck) {
                PackageManager.PERMISSION_GRANTED -> {
                    onPermissionGranted()
                }
                PackageManager.PERMISSION_DENIED -> {
                    onPermissionNotGranted()
                    requestPermissions()
                }
            }
        }
    }

    private fun isPermissionGranted() : Boolean {
        var permissionCheck = context?.let {  checkSelfPermission(it,
                    Manifest.permission.ACCESS_FINE_LOCATION)}

        return permissionCheck?.equals(PackageManager.PERMISSION_GRANTED) ?: false
    }

    private fun onPermissionGranted() {
        initializePositionListener()
        if (userLocation != null) {
            showUserLocation()
            isLocationActive = true
            tintUserLocationButton()
        }
    }

    private fun onPermissionNotGranted() {
        isLocationActive = false
        tintUserLocationButton()
    }

    private fun isGPSAvailable() : Boolean {
        locationManager = activity?.getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun initializePositionListener() {
        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, locationListener)
        } catch (securityException : SecurityException) {

        }

    }
}
