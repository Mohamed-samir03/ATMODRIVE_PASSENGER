package com.mosamir.atmodrivepassenger.futures.auth.presentation.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView.BufferType
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mosamir.atmodrivepassenger.databinding.FragmentVerifyBinding
import com.mosamir.atmodrivepassenger.futures.auth.domain.model.CheckCodeResponse
import com.mosamir.atmodrivepassenger.futures.auth.presentation.AuthViewModel
import com.mosamir.atmodrivepassenger.util.IResult
import com.mosamir.atmodrivepassenger.util.NetworkState
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
            loginViewModel.checkCode("device_token:${args.mobile!!}",args.mobile!!,"0000")
        }

        lifecycleScope.launch {
            loginViewModel.checkCodeResult.collect{ networkState ->
                when(networkState?.status){
                    NetworkState.Status.SUCCESS ->{
                        countDownTimer.cancel()
                        val data = networkState.data as IResult<CheckCodeResponse>
                        if(data.getData()?.is_new == true){
                            val action =
                                VerifyFragmentDirections.actionVerifyToCreateAccount2(mobile)
                            mNavController.navigate(action)
                        }else{
                            // go Home
                            showToast("Go Home")
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

        binding.verifyGoBack.setOnClickListener {
            countDownTimer.cancel()
            val action = VerifyFragmentDirections.actionVerifyToLogin()
            mNavController.navigate(action)
        }

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
    }

}