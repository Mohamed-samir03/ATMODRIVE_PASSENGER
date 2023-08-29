package com.mosamir.atmodrivepassenger.futures.auth.di

import com.mosamir.atmodrivepassenger.futures.auth.domain.repository.IAuthRepo
import com.mosamir.atmodrivepassenger.futures.auth.domain.use_case.CheckCodeUseCase
import com.mosamir.atmodrivepassenger.futures.auth.domain.use_case.ICheckCodeUseCase
import com.mosamir.atmodrivepassenger.futures.auth.domain.use_case.IRegisterUseCase
import com.mosamir.atmodrivepassenger.futures.auth.domain.use_case.ISendCodeUseCase
import com.mosamir.atmodrivepassenger.futures.auth.domain.use_case.RegisterUseCase
import com.mosamir.atmodrivepassenger.futures.auth.domain.use_case.SendCodeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object UdeCaseModule {


    @Provides
    fun provideSendCodeUseCase(iAuthRepo: IAuthRepo):ISendCodeUseCase = SendCodeUseCase(iAuthRepo)

    @Provides
    fun provideCheckCodeUseCase(iAuthRepo: IAuthRepo):ICheckCodeUseCase = CheckCodeUseCase(iAuthRepo)

    @Provides
    fun provideRegisterUseCase(iAuthRepo: IAuthRepo):IRegisterUseCase = RegisterUseCase(iAuthRepo)

}