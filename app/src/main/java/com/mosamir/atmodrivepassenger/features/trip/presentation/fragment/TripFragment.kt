package com.mosamir.atmodrivepassenger.features.trip.presentation.fragment

import android.Manifest
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Geocoder
import android.os.Bundle
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mosamir.atmodrivepassenger.R
import com.mosamir.atmodrivepassenger.databinding.FragmentTripBinding
import com.mosamir.atmodrivepassenger.features.trip.presentation.common.SharedViewModel
import com.mosamir.atmodrivepassenger.util.AnimationUtils
import com.mosamir.atmodrivepassenger.util.LocationHelper
import com.mosamir.atmodrivepassenger.util.MapUtils
import com.mosamir.atmodrivepassenger.util.showToast
import com.mosamir.atmodrivepassenger.util.visibilityGone
import com.mosamir.atmodrivepassenger.util.visibilityVisible
import java.io.IOException
import java.util.Locale

class TripFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentTripBinding? = null
    private val binding get() = _binding!!
    private lateinit var mNavController: NavController

    private lateinit var mMap: GoogleMap
    var mLocationRequest: LocationRequest?= null
    var mLocationCallback: LocationCallback?= null
    var mFusedLocationClient: FusedLocationProviderClient?= null
    private var bottomSheet = BottomSheetBehavior<ConstraintLayout>()

    private var mBackPressed: Long = 0
    private var movingCabMarker : Marker?= null
    private var previousLatLng: LatLng? = null
    private var currentLatLng: LatLng? = null
    private var valueAnimator: ValueAnimator? = null
    private var valueEventListener : ValueEventListener?= null

    private lateinit var database: DatabaseReference

    var address = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNavController = findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTripBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = Firebase.database.reference

        binding.locationCard.setOnClickListener {
            binding.locationCard.visibilityGone()
            disPlayBottomSheet()
        }

        binding.tvCancelFindCaptain.setOnClickListener {
            binding.layoutFindLocation.visibilityGone()
        }

        val model = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        binding.btnConfirmLocation.setOnClickListener {
            model.setLocation(address)
            binding.chooseLocationFromMaps.visibilityGone()
            bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
        }

        binding.btnCancelLocation.setOnClickListener {
            binding.chooseLocationFromMaps.visibilityGone()
            bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
        }

        model.locType.observe(viewLifecycleOwner, Observer {

            if (it == "pickupLoc"){
                binding.chooseLocationFromMaps.visibilityVisible()
                bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
            }else if(it == "dropLoc"){
                binding.chooseLocationFromMaps.visibilityVisible()
                bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
            }

        })

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        initLocation()

    }


    private fun disPlayBottomSheet(){
        val bottomSheetView = view?.findViewById<ConstraintLayout>(R.id.bottom_sheet_trip)
        bottomSheet = BottomSheetBehavior.from(bottomSheetView!!)
        bottomSheet.isDraggable = true
        bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetHeight()
    }

    private fun bottomSheetHeight(){
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val screenHeight = displayMetrics.heightPixels
        val desiredHeight = (screenHeight * 0.3).toInt()
        bottomSheet.peekHeight = desiredHeight
    }

    private fun setMapDarkStyle(map: GoogleMap) {
        try {
            // Customize the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success = map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style_dark)
            )
            if (!success) {
                Log.e("TAG", "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e("TAG", "Can't find style. Error: ", e)
        }
    }

    private fun initLocation(){
        if (mFusedLocationClient == null){
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        }

        if (mLocationRequest == null){
            mLocationRequest = LocationRequest.create()
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .setInterval(2000)
                .setFastestInterval(2000)
                .setSmallestDisplacement(5f)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation(){

        mMap.isMyLocationEnabled = true
        mLocationCallback = object : LocationCallback(){
            override fun onLocationResult(result: LocationResult) {
                super.onLocationResult(result)

                val latLng = LatLng(result.lastLocation!!.latitude,result.lastLocation!!.longitude)
                moveCameraMap(latLng)
                val address = getAddressFromLatLng(latLng)
                binding.tvYourLocation.text = address

            }
        }

        mFusedLocationClient?.requestLocationUpdates(mLocationRequest!!,mLocationCallback!!, Looper.getMainLooper())

    }

    private fun getAddressFromLatLng(latLng: LatLng): String {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (addresses?.isNotEmpty()!!) {
                val address = addresses[0]
                // You can format the address as per your requirements
                return address.getAddressLine(0)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            showToast("Error getting address")
        }
        return "Address not found"
    }

    private fun moveCameraMap(latLng: LatLng){
        val cameraPosition = CameraPosition.builder().target(latLng).zoom(15f).build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    private fun locationChecker(){

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(mLocationRequest!!)

        val result : Task<LocationSettingsResponse> = LocationServices.getSettingsClient(requireContext())
            .checkLocationSettings(builder.build())

        result.addOnCompleteListener {task ->

            try {
                task.getResult(ApiException::class.java)
                getLocation()
            }catch (exception : ApiException){
                when (exception.statusCode){

                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->{
                        try {
                            val resolve = exception as ResolvableApiException
                            startIntentSenderForResult(resolve.resolution.intentSender,
                                Priority.PRIORITY_HIGH_ACCURACY,null,0,0,0,null)
                        }catch (ex:Exception){}
                    }

                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE ->{
                        showToast("GPS Not available")
                    }

                }
            }

        }

    }

    private fun addCarMarkerAndGet(latLng: LatLng): Marker {
        val bitmapDescriptor =
            BitmapDescriptorFactory.fromBitmap(MapUtils.getCarBitmap(requireContext()))
        return mMap.addMarker(
            MarkerOptions().position(latLng).flat(true).icon(bitmapDescriptor)
        )!!
    }

    private fun updateCarLocation(latLng: LatLng) {
        val a = System.currentTimeMillis() - mBackPressed
        if (a < 2600) {
            return
        }
        mBackPressed = System.currentTimeMillis()
        if (movingCabMarker == null) {
            movingCabMarker = addCarMarkerAndGet(latLng)
        }
        if (previousLatLng == null) {
            currentLatLng = latLng
            previousLatLng = currentLatLng
            movingCabMarker?.position = currentLatLng!!
            movingCabMarker?.setAnchor(0.5f, 0.5f)
            animateCameraInTrip(currentLatLng!!, previousLatLng!!)
        } else {
            // animateCameraInTrip(currentLatLng!!, previousLatLng!!)
            previousLatLng = currentLatLng

            animateCameraInTrip(latLng, previousLatLng!!)
            valueAnimator = AnimationUtils.carAnimator()

            valueAnimator?.addUpdateListener { va ->
                currentLatLng = latLng
                if (currentLatLng != null && previousLatLng != null) {

                    val multiplier = va.animatedFraction
                    val nextLocation = LatLng(
                        multiplier * currentLatLng!!.latitude + (1 - multiplier) * previousLatLng!!.latitude,
                        multiplier * currentLatLng!!.longitude + (1 - multiplier) * previousLatLng!!.longitude
                    )
                    movingCabMarker?.position = nextLocation
                    val rotation = MapUtils.getRotation(previousLatLng!!, nextLocation)
                    if (!rotation.isNaN()) {
                        movingCabMarker?.rotation = rotation
                    }
                    movingCabMarker?.setAnchor(0.5f, 0.5f)
                }
            }
            valueAnimator?.start()
        }

    }

    private fun animateCameraInTrip(latLng: LatLng, previous: LatLng) {

        val cameraPosition = CameraPosition.Builder()
            .bearing(LocationHelper.getBearing(previous, latLng))
            .target(latLng).tilt(45f).zoom(16f).build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            Priority.PRIORITY_HIGH_ACCURACY ->{
                when(resultCode){
                    Activity.RESULT_OK ->{
                        getLocation()
                    }
                    Activity.RESULT_CANCELED ->{
                        locationChecker()
                    }
                }
            }
        }

    }

    private fun checkPermission(){
        if ((ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) && (
                    ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) ){
            ActivityCompat.requestPermissions(
                requireContext() as Activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),2)
        }else{
            locationChecker()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (resources.getString(R.string.mode) == "Night"){
            setMapDarkStyle(mMap)
        }

        mMap.setOnCameraIdleListener {
            val loc = mMap.cameraPosition.target
            address = getAddressFromLatLng(loc)
        }

//        mMap.uiSettings.apply {
//            isCompassEnabled = true
//            isZoomControlsEnabled = true
//            isMyLocationButtonEnabled = true
//        }
        checkPermission()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 2 && permissions.isNotEmpty() &&
            grantResults[0]== PackageManager.PERMISSION_GRANTED){
            locationChecker()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}