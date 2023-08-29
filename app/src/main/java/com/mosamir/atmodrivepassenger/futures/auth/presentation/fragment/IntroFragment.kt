package com.mosamir.atmodrivepassenger.futures.auth.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.mosamir.atmodrivepassenger.R
import com.mosamir.atmodrivepassenger.databinding.FragmentIntroBinding

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

        if (resources.getString(R.string.mode) == "Night"){
            binding.layoutIntro.setBackgroundResource(R.drawable.mapviewdark)
        }else{
            binding.layoutIntro.setBackgroundResource(R.drawable.mapview)
        }

        binding.btnGetStart.setOnClickListener {
            val action = IntroFragmentDirections.actionIntroToLogin()
            mNavController.navigate(action)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}