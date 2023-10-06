package com.mosamir.atmodrivepassenger.features.auth.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.mosamir.atmodrivepassenger.R
import com.mosamir.atmodrivepassenger.databinding.FragmentIntroBinding
import com.mosamir.atmodrivepassenger.util.Constants
import com.mosamir.atmodrivepassenger.util.SharedPreferencesManager

class IntroFragment:Fragment() {

    private var _binding: FragmentIntroBinding? = null
    private val binding get() = _binding!!
    private lateinit var mNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNavController = findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentIntroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (resources.getString(R.string.mode) == "Night"){
            binding.layoutIntro.setBackgroundResource(R.drawable.mapviewdark)
        }else{
            binding.layoutIntro.setBackgroundResource(R.drawable.mapview)
        }

        val intro = SharedPreferencesManager(requireContext()).getString(Constants.INTRO_PREFS)

        if (intro == "1"){
            val action = IntroFragmentDirections.actionIntroToLogin(null)
            mNavController.navigate(action)
        }

        binding.btnGetStart.setOnClickListener {
            SharedPreferencesManager(requireContext()).saveString(Constants.INTRO_PREFS,"1")
            val action = IntroFragmentDirections.actionIntroToLogin(null)
            mNavController.navigate(action)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}