package com.github.zottaa.note.details

import androidx.lifecycle.ViewModel
import com.github.zottaa.core.ClearViewModels
import com.github.zottaa.main.Navigation
import com.github.zottaa.note.core.NoteLiveDataWrapper
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

class NoteDetailsViewModel(
    private val noteLiveDataWrapper: NoteLiveDataWrapper.Mutable,
    private val noteListLiveDataWrapper: ListLiveDataWrapper.Update,
    private val repository: NotesRepository.Edit,
    private val navigation: Navigation.Update,
    private val clear: ClearViewModels,
    private val dispatcher: CoroutineDispatcher,
    private val dispatcherMain: CoroutineDispatcher
) : ViewModel() {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    fun init(noteId: Long) {
        viewModelScope.launch(dispatcher) {
            val note = repository.note(noteId)
            withContext(dispatcherMain) {
                noteLiveDataWrapper.update(note.toUi())
            }
        }
    }

    // TODO: Maybe fix later
    fun deleteNote(noteId: Long) {
        viewModelScope.launch(dispatcher) {
            repository.deleteNote(noteId)
            withContext(dispatcherMain) {
                comeback()
            }
        }
    }

    fun updateNote(noteId: Long, newTitle: String, newText: String) {
        viewModelScope.launch(dispatcher) {
            repository.updateNote(noteId, newTitle, newText)
            withContext(dispatcherMain) {
                noteListLiveDataWrapper.update(noteId, newTitle, newText)
                comeback()
            }
        }
    }

    fun comeback() {
        clear.clear(this.javaClass)
        navigation.update(NotesListScreen)
    }
}