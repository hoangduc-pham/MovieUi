package com.jicsoftwarestudio.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.jicsoftwarestudio.adapter.ImageAdapter
import com.jicsoftwarestudio.adapter.ImageAdapterSearch
import com.jicsoftwarestudio.model.Movie
import com.jicsoftwarestudio.movie_ass.R
import com.jicsoftwarestudio.viewModel.SearchViewModel


class SearchFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ImageAdapterSearch
    private lateinit var searchIcon: ImageButton
    private lateinit var icLoading: ProgressBar
    private lateinit var noFindMovie: TextView
    private lateinit var textEdit: EditText

    private val searchViewModel: SearchViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickSearch(view)
        initRecyclerView(view)
        observeViewModel()
    }

    private fun clickSearch(view: View) {
        searchIcon = view.findViewById(R.id.icSearch)
        textEdit = view.findViewById(R.id.searchEditText)
        icLoading = view.findViewById(R.id.icLoading)
        noFindMovie = view.findViewById(R.id.noFindMovie)

        searchIcon.setOnClickListener {
            val query = textEdit.text.toString()
            if (query.isNotEmpty()) {
                icLoading.visibility = View.VISIBLE
                searchViewModel.searchMovies(query)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please enter a search name movie",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recyclerViewSearch)
        recyclerView.layoutManager = CarauselLayout(requireContext(), RecyclerView.VERTICAL, false)
    }

    private fun observeViewModel() {
        searchViewModel.searchMovies.observe(viewLifecycleOwner, Observer { searchMovies ->
            icLoading.visibility = View.GONE
            noFindMovie.visibility = if(searchMovies.isNullOrEmpty()) View.VISIBLE else View.GONE
            setupAdapter(searchMovies)
        })
    }

    private fun setupAdapter(movieList: List<Movie>) {
        val imageUrlList = movieList.map { it.backdrop_path }
        val nameMovieList = movieList.map { it.title }
        val rateList = movieList.map { it.vote_average }

        adapter = ImageAdapterSearch(imageUrlList, nameMovieList, rateList)
        recyclerView.adapter = adapter
    }
}