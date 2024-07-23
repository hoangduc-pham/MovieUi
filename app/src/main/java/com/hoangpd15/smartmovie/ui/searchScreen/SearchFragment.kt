package com.hoangpd15.smartmovie.ui.searchScreen

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.hoangpd15.smartmovie.R
import com.hoangpd15.smartmovie.adapter.ImageAdapterSearch
import com.hoangpd15.smartmovie.adapter.ListGenresAdapter
import com.hoangpd15.smartmovie.databinding.FragmentSearchBinding
import com.hoangpd15.smartmovie.model.Movie
import com.hoangpd15.smartmovie.ui.CarauselLayout


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
        clickSearch(view)
        initRecyclerView(view)
        observeViewModel()
    }

    private fun clickSearch(view: View) {
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

    private fun initRecyclerView(view: View) {
        binding.recyclerViewSearch.layoutManager = CarauselLayout(requireContext(), RecyclerView.VERTICAL, false)
    }

    private fun observeViewModel() {
        searchViewModel.searchMovies.observe(viewLifecycleOwner, Observer { searchMovies ->
            setupAdapter(searchMovies)
        })
        searchViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.icLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
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