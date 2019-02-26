package com.obrasocialsjd.magicline.activities.main.fragments

import android.Manifest
import android.annotation.SuppressLint
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
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.maps.android.data.kml.KmlLayer
import com.obrasocialsjd.magicline.R
import com.obrasocialsjd.magicline.R.drawable.user_location_icon
import com.obrasocialsjd.magicline.activities.main.adapters.CardKm
import com.obrasocialsjd.magicline.activities.main.adapters.KmAdapter
import com.obrasocialsjd.magicline.utils.*
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
    private var areMarkersActive: Boolean = true
    private lateinit var arrayKml: TypedArray
    private lateinit var kmAdapter: KmAdapter

    private var mapInitialized = false

    private var userMarker : Marker? = null

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener
    private var firstTime = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationManager = activity?.getSystemService(LOCATION_SERVICE) as LocationManager
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mapView = inflater.inflate(R.layout.fragment_map, container, false)

        initToolbar()
        initCoordinates()

        return mapView
    }

    override fun onStart() {
        super.onStart()

        tintUserLocationMarkers()

        context?.let {
            isLocationActive = getUserLocationPreferences(it)
        }

        initListeners()

        val mapFragment: SupportMapFragment? = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        if (mapInitialized) {
            onStartUserLocation()
        }
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(topToolbar)
        mapView.mapToolbar.title = getString(R.string.toolbarMap)
    }

    private fun initListeners() {
        if( GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this.context) != 9 ) {
            mapView.userLocationBtn.setOnClickListener {switchUserLocation()}
            mapView.showMarkersBtn.setOnClickListener {
                switchInterestMarkers()
                firstTime = false
            }
        } else {
            mapView.userLocationBtn.isEnabled = false
            mapView.showMarkersBtn.isEnabled = false
            areMarkersActive = false
            tintUserLocationMarkers()
            mapView.userLocationBtn.alpha = 0.5f
            mapView.showMarkersBtn.alpha = 0.5f
        }

        locationListener  = object : LocationListener {
            override fun onLocationChanged(location: Location?) {
                userLocation = location
                userLocation.let {
                    if (isLocationActive) showUserLocation()
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

    private fun initializePositionListener() {
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
        } catch (securityException : SecurityException) { }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        if (!mapInitialized) {
            map = googleMap
            map.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.style_map))

            initKmCards()
            addKML()

            addMapElements()

            onStartUserLocation()
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
        initInterestPlaces()
        setInterestMarkersVisibility(true)

        addCurrentModalityLayerToMap()
    }

    private fun switchUserLocation() {
        if (mapInitialized) {
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
                activity?.notAvailableDialog()
            }
        }
    }

    private fun initLocationClient() {
        initFusedLocationClient()
        checkPermissions()
    }

    private fun switchUserLocationButton() {
        isLocationActive = !isLocationActive
        tintUserLocationMarkers()
    }

    private fun tintUserLocationMarkers() {
        if (isLocationActive && !firstTime){
            changeButtonColor(MAP_BUTTON_SELECTED, mapView.userLocationBtn)
        }
        else {
            changeButtonColor(MAP_BUTTON_UNSELECTED, mapView.userLocationBtn)
        }
        if (areMarkersActive){
            changeButtonColor(MAP_BUTTON_SELECTED, mapView.showMarkersBtn)
        }
        else {
            changeButtonColor(MAP_BUTTON_UNSELECTED, mapView.showMarkersBtn)
        }
    }

    @SuppressLint("RestrictedApi")
    private fun changeButtonColor(colorRes : Int, mapViewObject: AppCompatImageButton) {
        context?.let { context ->
            mapViewObject.supportBackgroundTintList = ContextCompat.getColorStateList(context, colorRes)

        }
    }

    private fun showUserLocation(moveCamera: Boolean = false) {
        if (mapInitialized) {
            tintUserLocationMarkers()
            if (userMarker != null) userMarker?.remove()
            userLocation?.let { location ->
                userMarker = setMarker("", "", location.latitude, location.longitude, user_location_icon)
                if (moveCamera) map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 17.0f))
            }
        }
    }

    private fun hideUserLocation() {
        userMarker?.remove()
        userMarker = null
        kmAdapter.let {
            ZoomDependingLocation(it)
        }
    }

    private fun isUserMarkerVisible() : Boolean {
        userMarker?.let { userMarker ->
            return userMarker.isVisible
        }

        return false
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
            tintUserLocationMarkers()
        }
    }

    private fun switchInterestMarkers() {
        setInterestMarkersVisibility()
    }

    private fun initInterestPlaces() {
        kmlMarkers  = KmlLayer(map, R.raw.ml_placemarkers, requireContext())
    }

    private fun addCurrentModalityLayerToMap() {
        try {
            kmAdapter.let {
                ZoomDependingLocation(it)
                if (!arrayKmlLayers[it.selectedPosition].isLayerOnMap) arrayKmlLayers[it.selectedPosition].addLayerToMap()
            }
        } catch (multitouch: IndexOutOfBoundsException) { }
    }

    private fun ZoomDependingLocation(it: KmAdapter){
        val lat: TypedArray = resources.obtainTypedArray(R.array.arrayLatitude)
        val lon: TypedArray = resources.obtainTypedArray(R.array.arrayLongitude)
        if(getFlavor() == BARCELONA) {
            val isStBoi = LatLng(lat.getFloat(INDEX_STBOI, Float.MAX_VALUE).toDouble(), lon.getFloat(INDEX_STBOI, Float.MAX_VALUE).toDouble())
            val is40km = LatLng(lat.getFloat(INDEX_40KM, Float.MAX_VALUE).toDouble(), lon.getFloat(INDEX_40KM, Float.MAX_VALUE).toDouble())
            val is10km = LatLng(lat.getFloat(INDEX_10KM, Float.MAX_VALUE).toDouble(), lon.getFloat(INDEX_10KM, Float.MAX_VALUE).toDouble())

            val array = arrayListCoordinates[it.selectedPosition]
            when(array){
                isStBoi, is40km -> map.moveCamera(CameraUpdateFactory.newLatLngZoom(arrayListCoordinates[it.selectedPosition], 11.5f))
                is10km -> map.moveCamera(CameraUpdateFactory.newLatLngZoom(arrayListCoordinates[it.selectedPosition], 14.0f))
                else -> map.moveCamera(CameraUpdateFactory.newLatLngZoom(arrayListCoordinates[it.selectedPosition], 12.5f))
            }
        }
        else {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(arrayListCoordinates[it.selectedPosition], 12.5f))
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
        } catch (multitouch : IndexOutOfBoundsException) { }
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
                        right = spaceHeight
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (grantResults[0]) {
            PackageManager.PERMISSION_GRANTED -> {
                isLocationActive = true
                onPermissionGranted()
                initializePositionListener()
            }
            PackageManager.PERMISSION_DENIED -> onPermissionNotGranted()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun requestPermissions() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
    }

    private fun getUserLocation() {
        try {
            initFusedLocationClient()
            if (isPermissionGranted()) {
                fusedLocationClient.lastLocation
                        .addOnSuccessListener { location: Location? ->
                            if (location != null) {
                                userLocation = location

                                map.let {
                                    if (isLocationActive) showUserLocation(true)
                                }
                            } else {
                                map.let { map.moveCamera(CameraUpdateFactory.newLatLngZoom(arrayListCoordinates[0], 17.0f)) }
                            }
                        }
            }
        } catch (exception : SecurityException) {}

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
            if (isLocationActive) showUserLocation(true)
        }

    }

    private fun onPermissionNotGranted() {
        isLocationActive = false
        tintUserLocationMarkers()
    }

    private fun isGPSAvailable() : Boolean {
        locationManager = context?.getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun initFusedLocationClient() {
        context?.let {context ->
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        }
    }

    private fun onStartUserLocation() {
        if (isPermissionGranted()) {
            if (isGPSAvailable()) {
                initLocationClient()
            } else {
                isLocationActive = false
                tintUserLocationMarkers()
            }
        } else {
            requestPermissions()
        }
    }

    override fun onStop() {
        super.onStop()

        context?.let { updateUserLocationPreferences(it, isLocationActive) }
    }

}
