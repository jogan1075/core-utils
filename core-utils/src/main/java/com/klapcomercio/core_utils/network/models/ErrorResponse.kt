package com.klapcomercio.core_utils.network.models

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

    @SerializedName("message")
    val message: String?,
    @SerializedName("status_code")
    val status: Int?,
    @SerializedName("uri")
    val url : String)