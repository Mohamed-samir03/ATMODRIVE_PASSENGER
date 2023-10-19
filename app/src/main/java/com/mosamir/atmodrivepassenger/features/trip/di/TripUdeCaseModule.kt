package com.mosamir.atmodrivepassenger.features.trip.di

import com.mosamir.atmodrivepassenger.features.trip.domain.repository.ITripRepo
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
    fun provideUpdateAvailabilityUseCase(iTripRepo: ITripRepo):IMakeTripUseCase
            = MakeTripUseCase(iTripRepo)


}