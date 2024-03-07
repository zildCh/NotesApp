package com.github.zottaa.core

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteCache)

    @Query("SELECT * FROM notes where note_id = :noteId")
    suspend fun note(noteId: Long): NoteCache

    @Query("SELECT * FROM notes")
    suspend fun notes(): List<NoteCache>

    @Query("DELETE FROM notes WHERE note_id = :noteId")
    suspend fun delete(noteId: Long)

    @Query("DELETE FROM notes")
    suspend fun deleteAll()
}