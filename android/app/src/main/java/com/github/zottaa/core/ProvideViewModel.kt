package com.github.zottaa.core

import androidx.lifecycle.ViewModel
import com.github.zottaa.authorization.login.LoginRepository
import com.github.zottaa.authorization.login.LoginService
import com.github.zottaa.authorization.login.LoginViewModel
import com.github.zottaa.authorization.registration.RegistrationRepository
import com.github.zottaa.authorization.registration.RegistrationService
import com.github.zottaa.authorization.registration.RegistrationViewModel
import com.github.zottaa.main.MainViewModel
import com.github.zottaa.main.Navigation
import com.github.zottaa.note.core.CategoryRepository
import com.github.zottaa.note.core.NoteLiveDataWrapper
import com.github.zottaa.note.core.NotesRepository
import com.github.zottaa.note.core.NotesService
import com.github.zottaa.note.core.RemoteNotesRepository
import com.github.zottaa.note.create.CreateNoteViewModel
import com.github.zottaa.note.details.NoteDetailsViewModel
import com.github.zottaa.note.list.ListLiveDataWrapper
import com.github.zottaa.note.list.NoteListViewModel
import com.github.zottaa.note.list.SynchronizeRepository
import com.github.zottaa.note.list.SynchronizeService
//import com.github.zottaa.note.list.NoteListViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.create

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
        categoriesDao: CategoryDao,
        private val sharedPreferencesRepository: SharedPreferencesRepository,
        retrofit: Retrofit
    ) : ProvideViewModel {

        private val now = Now.Base()

        private val navigation = Navigation.Base()
        private val sharedNoteLiveDataWrapper = NoteLiveDataWrapper.Base()
        private val sharedNoteListLiveDataWrapper = ListLiveDataWrapper.Base()
        private val notesRepository = NotesRepository.Base(now, notesDao)
        private val categoryRepository = CategoryRepository.Base(categoriesDao)
        private val loginRepository =
            LoginRepository.Base(retrofit.create(LoginService::class.java))
        private val registrationRepository =
            RegistrationRepository.Base(retrofit.create(RegistrationService::class.java))
        private val synchronizeRepository =
            SynchronizeRepository.Base(retrofit.create(SynchronizeService::class.java))
        private val remoteNoteRepository =
            RemoteNotesRepository.Base(retrofit.create(NotesService::class.java))

        override fun <T : ViewModel> viewModel(clasz: Class<T>): T {
            return when (clasz) {
                MainViewModel::class.java -> MainViewModel(
                    navigation
                )

                NoteDetailsViewModel::class.java -> NoteDetailsViewModel(
                    sharedPreferencesRepository,
                    remoteNoteRepository,
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
                    sharedPreferencesRepository,
                    synchronizeRepository,
                    categoryRepository,
                    notesRepository,
                    sharedNoteListLiveDataWrapper,
                    sharedNoteLiveDataWrapper,
                    navigation,
                    Dispatchers.IO,
                    Dispatchers.Main
                )

                CreateNoteViewModel::class.java -> CreateNoteViewModel(
                    sharedPreferencesRepository,
                    remoteNoteRepository,
                    categoryRepository,
                    sharedNoteListLiveDataWrapper,
                    notesRepository,
                    navigation,
                    clear,
                    Dispatchers.IO,
                    Dispatchers.Main,
                    now
                )

                LoginViewModel::class.java -> LoginViewModel(
                    loginRepository,
                    notesRepository,
                    sharedPreferencesRepository,
                    navigation,
                    clear,
                    Dispatchers.IO,
                    Dispatchers.Main
                )

                RegistrationViewModel::class.java -> RegistrationViewModel(
                    registrationRepository,
                    navigation,
                    clear,
                    Dispatchers.IO
                )

                else -> {
                    throw IllegalStateException("unknown view model $clasz")
                }
            } as T
        }

    }
}