package com.mosamir.atmodrivepassenger.features.trip.domain.use_case

import com.mosamir.atmodrivepassenger.features.trip.domain.model.CancelTripResponse
import com.mosamir.atmodrivepassenger.features.trip.domain.repository.ITripRepo
import com.mosamir.atmodrivepassenger.util.IResult
import javax.inject.Inject

class CancelBeforeCaptainUseCase @Inject constructor(
    private val iTripRepo: ITripRepo
):ICancelBeforeCaptainUseCase {
    override suspend fun cancelBeforeCaptain(tripId: Int): IResult<CancelTripResponse> {
        return iTripRepo.cancelBeforeCaptain(tripId)
    }
}