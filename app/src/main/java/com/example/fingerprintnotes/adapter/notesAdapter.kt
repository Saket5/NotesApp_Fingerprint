package com.example.fingerprintnotes.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fingerprintnotes.HomeFragmentDirections
import com.example.fingerprintnotes.R
import com.example.fingerprintnotes.databinding.ItemNotesBinding
import com.example.fingerprintnotes.model.Note

class notesAdapter : RecyclerView.Adapter<notesAdapter.NoteViewHolder>() {

    class NoteViewHolder(val itemBinding: ItemNotesBinding) :
            RecyclerView.ViewHolder(itemBinding.root)


    private val differCallback =
            object : DiffUtil.ItemCallback<Note>() {
                override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                    return oldItem.body == newItem.body &&
                            oldItem.title == newItem.title
                }

                override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                    return oldItem == newItem
                }

            }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
                ItemNotesBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                )
        )

    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = differ.currentList[position]

        holder.itemBinding.tvTitle.text = currentNote.title
        holder.itemBinding.tvDesc.text = currentNote.body
        //holder.itemBinding.cardView.setCardBackgroundColor(Color.parseColor(R.color.ColorLightBlack.toString()))

        holder.itemView.setOnClickListener { view ->
            val bundle = Bundle().apply {
                putSerializable("update_note",currentNote)
            }
            view.findNavController().navigate(R.id.action_homeFragment_to_updateFragment,bundle)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}