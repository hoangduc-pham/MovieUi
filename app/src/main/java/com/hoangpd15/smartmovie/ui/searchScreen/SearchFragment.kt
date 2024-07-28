package com.hoangpd15.smartmovie.ui.searchScreen

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entities.Movie
import com.hoangpd15.smartmovie.adapter.ImageAdapterSearch
import com.hoangpd15.smartmovie.databinding.FragmentSearchBinding
import com.hoangpd15.smartmovie.ui.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var adapter: ImageAdapterSearch
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val searchViewModel: SearchViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickSearch()
        initRecyclerView()
        observeViewModel()
    }

    private fun clickSearch() {
        val performSearch = {
            val query = binding.searchEditText.text.toString()
            if (query.isNotEmpty()) {
                searchViewModel.searchMovies(query)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please enter a search name movie",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.icSearch.setOnClickListener {
            performSearch()
        }

        binding.searchEditText.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                actionId == EditorInfo.IME_ACTION_DONE ||
                event?.action == KeyEvent.ACTION_DOWN &&
                event.keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                performSearch()
                true
            } else {
                false
            }
        }
    }

    private fun initRecyclerView() {
        binding.recyclerViewSearch.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }

    private fun observeViewModel() {
        searchViewModel.uiState.observe(viewLifecycleOwner, Observer { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    binding.icLoading.visibility = View.VISIBLE
                }

                is UiState.Success<*> -> {
                    binding.icLoading.visibility = View.GONE
                    setupAdapter(uiState.list as List<Movie>)
                }

                is UiState.Error -> {
                    binding.icLoading.visibility = View.GONE
                }

                is UiState.LoadMore -> {
                    TODO()
                }
            }
        })
        searchViewModel.noFindMovie.observe(viewLifecycleOwner, Observer { noFindMovie ->
            binding.noFindMovie.visibility = if (noFindMovie) View.VISIBLE else View.GONE
        })
    }

    private fun setupAdapter(movieList: List<Movie>) {
        val imageUrlList = movieList.map { it.backdropPath }
        val nameMovieList = movieList.map { it.title }
        val rateList = movieList.map { it.voteAverage }
        val idMovie = movieList.map { it.id }

        adapter = ImageAdapterSearch(imageUrlList, nameMovieList, rateList, idMovie)
        { id ->
            val action = SearchFragmentDirections.actionNavSearchToDetailFragment(id)
            findNavController().navigate(action)
        }
        binding.recyclerViewSearch.adapter = adapter
    }
}