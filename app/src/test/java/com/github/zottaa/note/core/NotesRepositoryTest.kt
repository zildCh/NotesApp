package com.github.zottaa.note.core

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

        repository.createNote(folderId = 1L, title = "first note")
        repository.createNote(folderId = 1L, title = "second note")
        repository.createNote(folderId = 2L, title = "third note")
        repository.createNote(folderId = 2L, title = "forth note")
        repository.createNote(folderId = 2L, title = "fifth note")

        val notesFirstFolderInitialActual = repository.noteList(folderId = 1L)
        val notesFirstFolderInitialExpected: List<MyNote> = listOf(
            MyNote(id = 15L, title = "first note", text = ""),
            MyNote(id = 16L, title = "second note", text = "")
        )
        assertEquals(notesFirstFolderInitialExpected, notesFirstFolderInitialActual)
        val notesSecondFolderInitialActual = repository.noteList(folderId = 2L)
        val notesSecondFolderInitialExpected: List<MyNote> = listOf(
            MyNote(id = 17L, title = "third note", text = ""),
            MyNote(id = 18L, title = "forth note", text = ""),
            MyNote(id = 19L, title = "fifth note", text = ""),
        )
        assertEquals(notesSecondFolderInitialExpected, notesSecondFolderInitialActual)

        repository.deleteNote(15L)
        repository.deleteNote(18L)

        repository.updateNote(16L, "new name for 2", "new text)))")
        repository.updateNote(19L, "new name for last one", "new text))")

        val expectedNote = MyNote(id = 19L, title = "new name for last one", text = "new text))")
        val actualNote: MyNote = repository.note(noteId = 19L)
        assertEquals(expectedNote, actualNote)
    }
}

interface FakeNotesDao : NotesDao {

    class Base : FakeNotesDao {

        private val set = HashSet<NoteCache>()

        override suspend fun notes(): List<NoteCache> {
            return set
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

        private var deleteCalledWithFolderId: Long = -1
    }
}