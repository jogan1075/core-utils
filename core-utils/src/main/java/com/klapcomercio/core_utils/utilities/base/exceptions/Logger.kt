package com.klapcomercio.core_utils.utilities.base.exceptions

import android.os.Build
import timber.log.Timber
import java.util.regex.Pattern

object Logger {

    private const val CALL_STACK_INDEX = 4
    private val ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$")
    private const val MAX_TAG_LENGTH = 23

    init {
//        if (Deb) {
            Timber.plant(Timber.DebugTree())
//        }
    }

    fun verbose(message: String, throwable: Throwable? = null, tag: String? = null) {
        Timber.tag(
            tag
                ?: createStackElementTag(Thread.currentThread().stackTrace[CALL_STACK_INDEX])
        )
        Timber.v(throwable, message)
    }

    fun info(message: String, throwable: Throwable? = null, tag: String? = null) {
        Timber.tag(
            tag
                ?: createStackElementTag(Thread.currentThread().stackTrace[CALL_STACK_INDEX])
        )
        Timber.i(throwable, message)
    }

    fun debug(message: String, throwable: Throwable? = null, tag: String? = null) {
        Timber.tag(
            tag
                ?: createStackElementTag(Thread.currentThread().stackTrace[CALL_STACK_INDEX])
        )
        Timber.d(throwable, message)
    }

    fun warning(message: String, throwable: Throwable? = null, tag: String? = null) {
        Timber.tag(
            tag
                ?: createStackElementTag(Thread.currentThread().stackTrace[CALL_STACK_INDEX])
        )
        Timber.w(throwable, message)
    }

    fun error(message: String?, throwable: Throwable? = null, tag: String? = null) {
        Timber.tag(
            tag
                ?: createStackElementTag(Thread.currentThread().stackTrace[CALL_STACK_INDEX])
        )
        Timber.e(throwable, message)
    }

    private fun createStackElementTag(element: StackTraceElement): String {
        var tag = element.className
        val m = ANONYMOUS_CLASS.matcher(tag)
        if (m.find()) {
            tag = m.replaceAll("")
        }
        val hasDollar = tag.indexOf("$") != -1
        tag = if (hasDollar) {
            tag.substring(tag.lastIndexOf('.') + 1, tag.indexOf("$"))
        } else {
            tag.substring(tag.lastIndexOf('.') + 1)
        }
        // Tag length limit was removed in API 24.
        return if (tag.length <= MAX_TAG_LENGTH || Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tag
        } else tag.substring(0, MAX_TAG_LENGTH)
    }
}