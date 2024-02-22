package com.github.zottaa.note.list

data class NoteUi(
    private val id: Long,
    private val title: String,
    private val text: String
) {
    fun isIdTheSame(id: Long) = id == this.id
}