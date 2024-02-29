package com.github.zottaa.note.create

import androidx.fragment.app.FragmentManager
import com.github.zottaa.core.Screen
import com.github.zottaa.note.details.NoteDetailsFragment

data class CreateNoteScreen(private val categoryId: Long) : Screen {
    override fun show(supportFragmentManager: FragmentManager, containerId: Int) {
        supportFragmentManager.beginTransaction()
            .add(
                containerId,
                CreateNoteFragment(categoryId)
            )
            .addToBackStack(CreateNoteFragment::class.java.name)
            .commit()
    }
}