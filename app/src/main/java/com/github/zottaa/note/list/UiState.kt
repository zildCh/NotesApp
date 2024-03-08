package com.github.zottaa.note.list

import android.view.View
import android.widget.ProgressBar

interface UiState {

    fun show(progress: ProgressBar)

    object Progress : UiState {
        override fun show(progress: ProgressBar) {
            progress.visibility = View.VISIBLE
        }
    }

    object Main : UiState {
        override fun show(progress: ProgressBar) {
            progress.visibility = View.GONE
        }
    }
}