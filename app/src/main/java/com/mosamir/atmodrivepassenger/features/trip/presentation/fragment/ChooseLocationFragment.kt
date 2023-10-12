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
import com.mosamir.atmodrivepassenger.databinding.FragmentChooseLocationBinding
import com.mosamir.atmodrivepassenger.features.trip.presentation.common.SharedViewModel

class ChooseLocationFragment : Fragment() {


    private var _binding: FragmentChooseLocationBinding? = null
    private val binding get() = _binding!!
    private lateinit var mNavController: NavController

    var locType = ""
    var pickupLoc: String? = null
    var dropLoc: String? = null

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

        val model = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

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
                val action = ChooseLocationFragmentDirections.actionChooseLocationFragmentToRequestTripFragment()
                mNavController.navigate(action)
            }

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}