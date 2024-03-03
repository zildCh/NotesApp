package com.github.zottaa.core

interface SharedPreferencesRepository {
    fun setUserId(value: Long)

    fun getUserId(): Long
}