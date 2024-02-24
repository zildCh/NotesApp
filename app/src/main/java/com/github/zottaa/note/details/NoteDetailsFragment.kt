package com.github.zottaa.note.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import com.github.zottaa.core.AbstractFragment
import com.github.zottaa.core.ProvideViewModel
import com.github.zottaa.databinding.FragmentNoteDetailsBinding
import com.google.android.material.textfield.TextInputEditText

class NoteDetailsFragment(
    private var noteId: Long = -1
) : AbstractFragment<FragmentNoteDetailsBinding>() {
    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentNoteDetailsBinding =
        FragmentNoteDetailsBinding.inflate(inflater, container, false)

    private lateinit var viewModel: NoteDetailsViewModel
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            viewModel.comeback()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            noteId = savedInstanceState.getLong(NOTE_ID_KEY)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(NOTE_ID_KEY, noteId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as ProvideViewModel).viewModel(NoteDetailsViewModel::class.java)
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)

        addSaveButtonListener(binding.noteTitleEditText)
        addSaveButtonListener(binding.noteTextEditText)

        binding.saveNoteButton.setOnClickListener {
            hideKeyboard()
            viewModel.updateNote(
                noteId,
                binding.noteTitleEditText.text.toString(),
                binding.noteTextEditText.text.toString()
            )
        }

        binding.deleteNoteButton.setOnClickListener {
            hideKeyboard()
            viewModel.deleteNote(noteId)
        }

        viewModel.liveData().observe(viewLifecycleOwner) {
            it.showTitle(binding.noteTitleEditText)
            it.showText(binding.noteTextEditText)
        }

        viewModel.init(noteId)
    }

    private fun addSaveButtonListener(inputEditText: TextInputEditText) {
        inputEditText.addTextChangedListener {
            binding.saveNoteButton.isEnabled = binding.noteTitleEditText.toString()
                .isNotEmpty() && binding.noteTextEditText.toString().isNotEmpty()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback.remove()
    }

    companion object {
        private const val NOTE_ID_KEY = "note_key"
    }
}