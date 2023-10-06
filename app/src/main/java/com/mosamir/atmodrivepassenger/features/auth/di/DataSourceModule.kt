package com.mosamir.atmodrivepassenger.features.auth.di

import com.mosamir.atmodrivepassenger.features.auth.data.data_source.remote.AuthApiService
import com.mosamir.atmodrivepassenger.features.auth.data.data_source.remote.AuthDataSource
import com.mosamir.atmodrivepassenger.features.auth.data.data_source.remote.IAuthDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {



    @Provides
    fun getAuthDataSource(apiService: AuthApiService):IAuthDataSource = AuthDataSource(apiService)


}