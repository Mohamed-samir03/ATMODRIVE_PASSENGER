package com.mosamir.atmodrivepassenger.features.trip.domain.use_case

import com.mosamir.atmodrivepassenger.features.trip.domain.model.captain.CaptainDetailsResponse
import com.mosamir.atmodrivepassenger.util.IResult

interface IGetCaptainDetailsUseCase {

    suspend fun getCaptainDetails(tripId: Int): IResult<CaptainDetailsResponse>

}