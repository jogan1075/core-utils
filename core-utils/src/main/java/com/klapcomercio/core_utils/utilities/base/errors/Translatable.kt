package com.klapcomercio.core_utils.utilities.base.errors

import android.content.res.Resources

fun interface Translatable {
    fun getStringResource(resources: Resources) : String
}