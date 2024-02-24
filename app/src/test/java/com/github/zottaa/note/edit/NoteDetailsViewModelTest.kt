package com.github.zottaa.note.edit

import androidx.lifecycle.LiveData
import com.github.zottaa.core.FakeClear
import com.github.zottaa.core.FakeClear.Companion.CLEAR
import com.github.zottaa.core.FakeNavigation
import com.github.zottaa.core.FakeNavigation.Companion.NAVIGATE
import com.github.zottaa.core.Order
import com.github.zottaa.core.Screen
import com.github.zottaa.note.core.FakeNow
import com.github.zottaa.note.core.Note
import com.github.zottaa.note.core.NoteLiveDataWrapper
import com.github.zottaa.note.core.NotesRepository
import com.github.zottaa.note.details.NoteDetailsViewModel
import com.github.zottaa.note.list.ListLiveDataWrapper
import com.github.zottaa.note.list.NoteUi
import com.github.zottaa.note.list.NotesListScreen
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Test

class NoteDetailsViewModelTest {

    private lateinit var order: Order
    private lateinit var repository: FakeEditNoteRepository
    private lateinit var navigation: FakeNavigation.Update
    private lateinit var clear: FakeClear
    private lateinit var noteLiveDataWrapper: FakeNoteLiveDataWrapper
    private lateinit var noteListLiveDataWrapper: FakeNoteListLiveDataWrapper
    private lateinit var viewModel: NoteDetailsViewModel
    private lateinit var now: FakeNow

    @Before
    fun setup() {
        order = Order()
        repository = FakeEditNoteRepository.Base(order)
        clear = FakeClear.Base(order)
        navigation = FakeNavigation.Base(order)
        noteLiveDataWrapper = FakeNoteLiveDataWrapper.Base(order)
        noteListLiveDataWrapper = FakeNoteListLiveDataWrapper.Base(order)
        now = FakeNow.Base(0)
        viewModel = NoteDetailsViewModel(
            noteLiveDataWrapper = noteLiveDataWrapper,
            noteListLiveDataWrapper = noteListLiveDataWrapper,
            repository = repository,
            navigation = navigation,
            clear = clear,
            dispatcher = Dispatchers.Unconfined,
            dispatcherMain = Dispatchers.Unconfined,
            now
        )
    }

    @Test
    fun test_init() {
        viewModel.init(noteId = 32L)

        repository.checkNote(32L)
        noteLiveDataWrapper.check(NoteUi(32L, "first note", "First", 32L))
        order.check(listOf(REPOSITORY_NOTE, NOTE_LIVE_DATA))
    }

    @Test
    fun test_delete() {
        viewModel.deleteNote(noteId = 32L)

        repository.checkDelete(32L)
        clear.check(listOf(NoteDetailsViewModel::class.java))
        navigation.checkScreen(Screen.Pop)
        order.check(listOf(REPOSITORY_DELETE, CLEAR, NAVIGATE))
    }

    @Test
    fun test_update() {
        viewModel.updateNote(noteId = 33L, newTitle = "a new title", newText = "a new text")

        repository.checkUpdate(33L, "a new title", "a new text")
        noteListLiveDataWrapper.check(33L, "a new title", "a new text")
        clear.check(listOf(NoteDetailsViewModel::class.java))
        navigation.checkScreen(Screen.Pop)
        order.check(listOf(REPOSITORY_UPDATE, NOTES_LIVE_DATA_UPDATE, CLEAR, NAVIGATE))
    }

    @Test
    fun test_comeback() {
        viewModel.comeback()

        clear.check(listOf(NoteDetailsViewModel::class.java))
        navigation.checkScreen(Screen.Pop)
        order.check(listOf(CLEAR, NAVIGATE))
    }
}

private const val NOTE_LIVE_DATA = "NoteLiveDataWrapper#UPDATE"
private const val REPOSITORY_DELETE = "NotesRepository.Edit#DELETE"
private const val REPOSITORY_UPDATE = "NotesRepository.Edit#RENAME"
private const val REPOSITORY_NOTE = "NotesRepository.Edit#note"
private const val NOTES_LIVE_DATA_UPDATE = "NoteListLiveDataWrapper.Update#update"

private interface FakeNoteLiveDataWrapper : NoteLiveDataWrapper.Mutable {

    fun check(expected: NoteUi)

    class Base(private val order: Order) : FakeNoteLiveDataWrapper {

        private var actual = NoteUi(0, "", "", 0)

        override fun check(expected: NoteUi) {
            assertEquals(expected, actual)
        }

        override fun update(noteUi: NoteUi) {
            actual = noteUi
            order.add(NOTE_LIVE_DATA)
        }

        override fun liveData(): LiveData<NoteUi> {
            throw IllegalStateException("not used here")
        }
    }
}

private interface FakeEditNoteRepository : NotesRepository.Edit {

    fun checkNote(expectedNoteId: Long)

    fun checkDelete(expectedNoteId: Long)

    fun checkUpdate(expectedNoteId: Long,
                    expectedNewTitle: String,
                    expectedNewText: String)

    class Base(private val order: Order) : FakeEditNoteRepository {

        private var actualId = -1L
        private var actualName = ""
        private var actualText = ""

        override fun checkDelete(expectedNoteId: Long) {
            assertEquals(expectedNoteId, actualId)
        }

        override fun checkUpdate(
            expectedNoteId: Long,
            expectedNewTitle: String,
            expectedNewText: String
        ) {
            assertEquals(expectedNoteId, actualId)
            assertEquals(expectedNewTitle, actualName)
            assertEquals(expectedNewText, actualText)
        }

        override fun checkNote(expectedNoteId: Long) {
            assertEquals(expectedNoteId, actualId)
        }

        override suspend fun deleteNote(id: Long) {
            actualId = id
            order.add(REPOSITORY_DELETE)
        }

        override suspend fun updateNote(id: Long, title: String, text: String) {
            actualId = id
            actualName = title
            actualText = text
            order.add(REPOSITORY_UPDATE)
        }

        override suspend fun note(noteId: Long): Note {
            actualId = noteId
            order.add(REPOSITORY_NOTE)
            return Note(id = noteId, title = "first note", text = "First", updateTime = noteId)
        }
    }
}

private interface FakeNoteListLiveDataWrapper : ListLiveDataWrapper.Edit {

    fun check(expectedNoteId: Long, expectedNewTitle: String, expectedNewText: String)

    class Base(private val order: Order) : FakeNoteListLiveDataWrapper {

        private var actualTitle: String = ""
        private var actualId: Long = -1
        private var actualText: String = ""

        override fun check(
            expectedNoteId: Long,
            expectedNewTitle: String,
            expectedNewText: String
        ) {
            assertEquals(expectedNoteId, actualId)
            assertEquals(expectedNewTitle, actualTitle)
            assertEquals(expectedNewText, actualText)
        }

        override fun update(noteId: Long, newTitle: String, newText: String, updateTime: Long) {
            actualId = noteId
            actualTitle = newTitle
            actualText = newText
            order.add(NOTES_LIVE_DATA_UPDATE)
        }

        override fun delete(noteId: Long) {

        }
    }
}