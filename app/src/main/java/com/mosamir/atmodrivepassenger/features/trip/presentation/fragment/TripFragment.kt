package com.mosamir.atmodrivepassenger.features.trip.presentation.fragment

import android.Manifest
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
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
import androidx.activity.addCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
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
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mosamir.atmodrivepassenger.R
import com.mosamir.atmodrivepassenger.databinding.FragmentTripBinding
import com.mosamir.atmodrivepassenger.features.auth.presentation.common.AuthActivity
import com.mosamir.atmodrivepassenger.features.trip.domain.model.CancelTripResponse
import com.mosamir.atmodrivepassenger.features.trip.domain.model.ConfirmTripResponse
import com.mosamir.atmodrivepassenger.features.trip.presentation.common.SharedViewModel
import com.mosamir.atmodrivepassenger.features.trip.presentation.common.TripViewModel
import com.mosamir.atmodrivepassenger.util.AnimationUtils
import com.mosamir.atmodrivepassenger.util.Constants
import com.mosamir.atmodrivepassenger.util.IResult
import com.mosamir.atmodrivepassenger.util.LocationHelper
import com.mosamir.atmodrivepassenger.util.MapUtils
import com.mosamir.atmodrivepassenger.util.NetworkState
import com.mosamir.atmodrivepassenger.util.SharedPreferencesManager
import com.mosamir.atmodrivepassenger.util.getData
import com.mosamir.atmodrivepassenger.util.showToast
import com.mosamir.atmodrivepassenger.util.visibilityGone
import com.mosamir.atmodrivepassenger.util.visibilityVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Locale
@AndroidEntryPoint
class TripFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentTripBinding? = null
    private val binding get() = _binding!!
    private lateinit var mNavController: NavController

    private lateinit var mMap: GoogleMap
    var mLocationRequest: LocationRequest?= null
    var mLocationCallback: LocationCallback?= null
    var mFusedLocationClient: FusedLocationProviderClient?= null
    private var bottomSheet = BottomSheetBehavior<ConstraintLayout>()
    private var myNavHostFragment: NavHostFragment? = null
    var model = SharedViewModel()
    private val tripViewModel by viewModels<TripViewModel>()

    private var mBackPressed: Long = 0
    private var movingCabMarker : Marker?= null
    private var previousLatLng: LatLng? = null
    private var currentLatLng: LatLng? = null
    private var valueAnimator: ValueAnimator? = null

    private var valueEventListener : ValueEventListener?= null
    private lateinit var database: DatabaseReference

    private var pickUpMarker : Marker?= null
    private var dropOffMarker : Marker?= null
    var address = ""
    var pickupDropOff = 0
    var myLoc:LatLng? = null

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

        model = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        initLocation()
        initBottomSheet()
        onClick()
        observeOnLocationType()
        observeOnRequestTrip()
        observeOnCancelBeforeCaptain()
        onBackPressHandle()
