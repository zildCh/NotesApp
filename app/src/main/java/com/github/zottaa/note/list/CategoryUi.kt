package com.github.zottaa.note.list

data class CategoryUi(
    val id: Long,
    private val name: String
) {
    fun isValid(note: NoteUi): Boolean = note.isCategoryIdTheSame(id) || id == 1L
    override fun toString(): String {
        return name
    }


}