package com.github.zottaa.core

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NoteCache::class, CategoryCache::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun notesDao(): NotesDao

    abstract fun categoryDao(): CategoryDao
}