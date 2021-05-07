package com.example.fingerprintnotes

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.fingerprintnotes.databinding.ActivityMainBinding
import com.example.fingerprintnotes.db.NotesDatabase
import com.example.fingerprintnotes.repository.NoteRepository
import com.example.fingerprintnotes.viewmodel.NoteViewModel
import com.example.fingerprintnotes.viewmodel.NoteViewModelFactory
import java.util.concurrent.Executor

class  MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var noteViewModel: NoteViewModel
    private lateinit var executor: Executor
    private lateinit var biometricManager: BiometricManager
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    var isAuthenticated:Int = 0
    override fun onResume() {
        super.onResume()
        isAuthenticated=0
        binding.fragmentHost.visibility=View.GONE
        biometricPrompt.authenticate(promptInfo)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)
        setUpViewModel()
        setContentView(binding.root)
        buildbiometricprompt()
        if(isAuthenticated==0) {
            biometricPrompt.authenticate(promptInfo)
            binding.fragmentHost.visibility=View.GONE
        }

    }
    private fun setUpViewModel() {
        val dao = NotesDatabase.getInstance(application).noteDao
        val noteRepository = NoteRepository(
            dao
        )

        val viewModelProviderFactory =
            NoteViewModelFactory(
                application, noteRepository
            )

        noteViewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(NoteViewModel::class.java)
    }
    private fun buildbiometricprompt()
    {
        executor = ContextCompat.getMainExecutor(this)

        biometricPrompt = BiometricPrompt(this@MainActivity,executor,object : BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                finishAffinity()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                isAuthenticated=1;
                binding.fragmentHost.visibility=View.VISIBLE
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()

            }
        })
        promptInfo = BiometricPrompt.PromptInfo.Builder().setTitle("FingerPrint Authentication")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Cancel")
                .build()

    }
}