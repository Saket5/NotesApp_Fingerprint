package com.example.fingerprintnotes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.findNavController
import com.example.fingerprintnotes.MainActivity
import com.example.fingerprintnotes.R
import com.example.fingerprintnotes.databinding.FragmentNotesBinding
import com.example.fingerprintnotes.model.Note
import com.example.fingerprintnotes.viewmodel.NoteViewModel
import com.google.android.material.snackbar.Snackbar


class NotesFragment : Fragment() {

    private  var binding : FragmentNotesBinding?=null
    private lateinit var noteViewModel:NoteViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotesBinding.inflate(
                inflater,
                container,
                false
        )
        return binding!!.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteViewModel = (activity as MainActivity).noteViewModel
        binding!!.fabSave.setOnClickListener{
            saveNote(it)
        }
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            saveNote(view)
        }
        callback.isEnabled
    }

    private fun saveNote(view: View){

        val noteTitle = binding!!.etNoteTitle.text.toString().trim()
        val noteBody = binding!!.etNoteBody.text.toString().trim()

        if (noteTitle.isNotEmpty()) {
            val note = Note(null,noteTitle, noteBody)

            noteViewModel.addNote(note)
            Snackbar.make(
                    view, "Note saved successfully",
                    Snackbar.LENGTH_SHORT
            ).show()
            view.findNavController().navigate(R.id.action_notesFragment_to_homeFragment)

        }else{
            view.findNavController().navigate(R.id.action_notesFragment_to_homeFragment)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


}