package com.github.zottaa.authorization.login

import androidx.lifecycle.ViewModel
import com.github.zottaa.authorization.registration.RegistrationScreen
import com.github.zottaa.core.ClearViewModels
import com.github.zottaa.core.SharedPreferencesRepository
import com.github.zottaa.core.UserNotesResult
import com.github.zottaa.main.Navigation
import com.github.zottaa.note.core.NotesRepository
import com.github.zottaa.note.list.NotesListScreen
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val loginRepository: LoginRepository,
    private val notesRepository: NotesRepository.CreateFromServer,
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val navigation: Navigation.Update,
    private val clear: ClearViewModels,
    private val dispatcher: CoroutineDispatcher,
    private val dispatcherMain: CoroutineDispatcher
) : ViewModel() {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    fun init() {
        if (sharedPreferencesRepository.getUserId() != EMPTY)
            comeback()
    }

    fun login(login: String, password: String) {
        viewModelScope.launch(dispatcher) {
            val loginResult = loginRepository.login(login, password)
            if (loginResult.isSuccess()) {
                val list = (loginResult as UserNotesResult.Success).notes()
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
                sharedPreferencesRepository.setUserId(loginResult.userId())
            } else {
                println((loginResult as UserNotesResult.Error).message())
            }
            withContext(dispatcherMain) {
                if (loginResult.isSuccess())
                    comeback()
            }
        }
    }

    fun goToRegistration() {
        comeback(true)
    }

    private fun comeback(toRegistration: Boolean = false) {
        clear.clear(this::class.java)
        if (toRegistration)
            navigation.update(RegistrationScreen)
        else
            navigation.update(NotesListScreen)
    }

    companion object {
        private const val EMPTY = -1L
    }
}