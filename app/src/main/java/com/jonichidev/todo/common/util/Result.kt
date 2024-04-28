package com.jonichidev.todo.common.util

sealed class Result<out T> {
    object Loading : Result<Nothing>()

    data class Error(val errorMessage: Int) : Result<Nothing>()

    data class Success<out T>(val data: T) : Result<T>()
}
