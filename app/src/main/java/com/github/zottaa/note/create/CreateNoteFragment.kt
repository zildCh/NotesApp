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

class CreateNoteFragment(private val currentCategoryId: Long) :
    AbstractFragment<FragmentNoteCreateBinding>() {
    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentNoteCreateBinding =
        FragmentNoteCreateBinding.inflate(inflater, container, false)

    private lateinit var viewModel: CreateNoteViewModel
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            viewModel.comeback()
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
            R.layout.simple_spinner_item,
            mutableListOf<CategoryUi>()
        )

        binding.noteCreateCategorySpinner.adapter = spinnerAdapter

        viewModel.categoryLiveData.observe(viewLifecycleOwner) {
            spinnerAdapter.addAll(it)
        }

        viewModel.init()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback.remove()
    }
}