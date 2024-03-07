package com.github.zottaa.core

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.github.zottaa.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface Core {

    fun notesDao(): NotesDao

    fun categoryDao(): CategoryDao

    fun sharedPreferencesRepository(): SharedPreferencesRepository

    fun retrofit(): Retrofit

    class Base(context: Context) : Core {
        private val db = Room.databaseBuilder(context, AppDataBase::class.java, "notes")
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    val categoryNames = listOf(
                        context.getString(R.string.no_category),
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

        private val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.106:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        private val sharedPreferencesRepository = SharedPreferencesRepository.Base(context)

        override fun notesDao(): NotesDao = db.notesDao()
        override fun categoryDao(): CategoryDao = db.categoryDao()
        override fun sharedPreferencesRepository() = sharedPreferencesRepository
        override fun retrofit(): Retrofit = retrofit

    }
}