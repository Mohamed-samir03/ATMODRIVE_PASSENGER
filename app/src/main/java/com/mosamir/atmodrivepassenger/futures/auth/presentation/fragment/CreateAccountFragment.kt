package com.mosamir.atmodrivepassenger.futures.auth.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mosamir.atmodrivepassenger.databinding.FragmentCreateAccountBinding
import com.mosamir.atmodrivepassenger.futures.auth.presentation.AuthViewModel
import com.mosamir.atmodrivepassenger.util.NetworkState
import com.mosamir.atmodrivepassenger.util.showToast
import com.mosamir.atmodrivepassenger.util.visibilityGone
import com.mosamir.atmodrivepassenger.util.visibilityVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateAccountFragment:Fragment() {

    private var _binding: FragmentCreateAccountBinding? = null
    private val binding get() = _binding!!
    private lateinit var mNavController: NavController
    private val loginViewModel by viewModels<AuthViewModel>()
    private val args by navArgs<CreateAccountFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNavController = findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCreateAccountBinding.inflate(inflater, container, false)

        binding.btnSubmit.setOnClickListener {
            val name = binding.etFullName.text.toString()
            val email = binding.etEmailAddress.text.toString()
            loginViewModel.registerUser(name,args.mobile!!,"","device_token:${args.mobile!!}","device_id:Oppo","android")
        }

        lifecycleScope.launch {
            loginViewModel.registerResult.collect{ networkState ->
                when(networkState?.status){
                    NetworkState.Status.SUCCESS ->{
                        showToast("Go Home")
                        binding.registerProgressBar.visibilityGone()
                    }
                    NetworkState.Status.FAILED ->{
                        showToast(networkState.msg.toString())
                        binding.registerProgressBar.visibilityGone()
                    }
                    NetworkState.Status.RUNNING ->{
                        binding.registerProgressBar.visibilityVisible()
                    }
                    else -> {}
                }
            }
        }

//        binding.createAccountGoBack.setOnClickListener {
//            val action = CreateAccountDirections.actionCreateAccountToVerify(args.mobile!!.toString())
//            mNavController.navigate(action)
//        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}