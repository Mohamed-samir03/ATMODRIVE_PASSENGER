package com.mosamir.atmodrivepassenger.features.trip.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.mosamir.atmodrivepassenger.R
import com.mosamir.atmodrivepassenger.databinding.FragmentRequestTripBinding
import com.mosamir.atmodrivepassenger.features.trip.presentation.common.SharedViewModel
import com.mosamir.atmodrivepassenger.util.Constants

class RequestTripFragment  : Fragment() {


    private var _binding: FragmentRequestTripBinding? = null
    private val binding get() = _binding!!
    private lateinit var mNavController: NavController

    var model = SharedViewModel()

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

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}