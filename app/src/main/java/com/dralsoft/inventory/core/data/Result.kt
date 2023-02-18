package com.dralsoft.inventory.core.data

sealed class Result<out T> {
    data class Success<out S>(val data: S) : Result<S>()
    data class Error(val errorResponse: ErrorResponse) : Result<Nothing>()
}
