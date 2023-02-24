package com.dralsoft.inventory.core

import com.dralsoft.inventory.core.data.ErrorResponse

sealed class Resource<T>(val data: T? = null, val error: ErrorResponse? = null) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(error: ErrorResponse? = null) : Resource<T>(null, error)
}