package com.mosamir.atmodrivepassenger.features.trip.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mosamir.atmodrivepassenger.features.trip.domain.use_case.ICancelBeforeCaptainUseCase
import com.mosamir.atmodrivepassenger.features.trip.domain.use_case.ICancelTripUseCase
import com.mosamir.atmodrivepassenger.features.trip.domain.use_case.IConfirmTripUseCase
import com.mosamir.atmodrivepassenger.features.trip.domain.use_case.IGetCaptainDetailsUseCase
import com.mosamir.atmodrivepassenger.features.trip.domain.use_case.IGetTripDetailsUseCase
import com.mosamir.atmodrivepassenger.features.trip.domain.use_case.IMakeTripUseCase
import com.mosamir.atmodrivepassenger.features.trip.domain.use_case.IOnTripUseCase
import com.mosamir.atmodrivepassenger.util.NetworkState
import com.mosamir.atmodrivepassenger.util.getError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TripViewModel @Inject constructor(
    private val iMakeTripUseCase: IMakeTripUseCase,
    private val iConfirmTripUseCase: IConfirmTripUseCase,
    private val iCancelBeforeCaptainUseCase: ICancelBeforeCaptainUseCase,
    private val iGetCaptainDetailsUseCase: IGetCaptainDetailsUseCase,
    private val iOnTripUseCase: IOnTripUseCase,
    private val iCancelTripUseCase: ICancelTripUseCase,
    private val iGetTripDetailsUseCase: IGetTripDetailsUseCase
):ViewModel(){

    private val _makeTripResult: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val makeTripResult: StateFlow<NetworkState?> =_makeTripResult

    private val _confirmTripResult: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val confirmTripResult: StateFlow<NetworkState?> =_confirmTripResult

    private val _cancelBeforeCaptainResult: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val cancelBeforeCaptainResult: StateFlow<NetworkState?> =_cancelBeforeCaptainResult

    private val _getCaptainDetailsResult: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val getCaptainDetailsResult: StateFlow<NetworkState?> =_getCaptainDetailsResult

    private val _onTripResult: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val onTripResult: StateFlow<NetworkState?> =_onTripResult

    private val _cancelTripResult: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val cancelTripResult: StateFlow<NetworkState?> =_cancelTripResult

    private val _tripDetailsResult: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val tripDetailsResult: StateFlow<NetworkState?> =_tripDetailsResult


    fun makeTrip(distanceText: String,
                 distanceValue: Long,
                 durationText: String,
                 durationValue: Long) {
        _makeTripResult.value = NetworkState.LOADING
        viewModelScope.launch {
            try {
                val result = iMakeTripUseCase.makeTrip(distanceText, distanceValue, durationText, durationValue)
                if (result.isSuccessful()){
                    _makeTripResult.value = NetworkState.getLoaded(result)
                }else{
                    _makeTripResult.value = NetworkState.getErrorMessage(result.getError().toString())
                }
            }catch (ex:Exception){
                ex.printStackTrace()
                _makeTripResult.value = NetworkState.getErrorMessage(ex)
            }
        }
    }

    fun confirmTrip(vehicleClassId: String,pickupLat: String,
                    pickupLng: String,dropOffLat: String,dropOffLng: String,
                    estimateCost: String,estimateTime: String,estimateDistance: String,
                    pickupLocationName: String,dropOffLocationName: String) {
        _confirmTripResult.value = NetworkState.LOADING
        viewModelScope.launch {
            try {
                val result = iConfirmTripUseCase.confirmTrip(vehicleClassId,
                    pickupLat,
                    pickupLng,
                    dropOffLat,
                    dropOffLng,
                    estimateCost,
                    estimateTime,
                    estimateDistance,
                    pickupLocationName,
                    dropOffLocationName)
                if (result.isSuccessful()){
                    _confirmTripResult.value = NetworkState.getLoaded(result)
                }else{
                    _confirmTripResult.value = NetworkState.getErrorMessage(result.getError().toString())
                }
            }catch (ex:Exception){
                ex.printStackTrace()
                _confirmTripResult.value = NetworkState.getErrorMessage(ex)
            }
        }
    }

    fun cancelBeforeCaptain(tripId:Int) {
        _cancelBeforeCaptainResult.value = NetworkState.LOADING
        viewModelScope.launch {
            try {
                val result = iCancelBeforeCaptainUseCase.cancelBeforeCaptain(tripId)
                if (result.isSuccessful()){
                    _cancelBeforeCaptainResult.value = NetworkState.getLoaded(result)
                }else{
                    _cancelBeforeCaptainResult.value = NetworkState.getErrorMessage(result.getError().toString())
                }
            }catch (ex:Exception){
                ex.printStackTrace()
                _cancelBeforeCaptainResult.value = NetworkState.getErrorMessage(ex)
            }
        }
    }

    fun getCaptainDetails(tripId:Int) {
        _getCaptainDetailsResult.value = NetworkState.LOADING
        viewModelScope.launch {
            try {
                val result = iGetCaptainDetailsUseCase.getCaptainDetails(tripId)
                if (result.isSuccessful()){
                    _getCaptainDetailsResult.value = NetworkState.getLoaded(result)
                }else{
                    _getCaptainDetailsResult.value = NetworkState.getErrorMessage(result.getError().toString())
                }
            }catch (ex:Exception){
                ex.printStackTrace()
                _getCaptainDetailsResult.value = NetworkState.getErrorMessage(ex)
            }
        }
    }

    fun onTrip() {
        _onTripResult.value = NetworkState.LOADING
        viewModelScope.launch {
            try {
                val result = iOnTripUseCase.onTrip()
                if (result.isSuccessful()){
                    _onTripResult.value = NetworkState.getLoaded(result)
                }else{
                    _onTripResult.value = NetworkState.getErrorMessage(result.getError().toString())
                }
            }catch (ex:Exception){
                ex.printStackTrace()
                _onTripResult.value = NetworkState.getErrorMessage(ex)
            }
        }
    }

    fun cancelTrip(tripId:Int) {
        _cancelTripResult.value = NetworkState.LOADING
        viewModelScope.launch {
            try {
                val result = iCancelTripUseCase.cancelTrip(tripId)
                if (result.isSuccessful()){
                    _cancelTripResult.value = NetworkState.getLoaded(result)
                }else{
                    _cancelTripResult.value = NetworkState.getErrorMessage(result.getError().toString())
                }
            }catch (ex:Exception){
                ex.printStackTrace()
                _cancelTripResult.value = NetworkState.getErrorMessage(ex)
            }
        }
    }

    fun getTripDetails(tripId:Int) {
        _tripDetailsResult.value = NetworkState.LOADING
        viewModelScope.launch {
            try {
                val result = iGetTripDetailsUseCase.getTripDetails(tripId)
                if (result.isSuccessful()){
                    _tripDetailsResult.value = NetworkState.getLoaded(result)
                }else{
                    _tripDetailsResult.value = NetworkState.getErrorMessage(result.getError().toString())
                }
            }catch (ex:Exception){
                ex.printStackTrace()
                _tripDetailsResult.value = NetworkState.getErrorMessage(ex)
            }
        }
    }

}