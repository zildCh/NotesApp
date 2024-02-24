package com.github.zottaa.core

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "notes"
)
data class NoteCache(
    @PrimaryKey
    @ColumnInfo(name = "note_id")
    val id: Long,
    @ColumnInfo(name = "note_title")
    val title: String,
    @ColumnInfo(name = "note_text")
    val text: String,
    @ColumnInfo(name = "note_update_date")
    val updateTime: Long
)