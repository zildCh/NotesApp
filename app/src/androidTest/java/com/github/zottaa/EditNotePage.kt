//package com.github.zottaa
//
//import android.widget.Button
//import android.widget.LinearLayout
//import android.widget.TextView
//import androidx.constraintlayout.widget.ConstraintLayout
//import androidx.test.espresso.Espresso.onView
//import androidx.test.espresso.action.ViewActions.clearText
//import androidx.test.espresso.action.ViewActions.click
//import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
//import androidx.test.espresso.action.ViewActions.typeText
//import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
//import androidx.test.espresso.assertion.ViewAssertions.matches
//import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
//import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
//import androidx.test.espresso.matcher.ViewMatchers.withId
//import androidx.test.espresso.matcher.ViewMatchers.withParent
//import androidx.test.espresso.matcher.ViewMatchers.withText
//import com.google.android.material.textfield.TextInputEditText
//import org.hamcrest.CoreMatchers.allOf
//
//class EditNotePage {
//
//    private val rootId: Int = R.id.editNoteRootLayout
//
//    private val inputViewTitle = onView(
//        allOf(
//            isAssignableFrom(TextInputEditText::class.java),
//            withId(R.id.noteTitleEditText),
//            withParent(isAssignableFrom(ConstraintLayout::class.java)),
//            withParent(withId(rootId))
//        )
//    )
//
//    private val inputViewText = onView(
//        allOf(
//            isAssignableFrom(TextInputEditText::class.java),
//            withId(R.id.noteTextEditText),
//            withParent(isAssignableFrom(ConstraintLayout::class.java)),
//            withParent(withId(rootId))
//        )
//    )
//
//    fun checkVisibleNow(title: String, date: String, text: String) {
//        onView(
//            allOf(
//                isAssignableFrom(TextView::class.java),
//                withId(R.id.notesDateTextView),
//                withParent(isAssignableFrom(ConstraintLayout::class.java)),
//                withParent(withId(rootId))
//            )
//        ).check(matches(withText(date)))
//        onView(
//            allOf(
//                isAssignableFrom(TextView::class.java),
//                withId(R.id.editNoteTitleTextView),
//                withText("update note"),
//                withParent(isAssignableFrom(ConstraintLayout::class.java)),
//                withParent(withId(rootId))
//            )
//        ).check(matches(isDisplayed()))
//        inputViewTitle.check(matches(withText(title)))
//        inputViewText.check(matches(withText(text)))
//    }
//
//    fun replaceTitle(text: String) {
//        inputViewTitle.perform(clearText(), typeText(text), closeSoftKeyboard())
//    }
//
//    fun replaceText(text: String) {
//        inputViewText.perform(clearText(), typeText(text), closeSoftKeyboard())
//    }
//
//    fun clickSaveButton() {
//        onView(
//            allOf(
//                withParent(isAssignableFrom(ConstraintLayout::class.java)),
//                withParent(withId(rootId)),
//                isAssignableFrom(Button::class.java),
//                withId(R.id.saveNoteButton),
//                withText("save")
//            )
//        ).perform(click())
//    }
//
//    fun checkNotVisibleNow() {
//        inputViewText.check(doesNotExist())
//    }
//
//    fun clickDeleteButton() {
//        onView(
//            allOf(
//                withParent(isAssignableFrom(ConstraintLayout::class.java)),
//                withParent(withId(rootId)),
//                isAssignableFrom(Button::class.java),
//                withId(R.id.deleteNoteButton),
//                withText("delete")
//            )
//        ).perform(click())
//    }
//}