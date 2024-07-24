package com.hoangpd15.smartmovie.ui.genresScreen.movieByGenres

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.hoangpd15.smartmovie.R
import com.hoangpd15.smartmovie.adapter.ImageAdapterSearch
import com.hoangpd15.smartmovie.databinding.FragmentGenresBinding
import com.hoangpd15.smartmovie.databinding.FragmentListMovieGenresBinding
import com.hoangpd15.smartmovie.databinding.FragmentSearchBinding
import com.hoangpd15.smartmovie.model.Movie
import com.hoangpd15.smartmovie.ui.UiState

class ListMovieGenresFragment : Fragment() {
    private var _binding: FragmentListMovieGenresBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ImageAdapterSearch
    private val movieByGenresViewModel: MoviesByGenreViewModel by viewModels()
    private val args: ListMovieGenresFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListMovieGenresBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeViewModel()
    }

    private fun initRecyclerView() {
        val genreId = args.idGenre
        movieByGenresViewModel.fetchMoviesByGenre(genreId)

        val genreName = args.nameGenres
        binding.topAppBar.title = genreName
        binding.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.recyclerViewGenresMovie.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }

    private fun observeViewModel() {
        movieByGenresViewModel.uiState.observe(viewLifecycleOwner, Observer { uiState ->
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

                is UiState.LoadMore -> TODO()
            }
        })
    }

    private fun setupAdapter(movieList: List<Movie>) {
        val imageUrlList = movieList.map { it.backdropPath }
        val nameMovieList = movieList.map { it.title }
        val rateList = movieList.map { it.voteAverage }
        val idMovie = movieList.map { it.id }

        adapter = ImageAdapterSearch(imageUrlList, nameMovieList, rateList, idMovie)
        { id ->
            val action =
                ListMovieGenresFragmentDirections.actionListMovieGenresFragmentToDetailFragment(id)
            findNavController().navigate(action)
        }
        binding.recyclerViewGenresMovie.adapter = adapter
    }
}