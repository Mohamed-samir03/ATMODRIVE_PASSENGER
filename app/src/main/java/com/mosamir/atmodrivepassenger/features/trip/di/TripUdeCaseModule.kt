package com.mosamir.atmodrivepassenger.features.trip.di

import com.mosamir.atmodrivepassenger.features.trip.domain.repository.ITripRepo
import com.mosamir.atmodrivepassenger.features.trip.domain.use_case.CancelBeforeCaptainUseCase
import com.mosamir.atmodrivepassenger.features.trip.domain.use_case.ConfirmTripUseCase
import com.mosamir.atmodrivepassenger.features.trip.domain.use_case.ICancelBeforeCaptainUseCase
import com.mosamir.atmodrivepassenger.features.trip.domain.use_case.IConfirmTripUseCase
import com.mosamir.atmodrivepassenger.features.trip.domain.use_case.IMakeTripUseCase
import com.mosamir.atmodrivepassenger.features.trip.domain.use_case.MakeTripUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object TripUdeCaseModule {


    @Provides
    fun provideMakeTripUseCase(iTripRepo: ITripRepo):IMakeTripUseCase
            = MakeTripUseCase(iTripRepo)

    @Provides
    fun provideConfirmTripUseCase(iTripRepo: ITripRepo):IConfirmTripUseCase
            = ConfirmTripUseCase(iTripRepo)

    @Provides
    fun provideCancelBeforeCaptainUseCase(iTripRepo: ITripRepo):ICancelBeforeCaptainUseCase
            = CancelBeforeCaptainUseCase(iTripRepo)


}