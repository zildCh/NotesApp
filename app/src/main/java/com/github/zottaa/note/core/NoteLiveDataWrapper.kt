package com.github.zottaa.note.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.zottaa.note.list.NoteUi

interface NoteLiveDataWrapper {
    interface Update {
        fun update(noteUi: NoteUi)
    }

    interface Read {
        fun liveData(): LiveData<NoteUi>
    }

    interface Change {
        fun updateNote(newName: String, newText: String)
    }

    interface Mutable : Update, Read

    interface All : Mutable, Change

    class Base(
        private val liveData: MutableLiveData<NoteUi> = MutableLiveData()
    ) : All {
        override fun update(noteUi: NoteUi) {
            liveData.value = noteUi
        }

        override fun liveData(): LiveData<NoteUi> = liveData

        override fun updateNote(newName: String, newText: String) {
            val note = liveData.value
            note?.let {
                liveData.value = it.copy(title = newName, text = newText)
            }
        }
    }
}