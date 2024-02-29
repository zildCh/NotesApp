package com.github.zottaa.note.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.zottaa.databinding.ItemNotesListRecyclerViewBinding

class NotesAdapter(
    private val noteDetails: NoteDetails,
    private var list: List<NoteUi> = emptyList()
) : RecyclerView.Adapter<NoteListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteListViewHolder {
        val binding = ItemNotesListRecyclerViewBinding.inflate(
            LayoutInflater.from(parent.context)
        )
        return NoteListViewHolder(binding, noteDetails)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: NoteListViewHolder, position: Int) {
        holder.hold(list[position])
    }

    fun update(newList: List<NoteUi>) {
        val diffUtil = NoteListDiffUtil(list, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        list = newList
        diffResult.dispatchUpdatesTo(this)
    }
}



class NoteListViewHolder(
    private val binding: ItemNotesListRecyclerViewBinding,
    private val noteDetails: NoteDetails,
) : RecyclerView.ViewHolder(binding.root) {

    fun hold(noteUi: NoteUi) {
        noteUi.apply {
            showTitle(binding.noteTitleTextView)
            showDate(binding.noteEditDateTextView)
            binding.notesLinearLayout.setOnClickListener {
                noteDetails.noteDetails(noteUi)
            }
        }
    }
}

class NoteListDiffUtil(
    private val oldList: List<NoteUi>,
    private val newList: List<NoteUi>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].isIdTheSame(newList[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == (newList[newItemPosition])
}