package com.github.zottaa.core

import android.content.Context

interface SharedPreferencesRepository {
    fun setUserId(value: Long)

    fun getUserId(): Long

    class Base(
        private val context: Context
    ) : SharedPreferencesRepository {
        private val sharedPreferences by lazy {
            context.getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
        }
        override fun setUserId(value: Long) {
            sharedPreferences.edit().putLong(userIdKey, value).apply()
        }

        override fun getUserId(): Long =
            sharedPreferences.getLong(userIdKey, -1)

        companion object {
            private const val sharedPreferencesKey = "sharedPreferencesKey"
            private const val userIdKey = "userIdKey"
        }
    }
}