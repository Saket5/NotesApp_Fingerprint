package com.example.fingerprintnotes.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fingerprintnotes.repository.NoteRepository
import java.lang.IllegalArgumentException

class NoteViewModelFactory (
    val app: Application,
    private val repository: NoteRepository)
    :ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NoteViewModel::class.java)){
            return NoteViewModel(app,repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}