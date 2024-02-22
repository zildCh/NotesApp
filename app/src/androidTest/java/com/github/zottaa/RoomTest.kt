package com.github.zottaa

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.zottaa.core.AppDataBase
import com.github.zottaa.core.NoteCache
import com.github.zottaa.core.NotesDao
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RoomTest {

    private lateinit var db: AppDataBase
    private lateinit var notesDao: NotesDao

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java)
            .allowMainThreadQueries()
            .build()
        notesDao = db.notesDao()
    }

    @After
    @Throws(IOException::class)
    fun clear() {
        db.close()
    }

    @Test
    fun test_notes() = runBlocking {
        notesDao.insert(note = NoteCache(id = 1L, title = "first note", text = "Yeee"))
        notesDao.insert(note = NoteCache(id = 2L, title = "second note", text = "Yeeee"))
        notesDao.insert(note = NoteCache(id = 3L, title = "third note", text = "yeeeeeeee"))
        notesDao.insert(note = NoteCache(id = 4L, title = "forth note", text = "yeeeeeeeeeee"))
        notesDao.insert(note = NoteCache(id = 5L, title = "fifth note", text = "Yeeeeeeeeeeeeeee"))

        notesDao.delete(noteId = 1L)
        notesDao.delete(noteId = 4L)

        notesDao.insert(note = NoteCache(id = 2L, title = "new name for note 2", text = "dasdasdadadad"))
        notesDao.insert(note = NoteCache(id = 5L, title = "new name for note 5", text = "dasdadadasd"))

        val expectedRenamedOne = NoteCache(id = 2L, title = "new name for note 2", text = "dasdasdadadad")
        val expectedRenamedTwo = NoteCache(id = 5L, title = "new name for note 5", text = "dasdadadasd")

        val actualRenamedOne = notesDao.note(noteId = 2L)
        val actualRenamedTwo = notesDao.note(noteId = 5L)

        assertEquals(expectedRenamedOne, actualRenamedOne)
        assertEquals(expectedRenamedTwo, actualRenamedTwo)
    }
}