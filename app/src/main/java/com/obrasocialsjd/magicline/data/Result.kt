package com.obrasocialsjd.magicline.data

sealed class Result<out T> {
    data class Success<out T>(val data: T?) : Result<T>()
    data class Failure<out T>(val throwable: Throwable) : Result<T>()
}