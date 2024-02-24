package com.github.zottaa

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.zottaa.core.Now
import com.github.zottaa.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat
import java.util.Date

@RunWith(AndroidJUnit4::class)
class NotesAppTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainScenario() {
        val notesListPage = NotesListPage()
        notesListPage.checkVisibleNow()

        notesListPage.clickAddButton()
        val createNotePage = CreateNotePage()
        createNotePage.checkVisibleNow()

        createNotePage.inputNote(text = "first note")

        val date = Date(Now.Base().timeInMillis())
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        val expectedDate = format.format(date)

        createNotePage.clickCreateButton()

        createNotePage.checkNotVisibleNow()

        notesListPage.checkNote(position = 0, title = "first note", date = expectedDate)
        notesListPage.clickNoteAt(0)

        val notesDetailPage = EditNotePage()
        notesDetailPage.checkVisibleNow(title = "first note", text = "")
        notesDetailPage.replaceText("Text For First Note ))")
        notesDetailPage.checkVisibleNow(title = "first note", text = "Text For First Note ))")

        notesDetailPage.replaceTitle("first note)")
        notesDetailPage.checkVisibleNow(title = "first note)", text = "Text For First Note ))")

        notesDetailPage.clickSaveButton()
        notesDetailPage.checkNotVisibleNow()

        notesListPage.checkVisibleNow()
        notesListPage.checkNote(position = 0, title = "first note)", date = expectedDate)

        notesListPage.clickNoteAt(0)
        notesDetailPage.checkVisibleNow(title = "first note)", text = "Text For First Note ))")
        notesDetailPage.clickDeleteButton()
        notesDetailPage.checkNotVisibleNow()

        notesListPage.checkVisibleNow()
    }

    @Test
    fun addTwoNotes() {
        val notesListPage = NotesListPage()
        notesListPage.checkVisibleNow()

        notesListPage.clickAddButton()
        val createNotePage = CreateNotePage()
        createNotePage.checkVisibleNow()

        createNotePage.inputNote(text = "first note")

        val date = Date(Now.Base().timeInMillis())
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        val expectedDate = format.format(date)

        createNotePage.clickCreateButton()

        createNotePage.checkNotVisibleNow()

        notesListPage.checkNote(position = 0, title = "first note", date = expectedDate)

        notesListPage.clickAddButton()
        createNotePage.checkVisibleNow()

        createNotePage.inputNote(text = "second note")

        createNotePage.clickCreateButton()

        createNotePage.checkNotVisibleNow()

        notesListPage.checkNote(position = 1, title = "second note", date = expectedDate)
    }


}