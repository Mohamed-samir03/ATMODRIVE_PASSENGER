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
import com.mosamir.atmodrivepassenger.databinding.FragmentMobileNumberBinding
import com.mosamir.atmodrivepassenger.futures.auth.presentation.AuthViewModel
import com.mosamir.atmodrivepassenger.util.NetworkState
import com.mosamir.atmodrivepassenger.util.showToast
import com.mosamir.atmodrivepassenger.util.visibilityGone
import com.mosamir.atmodrivepassenger.util.visibilityVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MobileNumberFragment:Fragment() {

    private var _binding: FragmentMobileNumberBinding? = null
    private val binding get() = _binding!!
    private lateinit var mNavController: NavController
    private val loginViewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNavController = findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMobileNumberBinding.inflate(inflater, container, false)

        binding.btnContinue.setOnClickListener {
            val phone = binding.etPhoneNumber.text.toString()
            loginViewModel.sendCode(phone)
        }

        lifecycleScope.launch {
            loginViewModel.sendCodeResult.collect{ networkState ->
                when(networkState?.status){
                    NetworkState.Status.SUCCESS ->{
                        val action =
                            MobileNumberFragmentDirections.actionLoginToVerify(binding.etPhoneNumber.text.toString())
                        mNavController.navigate(action)
                        binding.loginProgressBar.visibilityGone()
                    }
                    NetworkState.Status.FAILED ->{
                        showToast(networkState.msg.toString())
                        binding.loginProgressBar.visibilityGone()
                    }
                    NetworkState.Status.RUNNING ->{
                        binding.loginProgressBar.visibilityVisible()
                    }
                    else -> {}
                }
            }
        }


        binding.goBack.setOnClickListener {
            val action = MobileNumberFragmentDirections.actionLoginToIntro()
            mNavController.navigate(action)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}