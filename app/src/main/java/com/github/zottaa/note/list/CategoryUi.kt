package com.github.zottaa.note.list

interface CategoryUi {
    fun isValid(note: NoteUi): Boolean

    fun id(): Long

    data class Base(
        val id: Long,
        private val name: String
    ) : CategoryUi {
        override fun isValid(note: NoteUi): Boolean = note.isCategoryIdTheSame(id)
        override fun id(): Long = id

        override fun toString(): String {
            return name
        }
    }

    object Empty : CategoryUi {
        override fun isValid(note: NoteUi): Boolean = true
        override fun id(): Long = 0L

        override fun toString(): String {
            return ""
        }
    }
}
