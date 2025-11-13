package com.example.pokeapp.data.repository


sealed class ResultWrapper<out T> {

    data class Success<out T>(val data: T) : ResultWrapper<T>()


    data class Error(val message: String) : ResultWrapper<Nothing>()
}