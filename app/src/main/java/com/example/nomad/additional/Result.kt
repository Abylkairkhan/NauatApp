package com.example.nomad.additional

sealed class Result {
    data class Success<out T>(val data: T) : Result()
    data class Failure<out T>(val error: T) : Result()
}
