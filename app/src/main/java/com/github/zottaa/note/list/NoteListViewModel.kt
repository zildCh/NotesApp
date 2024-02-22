package com.github.zottaa.note.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.github.zottaa.main.Navigation
import com.github.zottaa.note.core.NoteLiveDataWrapper
import com.github.zottaa.note.core.NotesRepository
import com.github.zottaa.note.create.CreateNoteScreen
import com.github.zottaa.note.details.NoteDetailsScreen
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteListViewModel(
    private val repository: NotesRepository.ReadList,
    private val listLiveDataWrapper: ListLiveDataWrapper.Mutable,
    private val noteLiveDataWrapper: NoteLiveDataWrapper.Update,
    private val navigation: Navigation.Update,
    private val dispatcher: CoroutineDispatcher,
    private val dispatcherMain: CoroutineDispatcher
) : ViewModel(), ListLiveDataWrapper.Read  {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    fun init() {
        viewModelScope.launch(dispatcher) {
            val notes = repository.notes().map { it.toUi() }
            withContext(dispatcherMain) {
                listLiveDataWrapper.update(notes)
            }
        }
    }

    fun addNote() {
        navigation.update(CreateNoteScreen)
    }

    fun noteDetails(noteUi: NoteUi) {
        noteLiveDataWrapper.update(noteUi)
        navigation.update(NoteDetailsScreen)
    }

    override fun liveData(): LiveData<List<NoteUi>> =
        listLiveDataWrapper.liveData()

}