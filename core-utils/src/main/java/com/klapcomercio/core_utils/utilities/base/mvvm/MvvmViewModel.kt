package com.klapcomercio.core_utils.utilities.base.mvvm


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.klapcomercio.core_utils.network.models.DataState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber

open class MvvmViewModel : ViewModel() {

    private val _progress = MutableStateFlow<Boolean?>(null)
    val progress get() = _progress.asStateFlow()

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> get() = _error

    private val handler = CoroutineExceptionHandler { _, exception ->
        Timber.tag(COROUTINE_EXCEPTION_HANDLER_MESSAGE).e(exception)
    }

    protected fun safeLaunch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(handler, block = block)
    }

    open fun showProgress() {
        _progress.value = true
    }

    open fun hideProgress() {
        _progress.value = false
    }

    open fun passError(throwable: Throwable, showSystemError: Boolean = true) {
        if (showSystemError) {
            _error.value = throwable
        }
    }

    protected suspend fun <T> call(
        callFlow: Flow<T>,
        completionHandler: (collect: T) -> Unit = {}
    ) {
        callFlow
            .catch { passError(throwable = it) }
            .collect {
                completionHandler.invoke(it)
            }
    }

    protected suspend fun <T> callWithProgress(
        callFlow: Flow<T>,
        completionHandler: (collect: T) -> Unit= {}
    ) {
        callFlow
            .onStart { showProgress() }
            .onCompletion { hideProgress() }
            .catch { passError(throwable = it) }
            .collect {
                completionHandler.invoke(it)
            }
    }

    protected suspend fun <T> execute(
        callFlow: Flow<DataState<T>>,
        completionHandler: (collect: T) -> Unit= {}
    ) {
        callFlow
            .catch { passError(throwable = it) }
            .collect { state ->
                when (state) {
                    is DataState.Error -> Unit
                    is DataState.Success -> {
                        completionHandler.invoke(state.result)
                    }
                }
            }
    }

    protected suspend fun <T> executeWithProgress(
        callFlow: Flow<DataState<T>>,
        completionHandler: (collect: T) -> Unit= {}
    ) {
        callFlow
            .onStart { showProgress() }
            .onCompletion { hideProgress() }
            .catch { passError(throwable = it) }
            .collect { state ->
                when (state) {
                    is DataState.Error -> Unit
                    is DataState.Success -> {
                        completionHandler.invoke(state.result)
                    }
                }
            }
    }
    companion object {
        const val COROUTINE_EXCEPTION_HANDLER_MESSAGE = "MVVM-ExceptionHandler"
    }
}
