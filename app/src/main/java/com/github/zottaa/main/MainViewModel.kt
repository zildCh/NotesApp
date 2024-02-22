package com.github.zottaa.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.github.zottaa.core.Screen

class MainViewModel(
    private val navigation: Navigation.Mutable
) : ViewModel(), Navigation.Read {
    fun init(firstRun: Boolean) {
        if (firstRun)
            navigation.update(NotesListScreen)
    }

    override fun liveData(): LiveData<Screen> = navigation.liveData()
}