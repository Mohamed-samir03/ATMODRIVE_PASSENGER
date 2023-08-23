package com.mosamir.atmodrivepassenger.util

sealed class IResult<out T> {

    object Loading : IResult<Nothing>()
    data class Success<T>(val data: T) : IResult<T>()
    data class Fail<T>(val error: String?) : IResult<T>()

    companion object{

        fun <T> onSuccess(data:T) :IResult<T> = Success(data)
        fun <T> onFail(data:String) :IResult<T> = Fail(data)

    }

    fun <T>IResult.Success<T>.getData():T{
        return this.data
    }

}
