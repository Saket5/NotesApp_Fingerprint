package com.example.fingerprintnotes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.activity.addCallback
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.fingerprintnotes.MainActivity
import com.example.fingerprintnotes.R
import com.example.fingerprintnotes.adapter.notesAdapter
import com.example.fingerprintnotes.databinding.FragmentHomeBinding
import com.example.fingerprintnotes.model.Note
import com.example.fingerprintnotes.viewmodel.NoteViewModel


class HomeFragment : Fragment(){

    private  lateinit var notesViewModel: NoteViewModel
    private lateinit var noteAdapter: notesAdapter
    private lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentHomeBinding.bind(view);
        notesViewModel = (activity as MainActivity).noteViewModel
        binding.addNoteFab.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_notesFragment)
        }
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            finishAffinity(activity as MainActivity)
        }
        callback.isEnabled
        setUpRecyclerView()
        binding.searchView.setOnClickListener{
            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {

                    if (newText != null) {
                        searchNote(newText)
                    }
                    return true
                }
            } )
        }


    }

    private fun setUpRecyclerView() {
        noteAdapter = notesAdapter()

        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(
                    2,
                    StaggeredGridLayoutManager.VERTICAL
            )
            adapter = noteAdapter
        }
        notesViewModel.getAllNote().observe(viewLifecycleOwner,{
            noteAdapter.differ.submitList(it)
        })

    }
    private fun updateUI(note: List<Note>) {
        if (note.isNotEmpty()) {
            binding.recyclerView.visibility = View.VISIBLE
        } else {
            binding.recyclerView.visibility = View.GONE
        }
    }



    private fun searchNote(query: String?) {
        val searchQuery = "%$query%"
        notesViewModel.searchNote(searchQuery).observe(
                this, { list ->
            noteAdapter.differ.submitList(list)
        }
        )
    }




}