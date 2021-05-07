package com.example.fingerprintnotes.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.fingerprintnotes.MainActivity
import com.example.fingerprintnotes.R
import com.example.fingerprintnotes.UpdateFragmentArgs
import com.example.fingerprintnotes.databinding.FragmentUpdateBinding
import com.example.fingerprintnotes.model.Note
import com.example.fingerprintnotes.viewmodel.NoteViewModel


class UpdateFragment : Fragment() {

    private var binding: FragmentUpdateBinding? = null

    private lateinit var currentNote: Note
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteViewModel = (activity as MainActivity).noteViewModel
        binding= FragmentUpdateBinding.bind(view)
        val args : UpdateFragmentArgs by navArgs()
        currentNote = args.updateNote!!

        binding!!.etNoteBody.setText(currentNote.body)
        binding!!.etNoteTitle.setText(currentNote.title)

        binding!!.fabSave.setOnClickListener {
           saveNote(it)
        }
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            saveNote(view)
        }
        callback.isEnabled
        binding!!.fabDel.setOnClickListener {
            delNote(it)
        }
    }

    private fun saveNote(view: View)
    {
        val title = binding!!.etNoteTitle.text.toString().trim()
        val body = binding!!.etNoteBody.text.toString().trim()

        if (title.isNotEmpty()) {
            val note = Note(currentNote.id, title, body)
            noteViewModel.updateNote(note)

            view.findNavController().navigate(R.id.action_updateFragment_to_homeFragment)

        } else {
            view.findNavController().navigate(R.id.action_updateFragment_to_homeFragment)
        }
    }
    private fun delNote(view: View)
    {
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("Are you sure you want to permanently delete this note?")
            setPositiveButton("DELETE") { _, _ ->
                noteViewModel.deleteNote(currentNote)
                view?.findNavController()?.navigate(
                        R.id.action_updateFragment_to_homeFragment
                )
            }
            setNegativeButton("CANCEL", null)
        }.create().show()
    }
}


