package com.github.zottaa.core

import com.github.zottaa.authorization.login.ServerUser

interface UserNotesResult {

    fun isSuccess(): Boolean

    data class Success(private val user: ServerUser) :
        UserNotesResult {
        override fun isSuccess(): Boolean = true

        fun notes(): List<NoteCache> {
            val list = mutableListOf<NoteCache>()
            user.list.forEach { serverUserCategoryLink ->
                serverUserCategoryLink.noteList.forEach {
                    list.add(NoteCache(
                        it.id,
                        it.title,
                        it.text,
                        it.updateTime,
                        serverUserCategoryLink.category.id
                    ))
                }
            }
            return list
        }

        fun userId(): Long = user.id
    }

    data class Error(
        private val noConnection: Boolean,
    ) : UserNotesResult {
        override fun isSuccess(): Boolean = false

        fun message(): String =
            if (noConnection) {
                "No internet connection"
            } else {
                "User not found"
            }
    }
}