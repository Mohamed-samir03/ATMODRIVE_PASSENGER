package com.mosamir.atmodrivepassenger.futures.auth.presentation.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView.BufferType
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mosamir.atmodrivepassenger.databinding.FragmentVerifyBinding
import com.mosamir.atmodrivepassenger.futures.auth.domain.model.CheckCodeResponse
import com.mosamir.atmodrivepassenger.futures.auth.presentation.common.AuthViewModel
import com.mosamir.atmodrivepassenger.util.Constants
import com.mosamir.atmodrivepassenger.util.IResult
import com.mosamir.atmodrivepassenger.util.NetworkState
import com.mosamir.atmodrivepassenger.util.SharedPreferencesManager
import com.mosamir.atmodrivepassenger.util.disable
import com.mosamir.atmodrivepassenger.util.enabled
import com.mosamir.atmodrivepassenger.util.getData
import com.mosamir.atmodrivepassenger.util.showToast
import com.mosamir.atmodrivepassenger.util.visibilityGone
import com.mosamir.atmodrivepassenger.util.visibilityVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.NumberFormat


@AndroidEntryPoint
class VerifyFragment:Fragment() {

    private var _binding: FragmentVerifyBinding? = null
    private val binding get() = _binding!!
    private lateinit var mNavController: NavController
    private val loginViewModel by viewModels<AuthViewModel>()
    private val args by navArgs<VerifyFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNavController = findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentVerifyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mobile = args.mobile!!.toString()
        val fullText = "Enter the OTP code sent at mobile number <font color='#00A6A6'>+2${mobile}</font> to verify its you."
        binding.tvVerifyInfo.setText(Html.fromHtml(fullText), BufferType.SPANNABLE)

        if(!args.mobile.isNullOrBlank()){
            countDownTimer.start()
        }

        binding.btnVerify.setOnClickListener {
            val otpCode = binding.otpView.text.toString()
            loginViewModel.checkCode("device_token:${mobile}",mobile,otpCode)
        }

        binding.tvResendCode.setOnClickListener {
            loginViewModel.sendCode(args.mobile!!)
            countDownTimer.onTick(120000)
            countDownTimer.start()
        }

        observeOnCheckCode()
        observeOnSendCode()

        binding.verifyGoBack.setOnClickListener {
            countDownTimer.cancel()
            val action = VerifyFragmentDirections.actionVerifyToLogin()
            mNavController.navigate(action)
        }

    }



    private fun observeOnSendCode(){
        lifecycleScope.launch {
            loginViewModel.sendCodeResult.collect{ networkState ->
                when(networkState?.status){
                    NetworkState.Status.SUCCESS ->{
                        binding.verifyProgressBar.visibilityGone()
                    }
                    NetworkState.Status.FAILED ->{
                        showToast(networkState.msg.toString())
                        binding.verifyProgressBar.visibilityGone()
                    }
                    NetworkState.Status.RUNNING ->{
                        binding.verifyProgressBar.visibilityVisible()
                    }
                    else -> {}
                }
            }
        }
    }
    private fun observeOnCheckCode(){
        lifecycleScope.launch {
            loginViewModel.checkCodeResult.collect{ networkState ->
                when(networkState?.status){
                    NetworkState.Status.SUCCESS ->{
                        countDownTimer.cancel()
                        val data = networkState.data as IResult<CheckCodeResponse>
                        if(data.getData()?.is_new == true){
                            val action =
                                VerifyFragmentDirections.actionVerifyToCreateAccount2(args.mobile!!.toString())
                            mNavController.navigate(action)
                        }else{
                            // go Home
                            showToast("Successful Go Home")
                            val data = networkState.data as IResult<CheckCodeResponse>
                            saveUserDate(data)
                        }
                        binding.verifyProgressBar.visibilityGone()
                    }
                    NetworkState.Status.FAILED ->{
                        showToast(networkState.msg.toString())
                        binding.verifyProgressBar.visibilityGone()
                    }
                    NetworkState.Status.RUNNING ->{
                        binding.verifyProgressBar.visibilityVisible()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun saveUserDate(userData : IResult<CheckCodeResponse>){

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

    private val countDownTimer =
        object : CountDownTimer(120000, 1000) {

            // Callback function, fired on regular interval
            override fun onTick(millisUntilFinished: Long) {
                binding.tvResendCode.apply {
                    val f: NumberFormat = DecimalFormat("00")
                    val min = millisUntilFinished / 60000 % 60
                    val sec = millisUntilFinished / 1000 % 60
                    val mText =
                        "<font color='#B2C3C9'>Resend(${(f.format(min)).toString() + ":" + f.format(sec)}s)</font>"
                    setText(Html.fromHtml(mText), BufferType.SPANNABLE)
                    disable()
                }
            }

            // Callback function, fired
            // when the time is up
            override fun onFinish() {
                binding.tvResendCode.apply {
                    val mText = "<font color='#00A6A6'><u>Resend</u></font>"
                    setText(Html.fromHtml(mText), BufferType.SPANNABLE)
                    enabled()
                }
                cancel()
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        countDownTimer.cancel()
    }

}