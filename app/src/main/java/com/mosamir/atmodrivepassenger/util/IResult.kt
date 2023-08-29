package com.mosamir.atmodrivepassenger.util

sealed class IResult<out T> {

    data class Success<T>(val data: T) : IResult<T>()
    data class Fail<T>(val error: String?) : IResult<T>()

    companion object{

        fun <T> onSuccess(data:T) :IResult<T> = Success(data)
        fun <T> onFail(data:String) :IResult<T> = Fail(data)

    }

    fun isSuccessful():Boolean{
        return this is Success
    }
    fun isFailed():Boolean{
        return this is Fail
    }

}

fun  <T>IResult<T>.getData():T?{

    if (this is IResult.Success)
        return this.data

    return null
}
fun  <T>IResult<T>.getError():String?{

    if (this is IResult.Fail)
        return this.error

    return null
}
