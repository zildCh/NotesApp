package com.github.zottaa.core

import androidx.lifecycle.ViewModel
import com.github.zottaa.main.MainViewModel
import com.github.zottaa.main.Navigation
import com.github.zottaa.note.core.CategoryRepository
import com.github.zottaa.note.core.NoteLiveDataWrapper
import com.github.zottaa.note.core.NotesRepository
import com.github.zottaa.note.create.CreateNoteViewModel
import com.github.zottaa.note.details.NoteDetailsViewModel
import com.github.zottaa.note.list.ListLiveDataWrapper
import com.github.zottaa.note.list.NoteListViewModel
import kotlinx.coroutines.Dispatchers

interface ProvideViewModel {
    fun <T : ViewModel> viewModel(clasz: Class<T>): T

    class Factory(
        private val provideViewModel: ProvideViewModel
    ) : ClearViewModels, ProvideViewModel {

        private val cache = mutableMapOf<Class<out ViewModel>, ViewModel>()
        override fun clear(vararg viewModelClasses: Class<out ViewModel>) {
            viewModelClasses.forEach {
                cache.remove(it)
            }
        }

        override fun <T : ViewModel> viewModel(clasz: Class<T>): T {
            if (!cache.containsKey(clasz)) {
                cache[clasz] = provideViewModel.viewModel(clasz)

            }
            return cache[clasz] as T
        }
    }

    class Base(
        private val clear: ClearViewModels,
        notesDao: NotesDao,
        categoriesDao: CategoryDao
    ) : ProvideViewModel {

        private val now = Now.Base()

        private val navigation = Navigation.Base()
        private val sharedNoteLiveDataWrapper = NoteLiveDataWrapper.Base()
        private val sharedNoteListLiveDataWrapper = ListLiveDataWrapper.Base()
        private val notesRepository = NotesRepository.Base(now, notesDao)
        private val categoryRepository = CategoryRepository.Base(categoriesDao)

        override fun <T : ViewModel> viewModel(clasz: Class<T>): T {
            return when (clasz) {
                MainViewModel::class.java -> MainViewModel(
                    navigation
                )

                NoteDetailsViewModel::class.java -> NoteDetailsViewModel(
                    categoryRepository,
                    sharedNoteLiveDataWrapper,
                    sharedNoteListLiveDataWrapper,
                    notesRepository,
                    navigation,
                    clear,
                    Dispatchers.IO,
                    Dispatchers.Main,
                    now
                )

                NoteListViewModel::class.java -> NoteListViewModel(
                    categoryRepository,
                    notesRepository,
                    sharedNoteListLiveDataWrapper,
                    sharedNoteLiveDataWrapper,
                    navigation,
                    Dispatchers.IO,
                    Dispatchers.Main
                )

                CreateNoteViewModel::class.java -> CreateNoteViewModel(
                    categoryRepository,
                    sharedNoteListLiveDataWrapper,
                    notesRepository,
                    navigation,
                    clear,
                    Dispatchers.IO,
                    Dispatchers.Main,
                    now
                )

                else -> {
                    throw IllegalStateException("unknown view model $clasz")
                }
            } as T
        }

    }
}