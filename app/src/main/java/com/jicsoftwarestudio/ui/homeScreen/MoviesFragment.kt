package com.jicsoftwarestudio.ui.homeScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.jicsoftwarestudio.viewModel.MoviesViewModel
import com.jicsoftwarestudio.adapter.ImageAdapter
import com.jicsoftwarestudio.model.Movie
import com.jicsoftwarestudio.movie_ass.R
import com.jicsoftwarestudio.ui.CarauselLayout

class MoviesFragment : Fragment() {
    private lateinit var recyclerViews: List<RecyclerView>
    private lateinit var adapters: List<ImageAdapter>
    private lateinit var carouselLayoutManagers: List<CarauselLayout>
    private lateinit var icLoading: View

    private val moviesViewModel: MoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)
        observeViewModel()
    }

    private fun initRecyclerView(view: View) {
        icLoading = view.findViewById(R.id.icLoading)
        recyclerViews = listOf(
            view.findViewById(R.id.recyclerViewPopular),
            view.findViewById(R.id.recyclerViewTopRated),
            view.findViewById(R.id.recyclerViewUpComing),
            view.findViewById(R.id.recyclerViewNowPlaying)
        )

        carouselLayoutManagers = List(recyclerViews.size) {
            CarauselLayout(requireContext(), RecyclerView.VERTICAL, false)
        }

        recyclerViews.forEachIndexed { index, recyclerView ->
            recyclerView.layoutManager = carouselLayoutManagers[index]
        }
    }

    private fun observeViewModel() {
        val movieObservers = listOf(
            moviesViewModel.popularMovies to recyclerViews[0],
            moviesViewModel.topRatedMovies to recyclerViews[1],
            moviesViewModel.upcomingMovies to recyclerViews[2],
            moviesViewModel.nowPlayingMovies to recyclerViews[3]
        )

        movieObservers.forEach { (movieLiveData, recyclerView) ->
            movieLiveData.observe(viewLifecycleOwner) { movies ->
                icLoading.visibility = View.VISIBLE
                setupAdapter(movies, recyclerView)
                icLoading.visibility = View.GONE
            }
        }
    }


    private fun setupAdapter(movies: List<Movie>, recyclerView: RecyclerView) {
        val imageUrlList = movies.map { it.poster_path }
        val idList = movies.map { it.id }
        val nameMovieList = movies.map { it.title }
        val voteCountList = movies.map { it.vote_count.toString() }

        val adapter =
            ImageAdapter(imageUrlList, idList, nameMovieList, voteCountList, true, requireContext())
        recyclerView.adapter = adapter
    }
}
