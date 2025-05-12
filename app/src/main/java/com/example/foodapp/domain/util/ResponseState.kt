package com.example.foodapp.domain.util

sealed class ResponseState<out T> {
    data class Success<out T>(val data: T): ResponseState<T>()
    data class Error<out T>(val message: String): ResponseState<T>()
    class Loading<out T>: ResponseState<T>()
}