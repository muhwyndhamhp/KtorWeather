package com.github.muhwyndhamhp.ktorweather.utils

import android.content.SharedPreferences
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import java.io.IOException


inline fun <reified T> SharedPreferences.saveData(key: String, data: T) {
    val moshi = Moshi.Builder()
        .build()

    val adapter: JsonAdapter<T> = moshi.adapter(T::class.java)
    val jsonString = adapter.toJson(data)

    edit().putString(key, jsonString).apply()
}

inline fun <reified T> SharedPreferences.loadData(key: String, defaultValue: T? = null): T? {
    val jsonString = getString(key, null)
    if (jsonString != null) {
        val moshi = Moshi.Builder()
            .build()

        val adapter: JsonAdapter<T> = moshi.adapter(T::class.java)
        try {
            return adapter.fromJson(jsonString)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    return defaultValue
}