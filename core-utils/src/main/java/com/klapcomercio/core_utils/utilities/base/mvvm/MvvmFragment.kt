package com.klapcomercio.core_utils.utilities.base.mvvm

import androidx.viewbinding.ViewBinding
import com.klapcomercio.core_utils.utilities.base.coreBase.CoreFragment
import com.klapcomercio.core_utils.utilities.base.observeFlowStart
import com.klapcomercio.core_utils.utilities.base.observeLiveData

abstract class MvvmFragment<VB : ViewBinding, VM : MvvmViewModel> : CoreFragment<VB>() {

    abstract val viewModel: VM

    open fun showProgress() {}

    open fun hideProgress() {}

    open fun showError(throwable: Throwable) {}

    override fun observeUi() {
        super.observeUi()
        observeProgress()
        observeError()
    }

    private fun observeProgress() {
        observeFlowStart(viewModel.progress) { state ->
            state?.let {
                if (it) {
                    showProgress()
                } else {
                    hideProgress()
                }
            }
        }
    }

    private fun observeError() {
        observeLiveData(viewModel.error, ::showError)
    }
}
