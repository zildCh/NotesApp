package com.github.zottaa.authorization.registration

import androidx.lifecycle.ViewModel
import com.github.zottaa.authorization.login.LoginScreen
import com.github.zottaa.core.ClearViewModels
import com.github.zottaa.main.Navigation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val registrationRepository: RegistrationRepository,
    private val navigation: Navigation.Update,
    private val clear: ClearViewModels,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    fun registration(login: String, password: String) {
        viewModelScope.launch(dispatcher) {
            val registrationResult = registrationRepository.registration(login, password)
            println(registrationResult.message())
        }
    }

    fun comeback() {
        navigation.update(LoginScreen)
        clear.clear(this::class.java)
    }
}