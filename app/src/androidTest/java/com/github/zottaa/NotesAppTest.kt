package com.github.zottaa

import androidx.test.espresso.Espresso
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NotesAppTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainScenario() {
        val notesListPage = NotesListPage()
        notesListPage.checkVisibleNow()

        notesListPage.clickAddButton()
        notesListPage.checkNotVisibleNow()
        val createNotePage = CreateNotePage()
        createNotePage.checkVisibleNow()

        createNotePage.inputNote(text = "first note")
        createNotePage.clickSaveButton()
        createNotePage.checkNotVisibleNow()
        notesListPage.checkNote(position = 0, title = "first note", date = "22.02.24")
        notesListPage.clickNoteAt(0)
        notesListPage.checkNotVisibleNow()

        val notesDetailPage = EditNotePage()
        notesDetailPage.checkVisibleNow(title = "first note", date = "22.02.24", text = "")
        notesDetailPage.replaceText("Text For First Note ))")
        notesDetailPage.checkVisibleNow(title = "first note", date = "22.02.24", text = "Text For First Note ))")

        notesDetailPage.replaceTitle("first note)")
        notesDetailPage.checkVisibleNow(title = "first note)", date = "22.02.24", text = "Text For First Note ))")

        notesDetailPage.clickSaveButton()
        notesDetailPage.checkNotVisibleNow()

        notesListPage.checkVisibleNow()
        notesListPage.checkNote(position = 0, title = "first note)", date = "22.02.24")

        notesListPage.clickNoteAt(0)
        notesDetailPage.checkVisibleNow(title = "first note)", date = "22.02.24", text = "Text For First Note ))")
        notesDetailPage.clickDeleteButton()
        notesDetailPage.checkNotVisibleNow()

        notesListPage.checkVisibleNow()
    }


}