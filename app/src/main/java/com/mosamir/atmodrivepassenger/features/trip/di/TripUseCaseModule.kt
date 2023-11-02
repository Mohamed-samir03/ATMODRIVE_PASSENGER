package com.mosamir.atmodrivepassenger.features.trip.di

import com.mosamir.atmodrivepassenger.features.trip.domain.repository.ITripRepo
import com.mosamir.atmodrivepassenger.features.trip.domain.use_case.CancelBeforeCaptainUseCase
import com.mosamir.atmodrivepassenger.features.trip.domain.use_case.CancelTripUseCase
import com.mosamir.atmodrivepassenger.features.trip.domain.use_case.ConfirmTripUseCase
import com.mosamir.atmodrivepassenger.features.trip.domain.use_case.GetCaptainDetailsUseCase
import com.mosamir.atmodrivepassenger.features.trip.domain.use_case.GetTripDetailsUseCase
import com.mosamir.atmodrivepassenger.features.trip.domain.use_case.ICancelBeforeCaptainUseCase
import com.mosamir.atmodrivepassenger.features.trip.domain.use_case.ICancelTripUseCase
import com.mosamir.atmodrivepassenger.features.trip.domain.use_case.IConfirmTripUseCase
import com.mosamir.atmodrivepassenger.features.trip.domain.use_case.IGetCaptainDetailsUseCase
import com.mosamir.atmodrivepassenger.features.trip.domain.use_case.IGetTripDetailsUseCase
import com.mosamir.atmodrivepassenger.features.trip.domain.use_case.IMakeTripUseCase
import com.mosamir.atmodrivepassenger.features.trip.domain.use_case.IOnTripUseCase
import com.mosamir.atmodrivepassenger.features.trip.domain.use_case.MakeTripUseCase
import com.mosamir.atmodrivepassenger.features.trip.domain.use_case.OnTripUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object TripUseCaseModule {


    @Provides
    fun provideMakeTripUseCase(iTripRepo: ITripRepo):IMakeTripUseCase
            = MakeTripUseCase(iTripRepo)

    @Provides
    fun provideConfirmTripUseCase(iTripRepo: ITripRepo):IConfirmTripUseCase
            = ConfirmTripUseCase(iTripRepo)

    @Provides
    fun provideCancelBeforeCaptainUseCase(iTripRepo: ITripRepo):ICancelBeforeCaptainUseCase
            = CancelBeforeCaptainUseCase(iTripRepo)

    @Provides
    fun provideCaptainDetailsUseCase(iTripRepo: ITripRepo):IGetCaptainDetailsUseCase
            = GetCaptainDetailsUseCase(iTripRepo)

    @Provides
    fun provideOnTripUseCase(iTripRepo: ITripRepo):IOnTripUseCase
            = OnTripUseCase(iTripRepo)

    @Provides
    fun provideCancelTripUseCase(iTripRepo: ITripRepo):ICancelTripUseCase
            = CancelTripUseCase(iTripRepo)

    @Provides
    fun provideTripDetailsUseCase(iTripRepo: ITripRepo):IGetTripDetailsUseCase
            = GetTripDetailsUseCase(iTripRepo)


}