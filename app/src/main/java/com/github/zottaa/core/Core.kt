package com.github.zottaa.core

import android.content.Context
import androidx.room.Room

interface Core {

    fun notesDao(): NotesDao

    class Base(context: Context) : Core {
        private val db = Room.databaseBuilder(context, AppDataBase::class.java, "notes")
            .build()

        override fun notesDao(): NotesDao = db.notesDao()
    }
}