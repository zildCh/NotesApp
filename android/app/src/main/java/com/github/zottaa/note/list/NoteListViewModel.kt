package com.github.zottaa.note.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.zottaa.authorization.login.LoginScreen
import com.github.zottaa.core.SharedPreferencesRepository
import com.github.zottaa.core.UserNotesResult
import com.github.zottaa.main.Navigation
import com.github.zottaa.note.core.CategoryRepository
import com.github.zottaa.note.core.NoteLiveDataWrapper
import com.github.zottaa.note.core.NotesRepository
import com.github.zottaa.note.create.CreateNoteScreen
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteListViewModel(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val synchronizeRepository: SynchronizeRepository,
    private val categoryRepository: CategoryRepository.All,
    private val notesRepository: NotesRepository.Synchronize,
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

    val uiStateLiveData: LiveData<UiState>
        get() = _uiStateLiveData
    private val _uiStateLiveData: MutableLiveData<UiState> = MutableLiveData()

    fun init(categoryId: Long) {
        _uiStateLiveData.value = UiState.Progress
        viewModelScope.launch(dispatcher) {
            val category =
                if (categoryId != 0L)
                    categoryRepository.category(categoryId).toUi()
                else CategoryUi.Empty

            val notes = notesRepository.notes().map { it.toUi() }.filter {
                category.isValid(it)
            }
            val categories = mutableListOf<CategoryUi>(CategoryUi.Empty)
            categories.addAll(categoryRepository.categories().map { it.toUi() })
            withContext(dispatcherMain) {
                listLiveDataWrapper.update(notes)
                _categoryLiveData.value = categories
                _uiStateLiveData.postValue(UiState.Main)
            }
        }
    }

    fun synchronize() {
        _uiStateLiveData.value = UiState.Progress
        viewModelScope.launch(dispatcher) {
            val synchronizeResult =
                synchronizeRepository.synchronize(sharedPreferencesRepository.getUserId())
            if (synchronizeResult.isSuccess()) {
                val list = (synchronizeResult as UserNotesResult.Success).notes()
                notesRepository.deleteAll()
                list.forEach {
                    notesRepository.createNote(
                        it.id,
                        it.title,
                        it.text,
                        it.updateTime,
                        it.categoryId
                    )
                }
            } else {
                println((synchronizeResult as UserNotesResult.Error).message())
            }
            withContext(dispatcherMain) {
                init(0L)
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

    fun logout() {
        viewModelScope.launch(dispatcher) {
            sharedPreferencesRepository.setUserId(-1L)
            withContext(dispatcherMain) {
                navigation.update(LoginScreen)
            }
        }
    }

    override fun liveData(): LiveData<List<NoteUi>> =
        listLiveDataWrapper.liveData()

}