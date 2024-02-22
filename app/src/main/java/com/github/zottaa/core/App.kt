package com.github.zottaa.core

import android.app.Application
import androidx.lifecycle.ViewModel

class App : Application(), ProvideViewModel {

    private lateinit var factory: ProvideViewModel.Factory
    private val clear = object : ClearViewModels {
        override fun clear(vararg viewModelClasses: Class<out ViewModel>) {
            factory.clear(*viewModelClasses)
        }
    }

    override fun onCreate() {
        val core = Core.Base(applicationContext)
        factory = ProvideViewModel.Factory(
            ProvideViewModel.Base(
                clear,
                core.notesDao(),
                core.foldersDao()
            )
        )
        super.onCreate()
    }

    override fun <T : ViewModel> viewModel(clasz: Class<T>): T =
        factory.viewModel(clasz)

}