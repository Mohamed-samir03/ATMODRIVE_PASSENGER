package com.mosamir.atmodrivepassenger.features.trip.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mosamir.atmodrivepassenger.features.trip.domain.use_case.ICancelBeforeCaptainUseCase
import com.mosamir.atmodrivepassenger.features.trip.domain.use_case.IConfirmTripUseCase
import com.mosamir.atmodrivepassenger.features.trip.domain.use_case.IGetCaptainDetailsUseCase
import com.mosamir.atmodrivepassenger.features.trip.domain.use_case.IMakeTripUseCase
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
    private val iGetCaptainDetailsUseCase: IGetCaptainDetailsUseCase
):ViewModel(){

    private val _makeTripResult: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val makeTripResult: StateFlow<NetworkState?> =_makeTripResult

    private val _confirmTripResult: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val confirmTripResult: StateFlow<NetworkState?> =_confirmTripResult

    private val _cancelBeforeCaptainResult: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val cancelBeforeCaptainResult: StateFlow<NetworkState?> =_cancelBeforeCaptainResult

    private val _getCaptainDetailsResult: MutableStateFlow<NetworkState?> = MutableStateFlow(null)
    val getCaptainDetailsResult: StateFlow<NetworkState?> =_getCaptainDetailsResult


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

}