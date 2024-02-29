package com.github.zottaa.note.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.zottaa.core.ClearViewModels
import com.github.zottaa.core.Now
import com.github.zottaa.core.Screen
import com.github.zottaa.main.Navigation
import com.github.zottaa.note.core.CategoryRepository
import com.github.zottaa.note.core.NotesRepository
import com.github.zottaa.note.list.CategoryUi
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
    private val categoryRepository: CategoryRepository.All,
    private val addLiveDataWrapper: ListLiveDataWrapper.Create,
    private val repository: NotesRepository.Create,
    private val navigation: Navigation.Update,
    private val clear: ClearViewModels,
    private val dispatcher: CoroutineDispatcher,
    private val dispatcherMain: CoroutineDispatcher,
    private val now: Now
) : ViewModel() {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    val categoryLiveData: LiveData<List<CategoryUi>>
        get() = _categoryLiveData
    private val _categoryLiveData: MutableLiveData<List<CategoryUi>> = MutableLiveData()

    fun init() {
        viewModelScope.launch(dispatcherMain) {
            _categoryLiveData.value = categoryRepository.categories().map { it.toUi() }
        }
    }

    fun createNote(title: String, categoryId: Long, currentCategoryId: Long) {
        viewModelScope.launch(dispatcher) {
            val id = repository.createNote(title, categoryId)
            withContext(dispatcherMain) {
                if (categoryId == currentCategoryId || currentCategoryId == NO_CATEGORY)
                    addLiveDataWrapper.create(
                        NoteUi(
                            id,
                            title,
                            DEFAULT_TEXT,
                            now.timeInMillis(),
                            categoryId
                        )
                    )
                comeback()
            }
        }
    }

    fun comeback() {
        clear.clear(this::class.java)
        navigation.update(Screen.Pop)
    }

    companion object {
        private const val DEFAULT_TEXT = ""
        private const val NO_CATEGORY = 1L
    }
}