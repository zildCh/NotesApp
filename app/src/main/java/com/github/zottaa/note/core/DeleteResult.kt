package com.github.zottaa.note.core

interface DeleteResult {
    fun message(): String

    fun isSuccess(): Boolean

    object Success : DeleteResult {
        override fun message(): String = "Note deleted!"
        override fun isSuccess(): Boolean = true
    }

    data class Error(
        private val errorMessage: String
    ) : DeleteResult {
        override fun message(): String = errorMessage
        override fun isSuccess(): Boolean = false
    }
}