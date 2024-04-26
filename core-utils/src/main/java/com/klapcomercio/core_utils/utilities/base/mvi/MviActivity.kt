package com.klapcomercio.core_utils.utilities.base.mvi

import androidx.viewbinding.ViewBinding
import com.klapcomercio.core_utils.utilities.base.mvvm.MvvmActivity
import com.klapcomercio.core_utils.utilities.base.observeFlowStart

abstract class MviActivity<VB : ViewBinding, STATE, VM : MviViewModel<STATE, *>> :
    MvvmActivity<VB, VM>() {

    abstract fun renderViewState(viewState: STATE)

    override fun observeUi() {
        super.observeUi()
        observeFlowStart(viewModel.stateFlow, ::renderViewState)
    }
}
