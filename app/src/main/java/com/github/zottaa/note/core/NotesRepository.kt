package com.github.zottaa.note.core

import com.github.zottaa.core.NoteCache
import com.github.zottaa.core.NotesDao
import com.github.zottaa.core.Now
import com.github.zottaa.note.list.NoteUi

interface NotesRepository {
    interface Create {
        suspend fun createNote(title: String, categoryId: Long): Long
    }

    interface ReadList {
        suspend fun notes(): List<Note>
    }

    interface CreateFromServer {
        suspend fun createNote(
            id: Long,
            title: String,
            text: String,
            updateTime: Long,
            categoryId: Long
        )

        suspend fun deleteAll()
    }

    interface Edit {
        suspend fun deleteNote(id: Long)
        suspend fun updateNote(id: Long, title: String = "", text: String = "", categoryId: Long)
        suspend fun note(noteId: Long): Note
    }

    interface Synchronize : CreateFromServer, ReadList

    interface All : Create, Edit, Synchronize

    class Base(
        private val now: Now,
        private val dao: NotesDao
    ) : All {
        override suspend fun createNote(title: String, categoryId: Long): Long {
            val id = now.timeInMillis()
            dao.insert(NoteCache(id, title, "", now.timeInMillis(), categoryId))
            return id
        }

        override suspend fun createNote(
            id: Long,
            title: String,
            text: String,
            updateTime: Long,
            categoryId: Long
        ) {
            dao.insert(NoteCache(id, title, text, updateTime, categoryId))
        }

        override suspend fun notes(): List<Note> =
            dao.notes().map { Note(it.id, it.title, it.text, it.updateTime, it.categoryId) }

        override suspend fun deleteAll() {
            dao.deleteAll()
        }


        override suspend fun deleteNote(id: Long) {
            dao.delete(id)
        }

        override suspend fun updateNote(id: Long, title: String, text: String, categoryId: Long) {
            val note = dao.note(id)
            val newNote = note.copy(
                title = title,
                text = text,
                updateTime = now.timeInMillis(),
                categoryId = categoryId
            )
            dao.insert(newNote)
        }

        override suspend fun note(noteId: Long): Note =
            dao.note(noteId).let { Note(it.id, it.title, it.text, it.updateTime, it.categoryId) }
    }
}

data class Note(
    private val id: Long,
    private val title: String,
    private val text: String,
    private val updateTime: Long,
    private val categoryId: Long
) {
    fun toUi() =
        NoteUi(id, title, text, updateTime, categoryId)

    fun categoryId() = categoryId

}