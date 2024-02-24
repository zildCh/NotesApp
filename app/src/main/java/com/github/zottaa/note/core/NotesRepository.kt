package com.github.zottaa.note.core

import com.github.zottaa.core.NoteCache
import com.github.zottaa.core.NotesDao
import com.github.zottaa.core.Now
import com.github.zottaa.note.list.NoteUi

interface NotesRepository {
    interface Create {
        suspend fun createNote(title: String): Long
    }

    interface ReadList {
        suspend fun notes(): List<Note>
    }

    interface Edit {
        suspend fun deleteNote(id: Long)
        suspend fun updateNote(id: Long, title: String = "", text: String = "")
        suspend fun note(noteId: Long): Note
    }

    interface All : Create, ReadList, Edit

    class Base(
        private val now: Now,
        private val dao: NotesDao
    ) : All {
        override suspend fun createNote(title: String): Long {
            val id = now.timeInMillis()
            dao.insert(NoteCache(id, title, "", now.timeInMillis()))
            return id
        }

        override suspend fun notes(): List<Note> =
            dao.notes().map { Note(it.id, it.title, it.text, it.updateTime) }


        override suspend fun deleteNote(id: Long) {
            dao.delete(id)
        }

        override suspend fun updateNote(id: Long, title: String, text: String) {
            val note = dao.note(id)
            val newNote = note.copy(title = title, text = text, updateTime = now.timeInMillis())
            dao.insert(newNote)
        }

        override suspend fun note(noteId: Long): Note =
            dao.note(noteId).let { Note(it.id, it.title, it.text, it.updateTime) }
    }
}

data class Note(
    private val id: Long,
    private val title: String,
    private val text: String,
    private val updateTime: Long
) {
    fun toUi() =
        NoteUi(id, title, text, updateTime)

}