package com.mosamir.atmodrivepassenger.features.trip.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.mosamir.atmodrivepassenger.databinding.FragmentChooseLocationBinding
import com.mosamir.atmodrivepassenger.features.trip.domain.model.MakeTripResponse
import com.mosamir.atmodrivepassenger.features.trip.presentation.common.SharedViewModel
import com.mosamir.atmodrivepassenger.features.trip.presentation.common.TripViewModel
import com.mosamir.atmodrivepassenger.util.Constants
import com.mosamir.atmodrivepassenger.util.IResult
import com.mosamir.atmodrivepassenger.util.NetworkState
import com.mosamir.atmodrivepassenger.util.SharedPreferencesManager
import com.mosamir.atmodrivepassenger.util.getData
import com.mosamir.atmodrivepassenger.util.showToast
import com.mosamir.atmodrivepassenger.util.visibilityGone
import com.mosamir.atmodrivepassenger.util.visibilityVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChooseLocationFragment : Fragment() {


    private var _binding: FragmentChooseLocationBinding? = null
    private val binding get() = _binding!!
    private lateinit var mNavController: NavController

    var locType = ""
    var pickupLoc: String? = null
    var dropLoc: String? = null
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
        _binding = FragmentChooseLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        val loc = SharedPreferencesManager(requireContext()).getString(Constants.PICKUP_LOC)
        binding.tvPickupLocation.text = loc

        binding.tvPickupLocation.setOnClickListener {
            locType = "pickupLoc"
            model.setLocType("pickupLoc")
        }

        binding.tvDropOffLocation.setOnClickListener {
            locType = "dropLoc"
            model.setLocType("dropLoc")
        }

        model.location.observe(viewLifecycleOwner, Observer {

            if (locType == "pickupLoc"){
                binding.tvPickupLocation.text = it.toString()
                pickupLoc = it.toString()
            }else if (locType == "dropLoc"){
                binding.tvDropOffLocation.text = it.toString()
                dropLoc = it.toString()
            }

            if(!pickupLoc.isNullOrBlank() && !dropLoc.isNullOrBlank()){
                tripViewModel.makeTrip("500 KM",500,"160 Min",160)
            }

        })

        observeOnMakeTrip()

    }

    private fun observeOnMakeTrip(){
        lifecycleScope.launch {
            tripViewModel.makeTripResult.collect{ networkState ->
                when(networkState?.status){
                    NetworkState.Status.SUCCESS ->{
                        binding.chooseLocProgressBar.visibilityGone()
                        val data = networkState.data as IResult<MakeTripResponse>
                        model.setMakeTripData(data.getData()?.data!!)
                        val action = ChooseLocationFragmentDirections.actionChooseLocationFragmentToRequestTripFragment()
                        mNavController.navigate(action)
                    }
                    NetworkState.Status.FAILED ->{
                        showToast(networkState.msg.toString())
                        binding.chooseLocProgressBar.visibilityGone()
                    }
                    NetworkState.Status.RUNNING ->{
                        binding.chooseLocProgressBar.visibilityVisible()
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