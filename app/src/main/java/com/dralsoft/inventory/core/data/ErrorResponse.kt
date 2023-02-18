package com.dralsoft.inventory.core.data

import androidx.annotation.Keep

@Keep
data class ErrorData(
    val `data`: Any,
    val error: Error?,
    @Transient
    var errorType: ErrorType?
) {
    @Keep
    data class Error(
        val status: Int,
        var name: String,
        val message: String,
        val details: Details
    ) {
        @Keep
        class Details
    }
}

data class ErrorResponse(
    val statusCode: Int? = 0,
    val responseMessage: String? = null,
    val errorBodyJson: String? = null,
    val errorData: ErrorData? = null

) {
    val message: String
        get() = (errorData?.errorType?.message) ?: ErrorType.Default.message
}


sealed class ErrorType(val message: String) {
    object Default : ErrorType("Ocurrió un error")
    object NotFound : ErrorType("No se pudieron obtener datos")
    object UserExist : ErrorType("Un usuario con ese email ya existe")
    object ValidationError : ErrorType("Campos erroneos")
}