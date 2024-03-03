package com.github.zottaa.authorization.registration

import androidx.lifecycle.ViewModel
import com.github.zottaa.authorization.login.LoginScreen
import com.github.zottaa.core.ClearViewModels
import com.github.zottaa.main.Navigation
import kotlinx.coroutines.CoroutineDispatcher

class RegistrationViewModel(
    private val registrationRepository: RegistrationRepository,
    private val navigation: Navigation.Update,
    private val clear: ClearViewModels,
    private val dispatcher: CoroutineDispatcher,
    private val dispatcherMain: CoroutineDispatcher
) : ViewModel() {

    fun registration(login: String, password: String) {

    }

    fun comeback() {
        navigation.update(LoginScreen)
        clear.clear(this::class.java)
    }

}