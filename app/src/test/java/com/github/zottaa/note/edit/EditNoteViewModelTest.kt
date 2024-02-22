package com.github.zottaa.note.edit

import com.github.zottaa.note.DECREMENT_COUNT_LIVEDATA
import com.github.zottaa.note.FakeDecrementFoldersNoteCountLiveDataWrapper
import com.github.zottaa.note.FakeEditNoteRepository
import com.github.zottaa.note.FakeNoteListLiveDataWrapper
import com.github.zottaa.note.FakeNoteLiveDataWrapper
import com.github.zottaa.note.NOTES_LIVE_DATA_UPDATE
import com.github.zottaa.note.NOTE_LIVE_DATA
import com.github.zottaa.note.REPOSITORY_DELETE
import com.github.zottaa.note.REPOSITORY_NOTE
import com.github.zottaa.note.REPOSITORY_RENAME
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Test

class EditNoteViewModelTest {

    private lateinit var order: Order
    private lateinit var repository: FakeEditNoteRepository
    private lateinit var navigation: FakeNavigation.Update
    private lateinit var clear: FakeClear
    private lateinit var noteLiveDataWrapper: FakeNoteLiveDataWrapper
    private lateinit var noteListLiveDataWrapper: FakeNoteListLiveDataWrapper
    private lateinit var folderLiveDataWrapper: FakeDecrementFoldersNoteCountLiveDataWrapper
    private lateinit var viewModel: EditNoteViewModel

    @Before
    fun setup() {
        order = Order()
        repository = FakeEditNoteRepository.Base(order)
        clear = FakeClear.Base(order)
        navigation = FakeNavigation.Base(order)
        noteLiveDataWrapper = FakeNoteLiveDataWrapper.Base(order)
        folderLiveDataWrapper = FakeDecrementFoldersNoteCountLiveDataWrapper.Base(order)
        noteListLiveDataWrapper = FakeNoteListLiveDataWrapper.Base(order)
        viewModel = EditNoteViewModel(
            folderLiveDataWrapper = folderLiveDataWrapper,
            noteLiveDataWrapper = noteLiveDataWrapper,
            noteListLiveDataWrapper = noteListLiveDataWrapper,
            repository = repository,
            navigation = navigation,
            clear = clear,
            dispatcher = Dispatchers.Unconfined,
            dispatcherMain = Dispatchers.Unconfined
        )
    }

    @Test
    fun test_init() {
        viewModel.init(noteId = 32L)

        repository.checkNote(32L)
        noteLiveDataWrapper.check("first note")
        order.check(listOf(REPOSITORY_NOTE, NOTE_LIVE_DATA))
    }

    @Test
    fun test_delete() {
        viewModel.deleteNote(noteId = 32L)

        repository.checkDelete(32L)
        clear.check(listOf(EditNoteViewModel::class.java))
        navigation.checkScreen(FolderDetailsScreen)
        order.check(listOf(REPOSITORY_DELETE, DECREMENT_COUNT_LIVEDATA, CLEAR, NAVIGATE))
    }

    @Test
    fun test_rename() {
        viewModel.renameNote(noteId = 33L, newText = "a new text")

        repository.checkRename(33L, "a new text")
        noteListLiveDataWrapper.check(33L, "a new text")
        clear.check(listOf(EditNoteViewModel::class.java))
        navigation.checkScreen(FolderDetailsScreen)
        order.check(listOf(REPOSITORY_RENAME, NOTES_LIVE_DATA_UPDATE, CLEAR, NAVIGATE))
    }

    @Test
    fun test_comeback() {
        viewModel.comeback()

        clear.check(listOf(EditNoteViewModel::class.java))
        navigation.checkScreen(FolderDetailsScreen)
        order.check(listOf(CLEAR, NAVIGATE))
    }
}