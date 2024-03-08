package com.github.zottaa.note.list

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
                        (binding.categorySpinner.selectedItem as CategoryUi).id()
                    )
                }
            },
            object : ShowNoteCategory {
                override fun showNoteCategory(textView: TextView, noteUi: NoteUi) {
                    var category: CategoryUi
                    for (i in 0 until binding.categorySpinner.adapter.count) {
                        category = binding.categorySpinner.adapter.getItem(i) as CategoryUi
                        if (noteUi.isCategoryIdTheSame(category.id())) {
                            textView.text = category.toString()
                            break
                        }
                    }
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
            viewModel.addNote((binding.categorySpinner.selectedItem as CategoryUi).id())
        }

        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }

        viewModel.liveData().observe(viewLifecycleOwner) {
            adapter.update(it)
        }

        binding.notesRecyclerView.isEnabled = false

        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            com.github.zottaa.R.layout.my_spinner_item,
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
                    viewModel.init(category.id())
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }

        viewModel.categoryLiveData.observe(viewLifecycleOwner) {
            spinnerAdapter.clear()
            spinnerAdapter.addAll(it)
        }

        viewModel.uiStateLiveData.observe(viewLifecycleOwner) {
            it.show(binding.progressBar)
        }

        viewModel.init(
            INITIAL_CATEGORY_ID
        )

        viewModel.synchronize()

        if (savedInstanceState != null) {
            binding.categorySpinner.post {
                binding.categorySpinner.setSelection(
                    savedInstanceState.getInt(NOTE_LIST_CATEGORY_SPINNER_POSITION_KEY)
                )
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(
            NOTE_LIST_CATEGORY_SPINNER_POSITION_KEY,
            binding.categorySpinner.selectedItemPosition
        )
    }

    companion object {
        private const val INITIAL_CATEGORY_ID = 0L
        private const val NOTE_LIST_CATEGORY_SPINNER_POSITION_KEY =
            "NOTE_LIST_CATEGORY_SPINNER_POSITION_KEY"
    }
}