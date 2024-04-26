
package com.klapcomercio.core_utils.utilities.base.mvi

import androidx.viewbinding.ViewBinding
import com.klapcomercio.core_utils.utilities.base.mvvm.MvvmFragment
import com.klapcomercio.core_utils.utilities.base.observeFlowStart

abstract class MviFragment<VB : ViewBinding, STATE, VM : MviViewModel<STATE, *>> :
    MvvmFragment<VB, VM>() {

    abstract fun renderViewState(viewState: STATE)

    override fun observeUi() {
        super.observeUi()
        observeFlowStart(viewModel.stateFlow, ::renderViewState)
    }
}
