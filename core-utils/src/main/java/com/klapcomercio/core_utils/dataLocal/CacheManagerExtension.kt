package com.klapcomercio.core_utils.dataLocal

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.time.LocalDate

/**
 * @param fileName Name of the Shared Preferences
 * @return SharedPreferences
 */
const val PREFS = "PREFS"
const val DAY_TODAY = "DAY_TODAY"
fun Context.getPrefs(fileName: String? = null): SharedPreferences {
    val masterKey = MasterKey.Builder(this)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()


    val name = if (fileName.isNullOrEmpty()) {
        getDefaultSharedPrefName()
    } else {
        fileName.toString()
    }

    return EncryptedSharedPreferences.create(
        this,
        name,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
}

/**
 * @return Default SharedPreferences filename
 */
fun Context.getDefaultSharedPrefName(): String {
    return this.packageName + "_pref"
}

fun saveInCache(context: Context, key: String, value: String) {
    CacheManager(context, PREFS).write(key, value)
}

fun saveInCache(context: Context, key: String, value: Int) {
    CacheManager(context, PREFS).write(key, value)
}

fun saveInCache(context: Context, key: String, value: Boolean) {
    CacheManager(context, PREFS).write(key, value)
}

fun saveInCache(context: Context, key: String, value: Long) {
    CacheManager(context, PREFS).write(key, value)
}

fun getStringPref(context: Context, key: String): String {
    return CacheManager(context, PREFS).read(key, "")
}

fun getLongPref(context: Context, key: String): Long {
    return CacheManager(context, PREFS).read(key, 0)
}

fun getBooleanPref(context: Context, key: String): Boolean {
    return CacheManager(context, PREFS).read(key, false)
}

fun getIntPref(context: Context, key: String): Int {
    return CacheManager(context, PREFS).read(key, 0)
}


fun clearPreference(context: Context, key: String) {
    CacheManager(context, PREFS).clear(key)
}

@RequiresApi(Build.VERSION_CODES.O)
fun saveDateInCache(context: Context, key: String) {
    val dateToday =
        "${LocalDate.now().year}-${LocalDate.now().monthValue}-${LocalDate.now().dayOfMonth}"
    CacheManager(context, DAY_TODAY).write(key, dateToday)
}

fun getDateInCache(context: Context, key: String): String {
    return CacheManager(context, DAY_TODAY).read(key, "")
}

@RequiresApi(Build.VERSION_CODES.O)
fun isTodayValid(value: String): Boolean {
    val (year, month, day) = value.split("-")
    val actualDay = LocalDate.now()
    val otherDate = LocalDate.of(year.toInt(), month.toInt(), day.toInt())
    val comparacion = actualDay.compareTo(otherDate)

    return when {
        comparacion == 0 -> true
        comparacion < 0 -> false
        else -> false
    }

}
