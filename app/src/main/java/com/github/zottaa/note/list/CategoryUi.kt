package com.github.zottaa.note.list

interface CategoryUi {
    fun isValid(note: NoteUi): Boolean

    class Base(
        private  val id: Long,
        private  val name: String
    ) : CategoryUi {
        override fun isValid(note: NoteUi): Boolean = note.isCategoryIdTheSame(id)
    }

    object Empty : CategoryUi {
        override fun isValid(note: NoteUi): Boolean = true
    }
}