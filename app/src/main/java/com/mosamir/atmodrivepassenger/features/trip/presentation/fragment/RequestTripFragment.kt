package com.mosamir.atmodrivepassenger.features.trip.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.mosamir.atmodrivepassenger.R
import com.mosamir.atmodrivepassenger.databinding.FragmentRequestTripBinding
import com.mosamir.atmodrivepassenger.features.trip.domain.model.ConfirmTripResponse
import com.mosamir.atmodrivepassenger.features.trip.domain.model.MakeTripResponse
import com.mosamir.atmodrivepassenger.features.trip.presentation.common.SharedViewModel
import com.mosamir.atmodrivepassenger.features.trip.presentation.common.TripViewModel
import com.mosamir.atmodrivepassenger.util.Constants
import com.mosamir.atmodrivepassenger.util.IResult
import com.mosamir.atmodrivepassenger.util.NetworkState
import com.mosamir.atmodrivepassenger.util.getData
import com.mosamir.atmodrivepassenger.util.showToast
import com.mosamir.atmodrivepassenger.util.visibilityGone
import com.mosamir.atmodrivepassenger.util.visibilityVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RequestTripFragment  : Fragment() {


    private var _binding: FragmentRequestTripBinding? = null
    private val binding get() = _binding!!
    private lateinit var mNavController: NavController

    var model = SharedViewModel()
    private val tripViewModel by viewModels<TripViewModel>()

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

        binding.cardDateTime.setOnClickListener {
            val action = RequestTripFragmentDirections.actionRequestTripFragmentToTripDateTimeFragment()
            mNavController.navigate(action)
        }

        model.makeTripData.observe(viewLifecycleOwner, Observer {
            binding.tvTripClassName.text = it.vehicle_class_name
            binding.tvTripEstimateCost.text = "${it.estimate_cost} EGP"
            binding.tvTripEstimateTime.text = "${it.estimate_time} mins way from you"
            Glide.with(this)
                .load(Constants.BASE_Image_URL+it.vehicle_class_icon)
                .placeholder(R.drawable.atmo_plus)
                .into(binding.imgTripClassIcon)
        })

        binding.btnRequestTrip.setOnClickListener {
            tripViewModel.confirmTrip("1","","","","","","","","","")
        }
        observeOnConfirmTrip()

    }

    private fun observeOnConfirmTrip(){
        lifecycleScope.launch {
            tripViewModel.confirmTripResult.collect{ networkState ->
                when(networkState?.status){
                    NetworkState.Status.SUCCESS ->{
                        binding.requestTripProgressBar.visibilityGone()
                        val data = networkState.data as IResult<ConfirmTripResponse>
                        showToast(data.getData()?.trip_id!!)
                        val action = ChooseLocationFragmentDirections.actionChooseLocationFragmentToRequestTripFragment()
                        mNavController.navigate(action)
                    }
                    NetworkState.Status.FAILED ->{
                        showToast(networkState.msg.toString())
                        binding.requestTripProgressBar.visibilityGone()
                    }
                    NetworkState.Status.RUNNING ->{
                        binding.requestTripProgressBar.visibilityVisible()
                    }
                    else -> {}
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}