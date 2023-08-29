package com.mosamir.atmodrivepassenger.futures.auth.di

import com.mosamir.atmodrivepassenger.futures.auth.data.data_source.remote.IAuthDataSource
import com.mosamir.atmodrivepassenger.futures.auth.data.repository.AuthRepo
import com.mosamir.atmodrivepassenger.futures.auth.domain.repository.IAuthRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Provides
    fun provideAuthRepo(iAuthDataSource: IAuthDataSource):IAuthRepo
            = AuthRepo(iAuthDataSource)


}