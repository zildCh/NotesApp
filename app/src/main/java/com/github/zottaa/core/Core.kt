package com.github.zottaa.core

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.github.zottaa.R

interface Core {

    fun notesDao(): NotesDao

    fun categoryDao(): CategoryDao

    class Base(context: Context) : Core {
        private val db = Room.databaseBuilder(context, AppDataBase::class.java, "notes")
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    val categoryNames = listOf(
                        context.getString(R.string.category_family),
                        context.getString(R.string.category_work),
                        context.getString(R.string.category_personal),
                        context.getString(R.string.category_sport),
                        context.getString(R.string.category_study))
                    for (i in categoryNames.indices) {
                        val name = categoryNames[i]
                        db.execSQL("INSERT INTO category (category_id, category_name) VALUES (?, ?)", arrayOf(i.toLong() + 1, name))
                    }
                }
            })
            .build()

        override fun notesDao(): NotesDao = db.notesDao()
        override fun categoryDao(): CategoryDao = db.categoryDao()
    }
}