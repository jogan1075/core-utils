
package com.klapcomercio.core_utils.utilities.base.binding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.klapcomercio.core_utils.utilities.base.getBinding

open class BindingActivity<VB : ViewBinding> : AppCompatActivity() {

    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (::binding.isInitialized.not()) {
            binding = getBinding()
            setContentView(binding.root)
        }
    }
}
