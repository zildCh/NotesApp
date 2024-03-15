package com.github.zottaa.note.details

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import com.github.zottaa.core.AbstractFragment
import com.github.zottaa.core.ProvideViewModel
import com.github.zottaa.databinding.FragmentNoteDetailsBinding
import com.github.zottaa.note.list.CategoryUi
import com.google.android.material.textfield.TextInputEditText

class NoteDetailsFragment(
    private var noteId: Long = -1,
    private var currentCategoryId: Long = -1
) : AbstractFragment<FragmentNoteDetailsBinding>() {
    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentNoteDetailsBinding =
        FragmentNoteDetailsBinding.inflate(inflater, container, false)

    private lateinit var viewModel: NoteDetailsViewModel
    private lateinit var letterCount: LetterCount
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (binding.noteTextEditText.text.toString()
                    .isEmpty() && binding.noteTitleEditText.text.toString().isEmpty()
            )
                viewModel.deleteNote(noteId)
            viewModel.comeback()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            noteId = savedInstanceState.getLong(NOTE_ID_KEY)
            currentCategoryId = savedInstanceState.getLong(CURRENT_CATEGORY_KEY)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(NOTE_ID_KEY, noteId)
        outState.putLong(CURRENT_CATEGORY_KEY, currentCategoryId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as ProvideViewModel).viewModel(NoteDetailsViewModel::class.java)
        letterCount = LetterCount.Base()
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)

        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            com.github.zottaa.R.layout.my_spinner_item,
            mutableListOf<CategoryUi>()
        )

        binding.noteDetailCategorySpinner.adapter = spinnerAdapter
        binding.noteDetailCategorySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.updateNote(
                        noteId,
                        binding.noteTitleEditText.text.toString(),
                        binding.noteTextEditText.text.toString(),
                        (binding.noteDetailCategorySpinner.selectedItem as CategoryUi).id(),
                        currentCategoryId
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

        binding.deleteNoteButton.setOnClickListener {
            hideKeyboard()
            viewModel.deleteNote(noteId)
        }

        viewModel.categoryLiveData.observe(viewLifecycleOwner) {
            spinnerAdapter.clear()
            spinnerAdapter.addAll(it)
        }

        viewModel.liveData().observe(viewLifecycleOwner) {
            it.showTitle(binding.noteTitleEditText)
            it.showText(binding.noteTextEditText)
            it.showCategory(binding.noteDetailCategorySpinner)
            it.showUpdateTimeAndLettersCount(binding.updateTimeAndLettersTextView)
        }

        viewModel.init(noteId)

        addUpdateListener(binding.noteTitleEditText)
        addUpdateListener(binding.noteTextEditText)
    }

    private fun addUpdateListener(inputEditText: TextInputEditText) {
        inputEditText.addTextChangedListener {
            if (binding.noteTitleEditText.text.toString()
                    .isNotEmpty() || binding.noteTextEditText.text.toString().isNotEmpty()
            ) {
                binding.noteDetailCategorySpinner.selectedItem?.let {
                    viewModel.updateNote(
                        noteId,
                        binding.noteTitleEditText.text.toString(),
                        binding.noteTextEditText.text.toString(),
                        (binding.noteDetailCategorySpinner.selectedItem as CategoryUi).id(),
                        currentCategoryId
                    )
                }
                if (binding.updateTimeAndLettersTextView.text.toString().isNotEmpty()) {
                    val raw = binding.updateTimeAndLettersTextView.text.toString()
                    val delimiterIndex = raw.indexOf(" |")
                    val updateTime = raw.substring(0..<delimiterIndex)

                    val letterCount = letterCount.count(binding.noteTextEditText.text.toString())

                    binding.updateTimeAndLettersTextView.text =
                        requireContext().resources.getString(
                            com.github.zottaa.R.string.update_time_and_letter_count,
                            updateTime,
                            letterCount
                        )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback.remove()
    }

    companion object {
        private const val NOTE_ID_KEY = "noteKey"
        private const val CURRENT_CATEGORY_KEY = "currentCategoryKey"
    }
}