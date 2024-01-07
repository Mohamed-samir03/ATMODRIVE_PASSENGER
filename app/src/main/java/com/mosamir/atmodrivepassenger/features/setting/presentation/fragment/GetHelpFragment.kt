package com.mosamir.atmodrivepassenger.features.setting.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mosamir.atmodrivepassenger.R
import com.mosamir.atmodrivepassenger.databinding.FragmentGetHelpBinding
import com.mosamir.atmodrivepassenger.databinding.FragmentPassengerAccountInformationBinding

class GetHelpFragment : Fragment() {

    private var _binding: FragmentGetHelpBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGetHelpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}