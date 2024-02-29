package com.github.zottaa.note.list

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import com.github.zottaa.core.AbstractFragment
import com.github.zottaa.core.ProvideViewModel
import com.github.zottaa.databinding.FragmentNoteListBinding

class NoteListFragment : AbstractFragment<FragmentNoteListBinding>() {
    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentNoteListBinding =
        FragmentNoteListBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = (activity as ProvideViewModel).viewModel(NoteListViewModel::class.java)
        val adapter = NotesAdapter(
            object : NoteDetails {
                override fun noteDetails(noteUi: NoteUi) {
                    viewModel.noteDetails(
                        noteUi,
                        (binding.categorySpinner.selectedItem as CategoryUi).id
                    )
                }
            }
        )

        binding.notesRecyclerView.adapter = adapter
        binding.notesRecyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.notesRecyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )

        binding.addButton.setOnClickListener {
            viewModel.addNote((binding.categorySpinner.selectedItem as CategoryUi).id)
        }

        viewModel.liveData().observe(viewLifecycleOwner) {
            adapter.update(it)
        }

        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_spinner_item,
            mutableListOf<CategoryUi>()
        )

        binding.categorySpinner.adapter = spinnerAdapter
        binding.categorySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val category = parent?.getItemAtPosition(position) as CategoryUi
                    viewModel.init(category.id)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }

        viewModel.categoryLiveData.observe(viewLifecycleOwner) {
            spinnerAdapter.clear()
            spinnerAdapter.addAll(it)
        }

        viewModel.init(
            INITIAL_CATEGORY_ID
        )

        if (savedInstanceState != null)
            binding.categorySpinner.setSelection(
                savedInstanceState.getInt(CATEGORY_SPINNER_KEY),
                true
            )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(CATEGORY_KEY, (binding.categorySpinner.selectedItem as CategoryUi).id)
        outState.putInt(CATEGORY_SPINNER_KEY, binding.categorySpinner.selectedItemPosition)
    }

    companion object {
        private const val INITIAL_CATEGORY_ID = 1L
        private const val CATEGORY_KEY = "categoryKey"
        private const val CATEGORY_SPINNER_KEY = "categorySpinnerKey"
    }
}