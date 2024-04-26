package com.klapcomercio.core_utils.network.models

sealed class Resource<T>(
    val data: T?, val message: String?,
    val errorResponse: com.klapcomercio.core_utils.network.models.ErrorResponse?
) {
    class Success<T>(data: T) : Resource<T>(data, null, null)
    class Error<T>(message: String) : Resource<T>(null, message, null)
    class ErrorResponse<T>(errorResponse: com.klapcomercio.core_utils.network.models.ErrorResponse?) :
        Resource<T>(null, null, errorResponse)
}