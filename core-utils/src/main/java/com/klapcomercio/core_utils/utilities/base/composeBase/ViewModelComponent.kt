package com.klapcomercio.core_utils.utilities.base.composeBase

interface ViewState {
    val isLoading: Boolean
    fun getErrorMessage(): String? = null
    fun clearErrors(): ViewState? = null
}

interface ViewEvent

interface ViewSideEffect