//        handleBottomSheetSize()

    }

    private fun onClick(){

        binding.locationCard.setOnClickListener {
            binding.locationCard.visibilityGone()
            disPlayBottomSheet(R.navigation.trip_sheet_graph)
            try {
                Constants.pickUpLatLng = myLoc!!
                if(pickUpMarker == null){
                    pickUpMarker = addPickUpMarker(Constants.pickUpLatLng!!)
                }
                pickUpMarker?.position = Constants.pickUpLatLng!!
            }catch (ex:NullPointerException){
                showToast("There is no internet connection")
            }
        }

        binding.tvCancelFindCaptain.setOnClickListener {
            binding.layoutFindCaptain.visibilityGone()

//            model.setRequestTrip(false)
//            bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
//            Constants.isBottomSheetOn = true

            // cancel trip
            tripViewModel.cancelBeforeCaptain(Constants.tripId)

            clearMap()

        }

        binding.btnConfirmLocation.setOnClickListener {
            model.setLocation(address)
            binding.chooseLocationFromMaps.visibilityGone()
            bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
            Constants.isBottomSheetOn = true
            if(pickupDropOff == 1){
                if(pickUpMarker == null){
                    pickUpMarker = addPickUpMarker(Constants.pickUpLatLng!!)
                }
                pickUpMarker?.position = Constants.pickUpLatLng!!
            }else if (pickupDropOff == 2){
                try {
                    if (dropOffMarker == null){
                        dropOffMarker = addDropOffMarker(Constants.dropOffLatLng!!)
                    }
                    dropOffMarker?.position = Constants.dropOffLatLng!!
                }catch (ex:NullPointerException){
                    if (dropOffMarker == null){
                        dropOffMarker = addDropOffMarker(myLoc!!)
                    }
                    dropOffMarker?.position = myLoc!!
                    Constants.dropOffLatLng = myLoc
                }

            }
        }

        binding.btnCancelLocation.setOnClickListener {
            binding.chooseLocationFromMaps.visibilityGone()
            bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
            Constants.isBottomSheetOn = true
        }

        binding.imgCategory.setOnClickListener {
            // logout for test only
            SharedPreferencesManager(requireContext()).clearString(Constants.REMEMBER_TOKEN_PREFS)
            val intent = Intent(requireContext(), AuthActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

    }

    private fun observeOnLocationType(){
        model.locType.observe(viewLifecycleOwner, Observer {

            if (it == "pickupLoc"){
                pickupDropOff = 1
                binding.chooseLocationFromMaps.visibilityVisible()
                bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
                Constants.isBottomSheetOn = false
                setLocation(false)
            }else if(it == "dropLoc"){
                pickupDropOff = 2
                binding.chooseLocationFromMaps.visibilityVisible()
                bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
                Constants.isBottomSheetOn = false
                setLocation(false)
            }else if (it == "cancel"){
                clearMap()
            }else if(it == "continue"){

                val builder = LatLngBounds.Builder()
                builder.include(Constants.pickUpLatLng!!)
                builder.include(Constants.dropOffLatLng!!)
                val bounds = builder.build()
                mMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 200))

                // drawPath

            }

        })
    }

    private fun observeOnRequestTrip(){
        model.requestTrip.observe(viewLifecycleOwner, Observer {

            if (it){
                findingCaptain()
                listenerOnTrip()
            }else if(!it){
                showToast("tripCanceled")
                clearMap()
            }

        })
    }

    private fun observeOnCancelBeforeCaptain(){
        lifecycleScope.launch {
            tripViewModel.cancelBeforeCaptainResult.collect{ networkState ->
                when(networkState?.status){
                    NetworkState.Status.SUCCESS ->{
                        val data = networkState.data as IResult<CancelTripResponse>
                        showToast(data.getData()?.message!!)
                    }
                    NetworkState.Status.FAILED ->{
                        showToast(networkState.msg.toString())
                    }
                    NetworkState.Status.RUNNING ->{
                    }
                    else -> {}
                }
            }
        }
    }

    private fun listenerOnTrip(){
        valueEventListener =  object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val tripStats = snapshot.getValue(String::class.java)

                if (tripStats != null){
                    binding.layoutFindCaptain.visibilityGone()
                    disPlayBottomSheet(R.navigation.trip_lifecycle_nav_graph)
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        }

        database.child("trips").child(Constants.tripId.toString()).child("status")
            .addValueEventListener(valueEventListener!!)
    }

    private fun initBottomSheet(){
        val bottomSheetView = view?.findViewById<ConstraintLayout>(R.id.bottom_sheet_trip)
        bottomSheet = BottomSheetBehavior.from(bottomSheetView!!)
        bottomSheet.isDraggable = false
        myNavHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_trip_sheet) as NavHostFragment
    }

    private fun disPlayBottomSheet(nav:Int){
        val inflater = myNavHostFragment?.navController?.navInflater
        val graph = inflater?.inflate(nav)
        myNavHostFragment?.navController?.graph = graph!!
        bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
        Constants.isBottomSheetOn = true
    }

    private fun handleBottomSheetSize() {

        myNavHostFragment?.navController?.addOnDestinationChangedListener { _, destination, arguments ->

            if (destination.id == R.id.chooseLocationFragment || destination.id == R.id.requestTripFragment) {
                changeHeightOfSheet(requireContext(), 0.90)
                bottomSheet.isDraggable = false
            }

        }

    }

    private fun onBackPressHandle() {

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {

            val childFragment = myNavHostFragment?.childFragmentManager?.fragments

            if (childFragment?.size != 0 && Constants.isBottomSheetOn) {
                var fragment = childFragment?.get(0)

                if ((fragment is ChooseLocationFragment) && bottomSheet.state == BottomSheetBehavior.STATE_EXPANDED) {
                    clearMap()
                }
            } else {
                requireActivity().finish()
            }
        }
    }

    private fun changeHeightOfSheet(context: Context, percent: Double) {
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        bottomSheet.peekHeight = (displayMetrics.heightPixels * percent).toInt()
    }

    private fun clearMap(){
        bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
        binding.locationCard.visibilityVisible()
        setLocation(true)
        mMap.clear()
        moveCameraMap(myLoc!!)
        pickUpMarker = null
        dropOffMarker = null
        Constants.pickUpLatLng = myLoc
        Constants.dropOffLatLng = null
        pickupDropOff = 0
        Constants.isBottomSheetOn = false
    }

    private fun findingCaptain(){
        bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
        Constants.isBottomSheetOn = false
        binding.layoutFindCaptain.visibilityVisible()
    }

    private fun setLocation(status:Boolean){
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
        }
        mMap.isMyLocationEnabled = status
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
                myLoc = latLng

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
            showToast("There is no internet connection")
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

    private fun addPickUpMarker(latLng: LatLng): Marker {
        val bitmapDescriptor =
            BitmapDescriptorFactory.fromBitmap(MapUtils.getPickupBitmap(requireContext()))
        return mMap.addMarker(
            MarkerOptions().position(latLng).flat(true).icon(bitmapDescriptor)
        )!!
    }

    private fun addDropOffMarker(latLng: LatLng): Marker {
        val bitmapDescriptor =
            BitmapDescriptorFactory.fromBitmap(MapUtils.getDropOffBitmap(requireContext()))
        return mMap.addMarker(
            MarkerOptions().position(latLng).flat(true).icon(bitmapDescriptor)
        )!!
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
            if(pickupDropOff == 1){
                Constants.pickUpLatLng = loc
            }else if (pickupDropOff == 2){
                Constants.dropOffLatLng = loc
            }
        }

//        addPickUpMarker(LatLng(30.527235943660287, 30.920170507259662))


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
        database.child("trips").child(Constants.tripId.toString()).child("status")
            .removeEventListener(valueEventListener!!)
    }
}