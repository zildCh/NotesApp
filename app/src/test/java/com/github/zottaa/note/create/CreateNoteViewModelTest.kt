package com.github.zottaa.note.create

import com.github.zottaa.core.FakeClear
import com.github.zottaa.core.FakeClear.Companion.CLEAR
import com.github.zottaa.core.FakeNavigation
import com.github.zottaa.core.FakeNavigation.Companion.NAVIGATE
import com.github.zottaa.core.Order
import com.github.zottaa.core.Screen
import com.github.zottaa.note.core.FakeNow
import com.github.zottaa.note.core.NotesRepository
import com.github.zottaa.note.list.ListLiveDataWrapper
import com.github.zottaa.note.list.NoteUi
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Test

class CreateNoteViewModelTest {

    private lateinit var order: Order
    private lateinit var repository: FakeCreateNoteRepository
    private lateinit var addLiveDataWrapper: FakeAddNoteLiveDataWrapper
    private lateinit var navigation: FakeNavigation.Update
    private lateinit var clear: FakeClear
    private lateinit var viewModel: CreateNoteViewModel
    private lateinit var now: FakeNow

    @Before
    fun setup() {
        order = Order()
        repository = FakeCreateNoteRepository.Base(order, 101)
        addLiveDataWrapper = FakeAddNoteLiveDataWrapper.Base(order)
        navigation = FakeNavigation.Base(order)
        clear = FakeClear.Base(order)
        now = FakeNow.Base(0)
        viewModel = CreateNoteViewModel(
            addLiveDataWrapper = addLiveDataWrapper,
            noteRepository = repository,
            navigation = navigation,
            clear = clear,
            dispatcher = Dispatchers.Unconfined,
            dispatcherMain = Dispatchers.Unconfined,
            now
        )
    }

    @Test
    fun test_create() {
        viewModel.createNote(title = "new note text", 0)

        repository.check("new note text", "")
        addLiveDataWrapper.check(NoteUi(id = 101, title = "new note text", text = "", 0, 0))
        clear.check(listOf(CreateNoteViewModel::class.java))
        navigation.checkScreen(Screen.Pop)
        order.check(listOf(CREATE_NOTE_REPOSITORY, NOTE_LIVEDATA_ADD, CLEAR, NAVIGATE))
    }

    @Test
    fun test_comeback() {
        viewModel.comeback()

        clear.check(listOf(CreateNoteViewModel::class.java))
        navigation.checkScreen(Screen.Pop)
        order.check(listOf(CLEAR, NAVIGATE))
    }
}

private const val NOTE_LIVEDATA_ADD = "NoteListLiveDataWrapper.Create#"
private const val CREATE_NOTE_REPOSITORY = "NotesRepository.Create#createNote"

private interface FakeAddNoteLiveDataWrapper : ListLiveDataWrapper.Create {

    fun check(expected: NoteUi)

    class Base(private val order: Order) : FakeAddNoteLiveDataWrapper {

        private lateinit var actual: NoteUi

        override fun check(expected: NoteUi) {
            assertEquals(expected, actual)
        }

        override fun create(noteUi: NoteUi) {
            actual = noteUi
            order.add(NOTE_LIVEDATA_ADD)
        }
    }
}

private interface FakeCreateNoteRepository : NotesRepository.Create {

    fun check(title: String, text: String)

    class Base(private val order: Order, private var noteId: Long) : FakeCreateNoteRepository {

        private var actualTitle = ""
        private var actualText = ""


        override fun check(title: String, text: String) {
            assertEquals(title, actualTitle)
            assertEquals(text, actualText)
        }

        override suspend fun createNote(title: String, categoryId: Long): Long {
            actualTitle = title
            actualText = ""
            order.add(CREATE_NOTE_REPOSITORY)
            return noteId++
        }
    }
}