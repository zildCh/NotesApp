package com.github.zottaa.core

import androidx.lifecycle.ViewModel
import com.github.zottaa.main.MainViewModel
import com.github.zottaa.main.Navigation

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
    ) : ProvideViewModel {

        private val now = Now.Base()

        private val navigation = Navigation.Base()
        private val sharedNoteLiveDataWrapper = NoteLiveDataWrapper.Base()
        private val sharedNoteListLiveDataWrapper = NoteListLiveDataWrapper.Base()
        private val notesRepository = NotesRepository.Base(now, notesDao)

        override fun <T : ViewModel> viewModel(clasz: Class<T>): T {
            return when (clasz) {
                MainViewModel::class.java -> MainViewModel(
                    navigation
                )

                EditNoteViewModel::class.java -> EditNoteViewModel(
                    sharedFolderLiveDataWrapper,
                    sharedNoteLiveDataWrapper,
                    sharedNoteListLiveDataWrapper,
                    notesRepository,
                    navigation,
                    clear,
                    Dispatchers.IO,
                    Dispatchers.Main
                )

                CreateNoteViewModel::class.java -> CreateNoteViewModel(
                    sharedFolderLiveDataWrapper,
                    sharedNoteListLiveDataWrapper,
                    notesRepository,
                    navigation,
                    clear,
                    Dispatchers.IO,
                    Dispatchers.Main
                )

                else -> {
                    throw IllegalStateException("unknown view model $clasz")
                }
            } as T
        }

    }
}