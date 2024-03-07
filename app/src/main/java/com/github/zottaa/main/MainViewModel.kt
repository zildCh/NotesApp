package com.github.zottaa.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.github.zottaa.authorization.login.LoginScreen
import com.github.zottaa.core.Screen
import com.github.zottaa.note.list.NotesListScreen

class MainViewModel(
    private val navigation: Navigation.Mutable
) : ViewModel(), Navigation.Read {
    fun init(firstRun: Boolean) {
        if (firstRun)
            navigation.update(LoginScreen)
    }

    override fun liveData(): LiveData<Screen> = navigation.liveData()
}