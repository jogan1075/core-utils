package com.klapcomercio.core_utils.utilities.base.mvi


import com.klapcomercio.core_utils.utilities.base.flow.MutableEventFlow
import com.klapcomercio.core_utils.utilities.base.flow.asEventFlow
import com.klapcomercio.core_utils.utilities.base.mvvm.MvvmViewModel


abstract class MviViewModel<STATE, EVENT> : MvvmViewModel() {

    private val _stateFlow = MutableEventFlow<STATE>()
    val stateFlow = _stateFlow.asEventFlow()

    abstract fun onTriggerEvent(eventType: EVENT)

    protected fun setState(state: STATE) = safeLaunch {
        _stateFlow.emit(state)
    }

}
