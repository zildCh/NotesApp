package com.github.zottaa.note.create

import androidx.lifecycle.ViewModel
import com.github.zottaa.core.ClearViewModels
import com.github.zottaa.main.Navigation
import com.github.zottaa.note.core.NotesRepository
import com.github.zottaa.note.list.ListLiveDataWrapper
import com.github.zottaa.note.list.NoteUi
import com.github.zottaa.note.list.NotesListScreen
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateNoteViewModel(
    private val addLiveDataWrapper: ListLiveDataWrapper.Create,
    private val repository: NotesRepository.Create,
    private val navigation: Navigation.Update,
    private val clear: ClearViewModels,
    private val dispatcher: CoroutineDispatcher,
    private val dispatcherMain: CoroutineDispatcher
) : ViewModel() {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    fun createNote(title: String) {
        viewModelScope.launch(dispatcher) {
            val id = repository.createNote(title)
            withContext(dispatcherMain) {
                addLiveDataWrapper.create(NoteUi(id, title, DEFAULT_TEXT))
                comeback()
            }
        }
    }

    fun comeback() {
        clear.clear(this::class.java)
        navigation.update(NotesListScreen)
    }

    companion object {
        private const val DEFAULT_TEXT = ""
    }
}