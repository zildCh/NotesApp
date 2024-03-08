package com.github.zottaa.note.core

interface CreateResult {
    fun isSuccess(): Boolean

    data class Success(
        private val noteId: Long
    ) : CreateResult {
        override fun isSuccess(): Boolean = true

        fun noteId(): Long = noteId
    }

    data class Error(
        private val errorMessage: String
    ) : CreateResult {
        override fun isSuccess(): Boolean = false

        fun errorMessage(): String = errorMessage
    }
}