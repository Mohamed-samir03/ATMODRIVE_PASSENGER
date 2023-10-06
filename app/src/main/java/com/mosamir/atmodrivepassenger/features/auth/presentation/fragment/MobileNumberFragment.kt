package com.mosamir.atmodrivepassenger.features.auth.presentation.fragment


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
import com.mosamir.atmodrivepassenger.databinding.FragmentMobileNumberBinding
import com.mosamir.atmodrivepassenger.features.auth.domain.model.login.LoginResponse
import com.mosamir.atmodrivepassenger.features.auth.presentation.common.AuthViewModel
import com.mosamir.atmodrivepassenger.util.IResult
import com.mosamir.atmodrivepassenger.util.NetworkState
import com.mosamir.atmodrivepassenger.util.getData
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
    private val args by navArgs<MobileNumberFragmentArgs>()

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnContinue.setOnClickListener {
            val phone = binding.etPhoneNumber.text.toString()
            loginViewModel.sendCode("0$phone")
        }
        binding.ccp.setCcpClickable(false)

        sendCodeObserve()

        val mobile = args.mobile
        if (!mobile.isNullOrBlank()){
            binding.etPhoneNumber.setText(mobile)
            binding.etPhoneNumber.requestFocus()
        }

    }

    private fun sendCodeObserve(){
        lifecycleScope.launch {
            loginViewModel.sendCodeResult.collect{ networkState ->
                when(networkState?.status){
                    NetworkState.Status.SUCCESS ->{
                        val data = networkState.data as IResult<LoginResponse>
                        val phone = binding.etPhoneNumber.text.toString()
                        val name = data.getData()?.data?.full_name
                        val action =
                            MobileNumberFragmentDirections.actionLoginToVerify(phone,name)
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}