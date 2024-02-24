package com.github.zottaa.note.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        val adapter = NotesAdapter(object : NoteDetails {
            override fun noteDetails(noteUi: NoteUi) {
                viewModel.noteDetails(noteUi)
            }
        })
        binding.notesRecyclerView.adapter = adapter
        binding.notesRecyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.notesRecyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )

        binding.addButton.setOnClickListener {
            viewModel.addNote()
        }

        viewModel.liveData().observe(viewLifecycleOwner) {
            adapter.update(it)
        }

        viewModel.init()
    }
}