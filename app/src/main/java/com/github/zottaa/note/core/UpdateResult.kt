package com.github.zottaa.note.core

interface UpdateResult {
    fun message(): String

    fun isSuccess(): Boolean

    object Success : UpdateResult {
        override fun message(): String = "Note updated!"
        override fun isSuccess(): Boolean = true
    }

    data class Error(
        private val errorMessage: String
    ) : UpdateResult {
        override fun message(): String = errorMessage
        override fun isSuccess(): Boolean = false
    }
}