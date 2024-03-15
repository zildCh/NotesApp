package com.github.zottaa.note.list

import android.widget.Spinner
import android.widget.TextView
import com.github.zottaa.R
import com.github.zottaa.note.details.LetterCount
import com.github.zottaa.note.details.NoteDetailsScreen
import java.text.SimpleDateFormat
import java.util.Date

data class NoteUi(
    private val id: Long,
    private val title: String,
    private val text: String,
    private val updateTime: Long,
    private val categoryId: Long
) {
    fun isIdTheSame(id: Long) = id == this.id

    fun isIdTheSame(noteUi: NoteUi) = this.id == noteUi.id

    fun isCategoryIdTheSame(id: Long) = this.categoryId == id

    fun showTitle(textView: TextView) {
        textView.text = title
    }

    fun showCategory(spinner: Spinner) {
        val adapter = spinner.adapter
        var category: CategoryUi
        for (i in 0 until adapter.count) {
            category = adapter.getItem(i) as CategoryUi
            if (category.id() == categoryId) {
                spinner.setSelection(i)
            }
        }
    }

    fun showUpdateTime(textView: TextView): String {
        val date = Date(updateTime)
        val format = SimpleDateFormat("dd.MM.yyyy, HH:mm")
        textView.text = format.format(date)
        return format.format(date)
    }

    fun showText(textView: TextView) {
        textView.text = text
    }

    fun showUpdateTimeAndLettersCount(textView: TextView) {
        val updateTime = showUpdateTime(textView)
        val letterCount = LetterCount.Base().count(text)
        textView.text = textView.context.getString(R.string.update_time_and_letter_count, updateTime, letterCount)
    }

    fun detailsScreen(categoryId: Long) : NoteDetailsScreen =
        NoteDetailsScreen(id, categoryId)
}