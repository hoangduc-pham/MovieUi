package com.hoangpd15.smartmovie.ui

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
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.hoangpd15.smartmovie.R
import com.hoangpd15.smartmovie.adapter.ImageAdapterSearch
import com.hoangpd15.smartmovie.model.Movie
import com.hoangpd15.smartmovie.ui.detailScreen.DetailFragmentArgs
import com.hoangpd15.smartmovie.viewModel.MoviesByGenreViewModel

class ListMovieGenresFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ImageAdapterSearch
    private lateinit var icLoading: ProgressBar
    private lateinit var icLoadError: TextView
    private lateinit var topAppBar: MaterialToolbar
    private val movieByGenresViewModel: MoviesByGenreViewModel by viewModels()
    private val args: ListMovieGenresFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_movie_genres, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)
        observeViewModel()
    }
    private fun initRecyclerView(view: View) {
        icLoading = view.findViewById(R.id.icLoading)
        topAppBar = view.findViewById(R.id.topAppBar)
        icLoading.visibility = View.VISIBLE

        val genreId = args.idGenre
        movieByGenresViewModel.fetchMoviesByGenre(genreId)

        val genreName = args.nameGenres
        topAppBar.title = genreName

        recyclerView = view.findViewById(R.id.recyclerViewGenresMovie)
        recyclerView.layoutManager = CarauselLayout(requireContext(), RecyclerView.VERTICAL, false)
    }
    private fun observeViewModel() {
        movieByGenresViewModel.moviesByGenre.observe(viewLifecycleOwner, Observer { movies  ->
            icLoading.visibility = View.GONE
            setupAdapter(movies)
        })
    }

    private fun setupAdapter(movieList: List<Movie>) {
        val imageUrlList = movieList.map { it.backdropPath }
        val nameMovieList = movieList.map { it.title }
        val rateList = movieList.map { it.voteAverage }
        val idMovie = movieList.map { it.id }

        adapter = ImageAdapterSearch(imageUrlList, nameMovieList, rateList, idMovie)
        {
            id ->
            val action = ListMovieGenresFragmentDirections.actionListMovieGenresFragmentToDetailFragment(id)
            findNavController().navigate(action)
        }
        recyclerView.adapter = adapter
    }
}