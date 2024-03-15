package com.github.zottaa.note.details

interface LetterCount {
    fun count(rawNoteText: String): Int

    class Base : LetterCount {
        override fun count(rawNoteText: String): Int =
            rawNoteText.replace("\\s+".toRegex(), "").replace("\n", "").trim().length

    }
}