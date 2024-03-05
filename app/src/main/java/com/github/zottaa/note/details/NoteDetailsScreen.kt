package com.github.zottaa.note.details

import androidx.fragment.app.FragmentManager
import com.github.zottaa.core.Screen

data class NoteDetailsScreen(
    private val noteId: Long,
    private val currentCategoryId: Long
) : Screen {
    override fun show(supportFragmentManager: FragmentManager, containerId: Int) {
        supportFragmentManager.beginTransaction()
            .add(
                containerId,
                NoteDetailsFragment(noteId, currentCategoryId)
            )
            .addToBackStack(NoteDetailsFragment::class.java.name)
            .commit()
    }
}