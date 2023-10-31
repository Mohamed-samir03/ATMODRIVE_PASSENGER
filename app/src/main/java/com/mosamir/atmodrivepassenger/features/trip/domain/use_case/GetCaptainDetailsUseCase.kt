package com.mosamir.atmodrivepassenger.features.trip.domain.use_case

import com.mosamir.atmodrivepassenger.features.trip.domain.model.captain.CaptainDetailsResponse
import com.mosamir.atmodrivepassenger.features.trip.domain.repository.ITripRepo
import com.mosamir.atmodrivepassenger.util.IResult
import javax.inject.Inject

class GetCaptainDetailsUseCase @Inject constructor(
    private val iTripRepo: ITripRepo
) :IGetCaptainDetailsUseCase{
    override suspend fun getCaptainDetails(tripId: Int): IResult<CaptainDetailsResponse> {
        return iTripRepo.getCaptainDetails(tripId)
    }

}