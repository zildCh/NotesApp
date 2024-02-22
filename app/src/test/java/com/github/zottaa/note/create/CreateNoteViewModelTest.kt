package com.github.zottaa.note.create

import com.github.zottaa.note.CREATE_NOTE_REPOSITORY
import com.github.zottaa.note.FakeAddNoteLiveDataWrapper
import com.github.zottaa.note.FakeCreateNoteRepository
import com.github.zottaa.note.FakeIncrementFolderLiveDataWrapper
import com.github.zottaa.note.INCREMENT
import com.github.zottaa.note.NOTE_LIVEDATA_ADD
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Test

class CreateNoteViewModelTest {

    private lateinit var order: Order
    private lateinit var incrementFolder: FakeIncrementFolderLiveDataWrapper
    private lateinit var repository: FakeCreateNoteRepository
    private lateinit var addLiveDataWrapper: FakeAddNoteLiveDataWrapper
    private lateinit var navigation: FakeNavigation.Update
    private lateinit var clear: FakeClear
    private lateinit var viewModel: CreateNoteViewModel

    @Before
    fun setup() {
        order = Order()
        repository = FakeCreateNoteRepository.Base(order, 101)
        addLiveDataWrapper = FakeAddNoteLiveDataWrapper.Base(order)
        navigation = FakeNavigation.Base(order)
        clear = FakeClear.Base(order)
        incrementFolder = FakeIncrementFolderLiveDataWrapper.Base(order)
        viewModel = CreateNoteViewModel(
            folderLiveDataWrapper = incrementFolder,
            addLiveDataWrapper = addLiveDataWrapper,
            repository = repository,
            navigation = navigation,
            clear = clear,
            dispatcher = Dispatchers.Unconfined,
            dispatcherMain = Dispatchers.Unconfined
        )
    }

    @Test
    fun test_create() {
        viewModel.createNote(folderId = 4L, text = "new note text")

        repository.check(4L, "new note text")
        addLiveDataWrapper.check(NoteUi(id = 101, title = "new note text", text = ""))
        clear.check(listOf(CreateNoteViewModel::class.java))
        navigation.checkScreen(NotesListScreen)
        order.check(listOf(CREATE_NOTE_REPOSITORY, INCREMENT, NOTE_LIVEDATA_ADD, CLEAR, NAVIGATE))
    }

    @Test
    fun test_comeback() {
        viewModel.comeback()

        clear.check(listOf(CreateNoteViewModel::class.java))
        navigation.checkScreen(NotesListScreen)
        order.check(listOf(CLEAR, NAVIGATE))
    }

}