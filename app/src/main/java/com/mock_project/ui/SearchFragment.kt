package com.mock_project.ui

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.jicsoftwarestudio.movie_ass.R
import com.mock_project.adapter.ImageAdapterSearch
import com.mock_project.model.Movie
import com.mock_project.ui.detailScreen.DetailScreen
import com.mock_project.viewModel.SearchViewModel


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

        val performSearch = {
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
        searchIcon.setOnClickListener {
            performSearch()
        }

        textEdit.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                actionId == EditorInfo.IME_ACTION_DONE ||
                event?.action == KeyEvent.ACTION_DOWN &&
                event.keyCode == KeyEvent.KEYCODE_ENTER) {
                performSearch()
                true
            } else {
                false
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
        val imageUrlList = movieList.map { it.backdropPath }
        val nameMovieList = movieList.map { it.title }
        val rateList = movieList.map { it.voteAverage }
        val idMovie = movieList.map { it.id }

        adapter = ImageAdapterSearch(imageUrlList, nameMovieList, rateList, idMovie)
        {
            id -> val intent = Intent(requireContext(), DetailScreen::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
    }
}