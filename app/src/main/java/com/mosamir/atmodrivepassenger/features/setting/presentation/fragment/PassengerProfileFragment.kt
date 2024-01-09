package com.mosamir.atmodrivepassenger.features.setting.presentation.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mosamir.atmodrivepassenger.R
import com.mosamir.atmodrivepassenger.databinding.FragmentPassengerProfileBinding
import com.mosamir.atmodrivepassenger.features.auth.presentation.common.AuthActivity
import com.mosamir.atmodrivepassenger.features.setting.presentation.common.SettingActivity
import com.mosamir.atmodrivepassenger.features.trip.presentation.common.TripActivity
import com.mosamir.atmodrivepassenger.util.Constants
import com.mosamir.atmodrivepassenger.util.SharedPreferencesManager

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

        binding.backFromPassengerProfile.setOnClickListener {
            val intent = Intent(requireContext(), TripActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        binding.accountInfoLayout.setOnClickListener {
            val action = PassengerProfileFragmentDirections.actionPassengerProfileFragmentToPassengerAccountInformationFragment()
            findNavController().navigate(action)
        }

        binding.tripHistoryLayout.setOnClickListener {
            val action = PassengerProfileFragmentDirections.actionPassengerProfileFragmentToTripHistoryFragment()
            findNavController().navigate(action)
        }

        binding.getHelpLayout.setOnClickListener {
            val action = PassengerProfileFragmentDirections.actionPassengerProfileFragmentToGetHelpFragment()
            findNavController().navigate(action)
        }

        binding.settingLayout.setOnClickListener {
            val action = PassengerProfileFragmentDirections.actionPassengerProfileFragmentToSettingsFragment()
            findNavController().navigate(action)
        }

        binding.btnLogout.setOnClickListener{
            SharedPreferencesManager(requireContext()).clearString(Constants.REMEMBER_TOKEN_PREFS)
            val intent = Intent(requireContext(), AuthActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}