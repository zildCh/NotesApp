package com.github.zottaa

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.zottaa.core.AppDataBase
import com.github.zottaa.core.CategoryCache
import com.github.zottaa.core.CategoryDao
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
    private lateinit var categoryDao: CategoryDao

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java)
            .allowMainThreadQueries()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    val categoryNames = listOf(
                        context.getString(R.string.category_family),
                        context.getString(R.string.category_work),
                        context.getString(R.string.category_personal),
                        context.getString(R.string.category_sport),
                        context.getString(R.string.category_study))
                    for (i in categoryNames.indices) {
                        val name = categoryNames[i]
                        db.execSQL("INSERT INTO category (category_id, category_name) VALUES (?, ?)", arrayOf(i.toLong() + 1, name))
                    }
                }
            })
            .build()
        notesDao = db.notesDao()
        categoryDao = db.categoryDao()
    }

    @After
    @Throws(IOException::class)
    fun clear() {
        db.close()
    }

    @Test
    fun test_notes() = runBlocking {
        notesDao.insert(note = NoteCache(id = 1L, title = "first note", text = "Yeee", 0, 0))
        notesDao.insert(note = NoteCache(id = 2L, title = "second note", text = "Yeeee", 0, 0))
        notesDao.insert(note = NoteCache(id = 3L, title = "third note", text = "yeeeeeeee", 0, 0))
        notesDao.insert(note = NoteCache(id = 4L, title = "forth note", text = "yeeeeeeeeeee", 0, 0))
        notesDao.insert(note = NoteCache(id = 5L, title = "fifth note", text = "Yeeeeeeeeeeeeeee", 0, 0))

        notesDao.delete(noteId = 1L)
        notesDao.delete(noteId = 4L)

        notesDao.insert(note = NoteCache(id = 2L, title = "new name for note 2", text = "dasdasdadadad", 0, 0))
        notesDao.insert(note = NoteCache(id = 5L, title = "new name for note 5", text = "dasdadadasd", 0, 0))

        val expectedRenamedOne = NoteCache(id = 2L, title = "new name for note 2", text = "dasdasdadadad", 0, 0)
        val expectedRenamedTwo = NoteCache(id = 5L, title = "new name for note 5", text = "dasdadadasd", 0, 0)

        val actualRenamedOne = notesDao.note(noteId = 2L)
        val actualRenamedTwo = notesDao.note(noteId = 5L)

        assertEquals(expectedRenamedOne, actualRenamedOne)
        assertEquals(expectedRenamedTwo, actualRenamedTwo)
    }

    @Test
    fun test_categories() = runBlocking {
        val expectedOne = CategoryCache(1L, "Family")
        assertEquals(expectedOne, categoryDao.category(1))
        val expectedList = listOf(
            CategoryCache(1L, "Family"),
            CategoryCache(2L, "Work"),
            CategoryCache(3L, "Personal"),
            CategoryCache(4L, "Sport"),
            CategoryCache(5L, "Study")
        )
        assertEquals(expectedList, categoryDao.categories())
    }
}