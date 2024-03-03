package com.github.zottaa.authorization.login

import androidx.lifecycle.ViewModel
import com.github.zottaa.core.ClearViewModels
import com.github.zottaa.core.SharedPreferencesRepository
import com.github.zottaa.main.Navigation
import kotlinx.coroutines.CoroutineDispatcher

class LoginViewModel(
    private val loginRepository: LoginRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val navigation: Navigation.Update,
    private val clear: ClearViewModels,
    private val dispatcher: CoroutineDispatcher,
    private val dispatcherMain: CoroutineDispatcher
) : ViewModel() {

    fun init() {

        comeback()
    }

    fun login(login: String, password: String) {

    }

    fun goToRegistration() {
        comeback(true)
    }

    fun comeback(toRegistration: Boolean = false) {
        clear.clear(this::class.java)
    }
}