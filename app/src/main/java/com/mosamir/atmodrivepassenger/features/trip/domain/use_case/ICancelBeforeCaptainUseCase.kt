package com.mosamir.atmodrivepassenger.features.trip.domain.use_case

import com.mosamir.atmodrivepassenger.features.trip.domain.model.CancelTripResponse
import com.mosamir.atmodrivepassenger.util.IResult

interface ICancelBeforeCaptainUseCase {

    suspend fun cancelBeforeCaptain(tripId: Int): IResult<CancelTripResponse>

}