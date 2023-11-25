package com.mosamir.atmodrivepassenger.features.trip.presentation.fragment

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.gms.maps.model.LatLng
import com.mosamir.atmodrivepassenger.R
import com.mosamir.atmodrivepassenger.databinding.FragmentRequestTripBinding
import com.mosamir.atmodrivepassenger.features.trip.domain.model.ConfirmTripResponse
import com.mosamir.atmodrivepassenger.features.trip.domain.model.MakeTripResponse
import com.mosamir.atmodrivepassenger.features.trip.presentation.common.SharedViewModel
import com.mosamir.atmodrivepassenger.features.trip.presentation.common.TripViewModel
import com.mosamir.atmodrivepassenger.util.Constants
import com.mosamir.atmodrivepassenger.util.IResult
import com.mosamir.atmodrivepassenger.util.NetworkState
import com.mosamir.atmodrivepassenger.util.getAddressFromLatLng
import com.mosamir.atmodrivepassenger.util.getData
import com.mosamir.atmodrivepassenger.util.showToast
import com.mosamir.atmodrivepassenger.util.visibilityGone
import com.mosamir.atmodrivepassenger.util.visibilityVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Locale

@AndroidEntryPoint
class RequestTripFragment  : Fragment() {


    private var _binding: FragmentRequestTripBinding? = null
    private val binding get() = _binding!!
    private lateinit var mNavController: NavController

    var model = SharedViewModel()
    private val tripViewModel by viewModels<TripViewModel>()

    private var estimateCost: String = ""
    private var estimateTime: String = ""
    private var estimateDistance: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNavController = findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRequestTripBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        onClick()
        observeOnTripData()
        observeOnConfirmTrip()

    }

    private fun onClick(){
        binding.cardDateTime.setOnClickListener {
            val action = RequestTripFragmentDirections.actionRequestTripFragmentToTripDateTimeFragment()
            mNavController.navigate(action)
        }

        binding.btnRequestTrip.setOnClickListener {
            val pickUpLat = Constants.pickUpLatLng!!.latitude.toString()
            val pickUpLng = Constants.pickUpLatLng!!.longitude.toString()
            val dropOffLat = Constants.dropOffLatLng!!.latitude.toString()
            val dropOffLng = Constants.dropOffLatLng!!.longitude.toString()
            val pickUpName = getAddressFromLatLng(Constants.pickUpLatLng!!)
            val dropOffName = getAddressFromLatLng(Constants.dropOffLatLng!!)
            tripViewModel.confirmTrip("1",pickUpLat,pickUpLng,dropOffLat,dropOffLng,estimateCost,estimateTime,estimateDistance,pickUpName,dropOffName)
        }
    }

    private fun observeOnTripData(){
        model.makeTripData.observe(viewLifecycleOwner, Observer {
            estimateCost = it.estimate_cost
            estimateTime = it.estimate_time.toString()
            estimateDistance = it.estimate_distance.toString()
            binding.tvTripClassName.text = it.vehicle_class_name
            binding.tvTripEstimateCost.text = "$estimateCost EGP"
            binding.tvTripEstimateTime.text = "$estimateTime mins way from you"
            Glide.with(this)
                .load(Constants.BASE_Image_URL+it.vehicle_class_icon)
                .placeholder(R.drawable.atmo_plus)
                .into(binding.imgTripClassIcon)
        })
    }

    private fun observeOnConfirmTrip(){
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                tripViewModel.confirmTripResult.collect { networkState ->
                    when (networkState?.status) {
                        NetworkState.Status.SUCCESS -> {
                            binding.requestTripProgressBar.visibilityGone()
                            val data = networkState.data as IResult<ConfirmTripResponse>
//                        showToast(data.getData()?.trip_id!!)
                            Constants.tripId = data.getData()?.trip_id!!
                            model.setRequestTrip(true)
                        }

                        NetworkState.Status.FAILED -> {
                            showToast(networkState.msg.toString())
                            binding.requestTripProgressBar.visibilityGone()
                        }

                        NetworkState.Status.RUNNING -> {
                            binding.requestTripProgressBar.visibilityVisible()
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}