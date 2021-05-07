package com.example.fingerprintnotes.repository

import com.example.fingerprintnotes.db.NoteDao
import com.example.fingerprintnotes.model.Note

class NoteRepository (private val dao: NoteDao) {

    val notes = dao.getAllNotes();
    suspend fun insertNote(note: Note) = dao.insertNote(note)
    suspend fun deleteNote(note: Note) = dao.deleteNote(note)
    suspend fun updateNote(note: Note) = dao.updateNote(note)
    fun getAllNotes() = dao.getAllNotes()
    fun searchNote(query: String?) = dao.searchNote(query)
}