package ru.okcode.currencyconverter.util

import java.lang.Exception

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception): Result<Nothing>()
    data class Loading<out T>(val data: T? = null): Result<Nothing>()
}