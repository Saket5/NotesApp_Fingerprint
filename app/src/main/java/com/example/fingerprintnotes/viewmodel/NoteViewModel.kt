package com.example.fingerprintnotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.fingerprintnotes.model.Note
import com.example.fingerprintnotes.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(
    app: Application,
    private val noteRepository: NoteRepository
) : AndroidViewModel(app) {


    fun addNote(note: Note) =
        viewModelScope.launch {
            noteRepository.insertNote(note)
        }

    fun deleteNote(note: Note) =
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }

    fun updateNote(note: Note) =
        viewModelScope.launch {
            noteRepository.updateNote(note)
        }

    fun getAllNote() = noteRepository.getAllNotes()

    fun searchNote(query: String?) =
        noteRepository.searchNote(query)



}