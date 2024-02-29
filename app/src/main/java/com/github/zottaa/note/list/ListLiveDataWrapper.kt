package com.github.zottaa.note.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.zottaa.core.SingleLiveEvent

interface ListLiveDataWrapper {
    interface Read {
        fun liveData(): LiveData<List<NoteUi>>
    }

    interface UpdateList {
        fun update(list: List<NoteUi>)
    }

    interface Create {
        fun create(noteUi: NoteUi)
    }

    interface Update {
        fun update(
            noteId: Long,
            newTitle: String,
            newText: String,
            updateTime: Long,
            categoryId: Long
        )
    }

    interface Delete {
        fun delete(noteId: Long)
    }

    interface Edit : Update, Delete

    interface Mutable : Read, UpdateList, Edit

    interface All : Mutable, Create

    class Base(
        private val liveData: MutableLiveData<List<NoteUi>> = SingleLiveEvent()
    ) : All {
        override fun liveData(): LiveData<List<NoteUi>> = liveData

        override fun update(list: List<NoteUi>) {
            liveData.value = list
        }

        override fun update(
            noteId: Long,
            newTitle: String,
            newText: String,
            updateTime: Long,
            categoryId: Long
        ) {
            val notes = liveData.value
            val newList = notes?.toMutableList()?.let { list ->
                val note = list.find {
                    it.isIdTheSame(noteId)
                }
                val newNote = note?.copy(
                    title = newTitle,
                    text = newText,
                    updateTime = updateTime,
                    categoryId = categoryId
                )
                newNote?.let {
                    val id = list.indexOf(note)
                    list.remove(note)
                    list.add(newNote)
                    list
                }
            }
            liveData.value = newList
        }

        override fun delete(noteId: Long) {
            val notes = liveData.value
            val newList = notes?.toMutableList()?.let { list ->
                val note = list.find {
                    it.isIdTheSame(noteId)
                }
                list.remove(note)
                list
            }
            liveData.value = newList
        }

        override fun create(noteUi: NoteUi) {
            val list = liveData.value?.toMutableList() ?: mutableListOf()
            list.add(noteUi)
            update(list)
        }
    }
}