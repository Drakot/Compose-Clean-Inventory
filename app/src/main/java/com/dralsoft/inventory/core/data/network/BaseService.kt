package com.dralsoft.inventory.core.data.network

import android.util.Log
import com.dralsoft.inventory.core.Resource
import com.dralsoft.inventory.core.data.ErrorData
import com.dralsoft.inventory.core.data.ErrorResponse
import com.dralsoft.inventory.core.data.ErrorType
import com.google.gson.Gson
import retrofit2.Response

abstract class BaseService {


    protected suspend fun <T : Any> apiCall(call: suspend () -> Response<T>): Resource<T> {
        val response: Response<T>
        try {

            response = call.invoke()

            return if (!response.isSuccessful) {
                val errorResponse = mapErrorResponse(response)
                if (errorResponse.statusCode == 401) {
                    //  instance.logout()
                }
                Log.w("ApiService", errorResponse.message)
                Resource.Error(errorResponse)
            } else {
                if (response.body() == null) {
                    Resource.Error(mapErrorResponse(response))
                } else {
                    Resource.Success(response.body()!!)
                }
            }
        } catch (t: Throwable) {
            Log.w("ApiService", t.message.toString())
            return Resource.Error(ErrorResponse(responseMessage = t.message))
        }
    }

    private fun <T> mapErrorResponse(response: Response<T>): ErrorResponse {
        val errorBody = response.errorBody()?.string()

        val errorData = try {
            val parsedData = Gson().fromJson(errorBody, ErrorData::class.java)

            if (parsedData.error?.name == "ApplicationError" &&
                parsedData.error.message == "Email or Username are already taken"
            ) {
                parsedData.error.name = "UserExist"
            }

            parsedData?.errorType = when (parsedData?.error?.name) {
                "NotFound", "NotFoundError" -> ErrorType.NotFound
                "ValidationError" -> ErrorType.ValidationError
                "UserExist" -> ErrorType.UserExist
                else -> {
                    ErrorType.Default
                }
            }

            parsedData
        } catch (e: java.lang.Exception) {
            null
        }

        return ErrorResponse(response.code(), response.message(), errorBody ?: "", errorData)
    }
}