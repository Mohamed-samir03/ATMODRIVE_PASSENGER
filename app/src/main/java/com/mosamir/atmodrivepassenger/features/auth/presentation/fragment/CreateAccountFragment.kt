package com.mosamir.atmodrivepassenger.features.auth.presentation.fragment

import android.content.Intent
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
import com.mosamir.atmodrivepassenger.features.auth.domain.model.register.RegisterResponse
import com.mosamir.atmodrivepassenger.features.auth.presentation.common.AuthViewModel
import com.mosamir.atmodrivepassenger.features.trip.HomeActivity
import com.mosamir.atmodrivepassenger.util.Constants
import com.mosamir.atmodrivepassenger.util.IResult
import com.mosamir.atmodrivepassenger.util.NetworkState
import com.mosamir.atmodrivepassenger.util.SharedPreferencesManager
import com.mosamir.atmodrivepassenger.util.getData
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSubmit.setOnClickListener {
            val name = binding.etFullName.text.toString()
            val email = binding.etEmailAddress.text.toString()
            loginViewModel.registerUser(name,"0"+args.mobile!!,"","device_token:${args.mobile!!}","device_id:Oppo","android",email)
        }

        registerObserve()

        binding.createAccountGoBack.setOnClickListener {
            val action = CreateAccountFragmentDirections.actionCreateAccountToLogin(args.mobile!!.toString())
            mNavController.navigate(action)
        }

    }

    private fun registerObserve(){
        lifecycleScope.launch {
            loginViewModel.registerResult.collect{ networkState ->
                when(networkState?.status){
                    NetworkState.Status.SUCCESS ->{
                        binding.registerProgressBar.visibilityGone()
                        val data = networkState.data as IResult<RegisterResponse>
                        saveUserDate(data)
                        val intent = Intent(requireContext(), HomeActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
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
    }

    private fun saveUserDate(userData : IResult<RegisterResponse>){

        val data = userData.getData()?.data
        val myPrefs = SharedPreferencesManager(requireContext())

        myPrefs.saveString(Constants.AVATAR_PREFS,data!!.avatar)
        myPrefs.saveString(Constants.EMAIL_PREFS,data.email.toString())
        myPrefs.saveString(Constants.FULL_NAME_PREFS,data.full_name)
        myPrefs.saveString(Constants.IS_DARK_MODE_PREFS,data.is_dark_mode.toString())
        myPrefs.saveString(Constants.LANG_PREFS,data.lang)
        myPrefs.saveString(Constants.MOBILE_PREFS,data.mobile)
        myPrefs.saveString(Constants.PASSENGER_CODE_PREFS,data.passenger_code)
        myPrefs.saveString(Constants.RATE_PREFS,data.rate.toString())
        myPrefs.saveString(Constants.REMEMBER_TOKEN_PREFS,data.remember_token)
        myPrefs.saveString(Constants.SHAKE_PHONE_PREFS,data.shake_phone.toString())
        myPrefs.saveString(Constants.STATUS_PREFS,data.status.toString())
        myPrefs.saveString(Constants.SUSPEND_PREFS,data.suspend.toString())

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}