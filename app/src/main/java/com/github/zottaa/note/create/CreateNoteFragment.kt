package com.github.zottaa.note.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import com.github.zottaa.core.AbstractFragment
import com.github.zottaa.core.ProvideViewModel
import com.github.zottaa.databinding.FragmentNoteCreateBinding

class CreateNoteFragment : AbstractFragment<FragmentNoteCreateBinding>() {
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
            viewModel.createNote(binding.createNoteEditText.text.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback.remove()
    }
}