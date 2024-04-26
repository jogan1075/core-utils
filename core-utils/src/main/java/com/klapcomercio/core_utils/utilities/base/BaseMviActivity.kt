package com.klapcomercio.core_utils.utilities.base

import android.app.ProgressDialog
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.klapcomercio.core_utils.utilities.base.mvi.MviActivity
import com.klapcomercio.core_utils.utilities.base.mvi.MviViewModel
import timber.log.Timber


abstract class BaseMviActivity<VB : ViewBinding, STATE, VM : MviViewModel<STATE, *>> :
    MviActivity<VB, STATE, VM>() {

    var progressDialog: ProgressDialog? = null

    var isFragmentLoaded = false
    var menuFragment: Fragment? = null
    lateinit var menuButton: Button

    override fun showProgress() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(this)
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


   /* fun hideMenuFragment(fragmentManager: FragmentManager) {
//        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragmentTransaction= fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
        fragmentTransaction.remove(menuFragment!!)
        fragmentTransaction.commit()
        menuButton.setBackgroundResource(R.drawable.ic_baseline_menu_24)
        isFragmentLoaded = false
    }*/

    /*
    private fun loadMenuFragment() {
        val fm = supportFragmentManager
        menuFragment = fm.findFragmentById(R.id.container)
        menuButton.setBackgroundResource(R.drawable.ic_baseline_menu_24)
        if (menuFragment == null) {
            menuFragment = MenuFragment()
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
            fragmentTransaction.add(R.id.container, menuFragment as MenuFragment)
            fragmentTransaction.commit()
        }
        isFragmentLoaded = true
    }
    */

}