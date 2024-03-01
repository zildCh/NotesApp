package com.github.zottaa.note.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.zottaa.main.Navigation
import com.github.zottaa.note.core.CategoryRepository
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
    private val categoryRepository: CategoryRepository.All,
    private val repository: NotesRepository.ReadList,
    private val listLiveDataWrapper: ListLiveDataWrapper.Mutable,
    private val noteLiveDataWrapper: NoteLiveDataWrapper.Update,
    private val navigation: Navigation.Update,
    private val dispatcher: CoroutineDispatcher,
    private val dispatcherMain: CoroutineDispatcher
) : ViewModel(), ListLiveDataWrapper.Read {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    val categoryLiveData: LiveData<List<CategoryUi>>
        get() = _categoryLiveData
    private val _categoryLiveData: MutableLiveData<List<CategoryUi>> = MutableLiveData()

    fun init(categoryId: Long) {
        viewModelScope.launch(dispatcher) {
            val category = categoryRepository.category(categoryId).toUi()
            val notes = repository.notes().map { it.toUi() }.filter {
                category.isValid(it)
            }
            val categories = categoryRepository.categories().map { it.toUi() }
            withContext(dispatcherMain) {
                listLiveDataWrapper.update(notes)
                _categoryLiveData.value = categories
            }
        }
    }

    fun addNote(categoryId: Long) {
        navigation.update(CreateNoteScreen(categoryId))
    }

    fun noteDetails(noteUi: NoteUi, categoryId: Long) {
        noteLiveDataWrapper.update(noteUi)
        navigation.update(noteUi.detailsScreen(categoryId))
    }

    override fun liveData(): LiveData<List<NoteUi>> =
        listLiveDataWrapper.liveData()

}