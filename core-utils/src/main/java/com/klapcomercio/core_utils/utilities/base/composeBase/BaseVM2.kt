package com.klapcomercio.core_utils.utilities.base.composeBase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.klapcomercio.core_utils.utilities.base.flow.MutableEventFlow
import com.klapcomercio.core_utils.utilities.base.flow.asEventFlow
import com.klapcomercio.core_utils.utilities.base.mvvm.MvvmViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseVM2<Event, UiState> : ViewModel() {

    private val _stateFlow = MutableEventFlow<UiState>()
    val state = _stateFlow.asEventFlow()

    abstract fun onTriggerEvent(eventType: Event)

    protected fun setState(state: UiState) = safeLaunch {
        _stateFlow.emit(state)
    }

    private fun safeLaunch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(handler, block = block)
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        Timber.tag(MvvmViewModel.COROUTINE_EXCEPTION_HANDLER_MESSAGE).e(exception.toString())
    }

    companion object {
        private const val COROUTINE_EXCEPTION_HANDLER_MESSAGE = "MVVM-ExceptionHandler"
    }
}