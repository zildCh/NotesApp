package com.github.zottaa.note.list

import androidx.lifecycle.LiveData
import com.github.zottaa.core.FakeNavigation
import com.github.zottaa.core.FakeNavigation.Companion.NAVIGATE
import com.github.zottaa.core.Order
import com.github.zottaa.note.core.Note
import com.github.zottaa.note.core.NoteLiveDataWrapper
import com.github.zottaa.note.core.NotesRepository
import com.github.zottaa.note.create.CreateNoteScreen
import com.github.zottaa.note.details.NoteDetailsScreen
import com.github.zottaa.note.list.FakeLiveDataWrapper.Companion.UPDATE
import com.github.zottaa.note.list.FakeRepository.Companion.NOTES
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Test

class NoteListViewModelTest {
    private lateinit var order: Order
    private lateinit var navigation: FakeNavigation.Update
    private lateinit var repository: FakeRepository
    private lateinit var noteLiveDataWrapper: FakeNoteUpdateLiveDataWrapper
    private lateinit var liveDataWrapper: FakeLiveDataWrapper
    private lateinit var viewModel: NoteListViewModel

    @Before
    fun setup() {
        order = Order()
        repository = FakeRepository.Base(order)
        liveDataWrapper = FakeLiveDataWrapper.Base(order)
        noteLiveDataWrapper = FakeNoteUpdateLiveDataWrapper.Base(order)
        navigation = FakeNavigation.Base(order)
        viewModel = NoteListViewModel(
            repository = repository,
            listLiveDataWrapper = liveDataWrapper,
            noteLiveDataWrapper = noteLiveDataWrapper,
            navigation = navigation,
            dispatcher = Dispatchers.Unconfined,
            dispatcherMain = Dispatchers.Unconfined
        )
    }

    @Test
    fun test_init() {
        repository.expectList(
            listOf(
                Note(id = 1L, title = "first note", text = "First"),
                Note(id = 2L, title = "second note", text = "Second"),
            )
        )

        viewModel.init()
        liveDataWrapper.checkList(
            listOf(
                NoteUi(id = 1L, title = "first note", text = "First"),
                NoteUi(id = 2L, title = "second note", text = "Second"),
            )
        )
        order.check(listOf(NOTES, UPDATE))
    }

    @Test
    fun test_add() {
        viewModel.addNote()

        navigation.checkScreen(CreateNoteScreen)
        order.check(listOf(NAVIGATE))
    }

    @Test
    fun test_details() {
        repository.expectList(
            listOf(
                Note(id = 1L, title = "first note", text = "First"),
                Note(id = 2L, title = "second note", text = "Second"),
            )
        )

        viewModel.init()
        liveDataWrapper.checkList(
            listOf(
                NoteUi(id = 1L, title = "first note", text = "First"),
                NoteUi(id = 2L, title = "second note", text = "Second"),
            )
        )
        order.check(listOf(NOTES, UPDATE))

        viewModel.noteDetails(
            noteUi = NoteUi(
                id = 1L,
                title = "first note",
                text = "First"
            )
        )
        noteLiveDataWrapper.check(
            NoteUi(
                id = 1L,
                title = "first note",
                text = "First"
            )
        )
        navigation.checkScreen(NoteDetailsScreen)
        order.check(listOf(NOTES, UPDATE, UPDATE_NOTE_LIVEDATA, NAVIGATE))
    }
}

private interface FakeLiveDataWrapper : ListLiveDataWrapper.Mutable {

    companion object {
        const val UPDATE = "NoteListLiveDataWrapper#update"
    }

    fun checkList(expected: List<NoteUi>)

    class Base(private val order: Order) : FakeLiveDataWrapper {

        private val actual = mutableListOf<NoteUi>()

        override fun checkList(expected: List<NoteUi>) {
            assertEquals(expected, actual)
        }

        override fun update(list: List<NoteUi>) {
            actual.clear()
            actual.addAll(list)
            order.add(UPDATE)
        }

        override fun update(noteId: Long, newTitle: String, newText: String) {
            val note = actual.find {
                it.isIdTheSame(noteId)
            }
        }

        override fun liveData(): LiveData<List<NoteUi>> {
            throw IllegalStateException("Not used here")
        }
    }
}

private interface FakeRepository : NotesRepository.ReadList {

    fun expectList(folders: List<Note>)

    companion object {
        const val NOTES = "NotesRepository#notes"
    }

    class Base(private val order: Order) : FakeRepository {

        private val list = mutableListOf<Note>()

        override fun expectList(notes: List<Note>) {
            list.clear()
            list.addAll(notes)
        }

        override suspend fun notes(): List<Note> {
            order.add(NOTES)
            return list
        }
    }
}

private interface FakeNoteUpdateLiveDataWrapper : NoteLiveDataWrapper.Update {

    fun check(expected: NoteUi)

    class Base(private val order: Order) : FakeNoteUpdateLiveDataWrapper {

        private lateinit var actual: NoteUi

        override fun check(expected: NoteUi) {
            assertEquals(expected, actual)
        }

        override fun update(note: NoteUi) {
            order.add(UPDATE_NOTE_LIVEDATA)
            actual = note
        }
    }
}

private const val UPDATE_NOTE_LIVEDATA = "NoteLiveDataWrapper.Update#update"
