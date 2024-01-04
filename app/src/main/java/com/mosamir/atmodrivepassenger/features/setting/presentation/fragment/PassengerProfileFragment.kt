package com.mosamir.atmodrivepassenger.features.setting.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mosamir.atmodrivepassenger.R
import com.mosamir.atmodrivepassenger.databinding.FragmentPassengerProfileBinding

class PassengerProfileFragment : Fragment() {

    private var _binding: FragmentPassengerProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPassengerProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.accountInfoLayout.setOnClickListener {
            val action = PassengerProfileFragmentDirections.actionPassengerProfileFragmentToPassengerAccountInformationFragment()
            findNavController().navigate(action)
        }

        binding.tripHistoryLayout.setOnClickListener {
            val action = PassengerProfileFragmentDirections.actionPassengerProfileFragmentToTripHistoryFragment()
            findNavController().navigate(action)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}