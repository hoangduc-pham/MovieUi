package com.mock_project.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.jicsoftwarestudio.movie_ass.R
import com.mock_project.adapter.ListGenresAdapter
import com.mock_project.model.Genres
import com.mock_project.viewModel.GenresViewModel

class GenresFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListGenresAdapter
    private val listGenresViewModel: GenresViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_genres, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)
        observeViewModel()
    }
    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recyclerListGenres)
        recyclerView.layoutManager = CarauselLayout(requireContext(), RecyclerView.VERTICAL, false)
    }

    private fun observeViewModel() {
        listGenresViewModel.listGenres.observe(viewLifecycleOwner, Observer { listGenres ->
            setupAdapter(listGenres)
        })
    }

    private fun setupAdapter(listGenres: List<Genres>) {
        val nameGenres = listGenres.map { it.name }
        val idGenres = listGenres.map { it.id }
        adapter = ListGenresAdapter(nameGenres, idGenres) { (idGenre, nameGenres) ->
            val intent = Intent(requireContext(), ListMovieGenresActivity::class.java)
            intent.putExtra("GENRE_ID", idGenre)
            intent.putExtra("GENRE_NAME", nameGenres)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
    }
}