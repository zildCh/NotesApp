package com.github.zottaa.main

import com.github.zottaa.core.FakeNavigation
import com.github.zottaa.core.Order
import com.github.zottaa.note.list.NotesListScreen
import org.junit.Test


class MainViewModelTest {

    @Test
    fun test() {
        val order = Order()
        val navigation: FakeNavigation.Mutable = FakeNavigation.Base(order)
        val viewModel = MainViewModel(
            navigation = navigation
        )

        viewModel.init(firstRun = true)
        navigation.checkScreen(NotesListScreen)
    }
}