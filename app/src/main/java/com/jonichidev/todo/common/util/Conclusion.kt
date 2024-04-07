package com.jonichidev.todo.common.util

sealed class Conclusion<out T> {
    object Loading : Conclusion<Nothing>()
    data class Error(val errorMessage: Int) : Conclusion<Nothing>()
    data class Success<out T>(val data: T) : Conclusion<T>()
}