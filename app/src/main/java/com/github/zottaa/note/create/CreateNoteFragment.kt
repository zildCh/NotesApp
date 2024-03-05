package com.github.zottaa.note.create

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import com.github.zottaa.core.AbstractFragment
import com.github.zottaa.core.ProvideViewModel
import com.github.zottaa.databinding.FragmentNoteCreateBinding
import com.github.zottaa.note.list.CategoryUi

class CreateNoteFragment(private var currentCategoryId: Long = -1) :
    AbstractFragment<FragmentNoteCreateBinding>() {
    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentNoteCreateBinding =
        FragmentNoteCreateBinding.inflate(inflater, container, false)

    private lateinit var viewModel: CreateNoteViewModel
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            viewModel.comeback()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            currentCategoryId = savedInstanceState.getLong(NOTE_CREATE_CURRENT_CATEGORY_KEY)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as ProvideViewModel).viewModel(CreateNoteViewModel::class.java)
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)

        binding.createNoteEditText.addTextChangedListener {
            binding.createNoteButton.isEnabled = binding.createNoteEditText.toString().isNotEmpty()
        }

        binding.createNoteButton.setOnClickListener {
            hideKeyboard()
            viewModel.createNote(
                binding.createNoteEditText.text.toString(),
                (binding.noteCreateCategorySpinner.selectedItem as CategoryUi).id,
                currentCategoryId
            )
        }

        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            com.github.zottaa.R.layout.my_spinner_item,
            mutableListOf<CategoryUi>()
        )

        binding.noteCreateCategorySpinner.adapter = spinnerAdapter

        viewModel.categoryLiveData.observe(viewLifecycleOwner) {
            spinnerAdapter.clear()
            spinnerAdapter.addAll(it)
        }
        viewModel.init()

        if (savedInstanceState != null) {
            binding.createNoteEditText.setText(
                savedInstanceState.getString(NOTE_CREATE_TITLE_CACHE_KEY).toString()
            )
            binding.noteCreateCategorySpinner.post {
                binding.noteCreateCategorySpinner.setSelection(
                    savedInstanceState.getInt(NOTE_CREATE_CATEGORY_SPINNER_POSITION_KEY)
                )
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(
            NOTE_CREATE_CATEGORY_SPINNER_POSITION_KEY,
            binding.noteCreateCategorySpinner.selectedItemPosition
        )
        outState.putString(NOTE_CREATE_TITLE_CACHE_KEY, binding.createNoteEditText.text.toString())
        outState.putLong(NOTE_CREATE_CURRENT_CATEGORY_KEY, currentCategoryId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback.remove()
    }

    companion object {
        private const val NOTE_CREATE_TITLE_CACHE_KEY = "NOTE_CREATE_TITLE_CACHE_KEY"
        private const val NOTE_CREATE_CATEGORY_SPINNER_POSITION_KEY = "NOTE_CREATE_CATEGORY_SPINNER_POSITION_KEY"
        private const val NOTE_CREATE_CURRENT_CATEGORY_KEY = "NOTE_CREATE_CURRENT_CATEGORY_KEY"

    }
}