package com.github.zottaa.note.list

import android.widget.TextView
import com.github.zottaa.note.details.NoteDetailsScreen
import java.text.SimpleDateFormat
import java.util.Date

data class NoteUi(
    private val id: Long,
    private val title: String,
    private val text: String,
    private val updateTime: Long
) {
    fun isIdTheSame(id: Long) = id == this.id

    fun isIdTheSame(noteUi: NoteUi) = this.id == noteUi.id

    fun showTitle(textView: TextView) {
        textView.text = title
    }

    fun showDate(textView: TextView) {
        val date = Date(updateTime)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        textView.text = format.format(date)
    }

    fun showText(textView: TextView) {
        textView.text = text
    }

    fun detailsScreen() : NoteDetailsScreen =
        NoteDetailsScreen(id)
}