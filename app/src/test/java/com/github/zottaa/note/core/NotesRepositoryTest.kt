package com.github.zottaa.note.core

import com.github.zottaa.core.NoteCache
import com.github.zottaa.core.NotesDao
import com.github.zottaa.core.Now
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class NotesRepositoryTest {

    @Test
    fun test() = runBlocking {
        val now = FakeNow.Base(15L)
        val dao = FakeNotesDao.Base()
        val repository = NotesRepository.Base(
            now = now,
            dao = dao
        )

        repository.createNote(title = "first note", 0)
        repository.createNote(title = "second note", 0)
        repository.createNote(title = "third note", 0)

        val notesInitialActual = repository.notes()
        val notesInitialExpected: List<Note> = listOf(
            Note(id = 19L, title = "third note", text = "", 20L, 0),
            Note(id = 15L, title = "first note", text = "", 16L, 0),
            Note(id = 17L, title = "second note", text = "", 18L, 0),
        )
        assertEquals(notesInitialExpected, notesInitialActual)

        repository.deleteNote(15L)

        repository.updateNote(17L, "new name for 2", "new text)))", 0)
        repository.updateNote(19L, "new name for last one", "new text))", 0)

        val expectedNote = Note(id = 19L, title = "new name for last one", text = "new text))", 22L, 0)
        val actualNote: Note = repository.note(noteId = 19L)
        assertEquals(expectedNote, actualNote)
    }
}

interface FakeNotesDao : NotesDao {

    class Base : FakeNotesDao {

        private val set = HashSet<NoteCache>()

        override suspend fun notes(): List<NoteCache> {
            return set.toList()
        }

        override suspend fun note(noteId: Long): NoteCache {
            return set.find { it.id == noteId }!!
        }

        override suspend fun insert(note: NoteCache) {
            set.find { it.id == note.id }?.let {
                set.remove(it)
            }
            set.add(note)
        }

        override suspend fun delete(noteId: Long) {
            set.find { it.id == noteId }?.let {
                set.remove(it)
            }
        }

        override suspend fun notesByCategory(categoryId: Long): List<NoteCache> {
            TODO("Not yet implemented")
        }

        private var deleteCalledWithFolderId: Long = -1
    }
}

interface FakeNow : Now {

    class Base(private var time: Long) : FakeNow {

        override fun timeInMillis(): Long {
            return time++
        }
    }
}