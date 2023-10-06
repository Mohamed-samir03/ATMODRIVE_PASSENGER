package com.mosamir.atmodrivepassenger.features.auth.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mosamir.atmodrivepassenger.features.auth.domain.use_case.ICheckCodeUseCase
import com.mosamir.atmodrivepassenger.features.auth.domain.use_case.IRegisterUseCase
import com.mosamir.atmodrivepassenger.features.auth.domain.use_case.ISendCodeUseCase
import com.mosamir.atmodrivepassenger.util.NetworkState
import com.mosamir.atmodrivepassenger.util.getError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val iSendCodeUseCase: ISendCodeUseCase,
    private val iCheckCodeUseCase: ICheckCodeUseCase,
    private val iRegisterUseCase: IRegisterUseCase
):ViewModel() {


    private val _sendCodeResult: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val sendCodeResult: StateFlow<NetworkState?> =_sendCodeResult

    private val _checkCodeResult: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val checkCodeResult:StateFlow<NetworkState?> = _checkCodeResult

    private val _registerResult: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val registerResult:StateFlow<NetworkState?> = _registerResult



    fun sendCode(mobile: String) {
        _sendCodeResult.value = NetworkState.LOADING
        viewModelScope.launch {
            try {
                val result = iSendCodeUseCase.sendCode(mobile)
                if (result.isSuccessful()){
                    _sendCodeResult.value = NetworkState.getLoaded(result)
                }else{
                    _sendCodeResult.value = NetworkState.getErrorMessage(result.getError().toString())
                }
            }catch (ex:Exception){
                ex.printStackTrace()
                _sendCodeResult.value = NetworkState.getErrorMessage(ex)
            }
        }
    }


    fun checkCode(deviceToken: String, mobile: String, verificationCode: String) {
        _checkCodeResult.value = NetworkState.LOADING
        viewModelScope.launch {
            try {
                val result = iCheckCodeUseCase.checkCode(mobile,verificationCode, deviceToken)
                if (result.isSuccessful()){
                    _checkCodeResult.value = NetworkState.getLoaded(result)
                }else{
                    _checkCodeResult.value = NetworkState.getErrorMessage(result.getError().toString())
                }
            }catch (ex:Exception){
                ex.printStackTrace()
                _checkCodeResult.value = NetworkState.getErrorMessage(ex)
            }
        }
    }


    fun registerUser(fullName:String, mobile:String, avatar:String, deviceToken:String, deviceId:String, deviceType:String, email:String) {
        _registerResult.value = NetworkState.LOADING
        viewModelScope.launch {
            try {
                val result = iRegisterUseCase.registerUser(fullName, mobile, avatar, deviceToken, deviceId, deviceType,email)
                if (result.isSuccessful()){
                    _registerResult.value = NetworkState.getLoaded(result)
                }else{
                    _registerResult.value = NetworkState.getErrorMessage(result.getError().toString())
                }
            }catch (ex:Exception){
                ex.printStackTrace()
                _registerResult.value = NetworkState.getErrorMessage(ex)
            }
        }
    }


}