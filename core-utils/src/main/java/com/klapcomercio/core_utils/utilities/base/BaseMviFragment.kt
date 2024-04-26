package com.klapcomercio.core_utils.utilities.base

import android.app.ProgressDialog
import androidx.viewbinding.ViewBinding
import com.klapcomercio.core_utils.utilities.base.mvi.MviFragment
import com.klapcomercio.core_utils.utilities.base.mvi.MviViewModel
import timber.log.Timber

abstract class BaseMviFragment<VB : ViewBinding, STATE, VM : MviViewModel<STATE, *>> :
    MviFragment<VB, STATE, VM>() {

    private var progressDialog: ProgressDialog? = null

    override fun showProgress() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(requireContext())
        }
        progressDialog?.show()
    }

    override fun hideProgress() {
        progressDialog?.dismiss()
    }

    override fun showError(throwable: Throwable) {
        handleErrorMessage(throwable.message.toString())
    }

    protected open fun handleErrorMessage(message: String?) {
        if (message.isNullOrBlank()) return
        hideProgress()
        Timber.e(message)
    }
}