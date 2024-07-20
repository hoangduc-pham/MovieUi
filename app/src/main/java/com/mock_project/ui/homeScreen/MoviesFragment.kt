package com.mock_project.ui.homeScreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jicsoftwarestudio.movie_ass.R
import com.mock_project.adapter.ImageAdapter
import com.mock_project.model.Movie
import com.mock_project.ui.CarauselLayout
import com.mock_project.ui.detailScreen.DetailScreen
import com.mock_project.viewModel.homeViewModel.MoviesViewModel


class MoviesFragment : Fragment() {
    private lateinit var recyclerViews: List<RecyclerView>
    private lateinit var gridLayoutManagers: List<GridLayoutManager>
    private lateinit var carouselLayoutManagers: List<CarauselLayout>
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var icLoading: View
    private var isSwitch: Boolean = false
    private lateinit var listMovie: List<Movie>

    private val moviesViewModel: MoviesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        icLoading = view.findViewById(R.id.icLoading)
        initRecyclerView(view)
        observeViewModel()
        setupSwipeRefresh()
    }

    private fun initRecyclerView(view: View) {
        swipeRefreshLayout  = view.findViewById(R.id.swipeRefreshLayout)
        recyclerViews = listOf(
            view.findViewById(R.id.recyclerViewPopular),
            view.findViewById(R.id.recyclerViewTopRated),
            view.findViewById(R.id.recyclerViewUpComing),
            view.findViewById(R.id.recyclerViewNowPlaying)
        )

        gridLayoutManagers = List(recyclerViews.size) {
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        }

        carouselLayoutManagers = List(recyclerViews.size) {
            CarauselLayout(requireContext(), RecyclerView.VERTICAL, false)
        }
        updateRecyclerViewLayoutManagers()
    }

    private fun updateRecyclerViewLayoutManagers() {
        recyclerViews.forEachIndexed { index, recyclerView ->
            recyclerView.layoutManager = if (isSwitch) {
                gridLayoutManagers[index]
            } else {
                carouselLayoutManagers[index]
            }
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
                listMovie = movies
                icLoading.visibility = View.VISIBLE
                setupAdapter(movies, recyclerView)
                icLoading.visibility = View.GONE
            }
        }
    }

    private fun setupAdapter(movies: List<Movie>, recyclerView: RecyclerView) {
        val limitedMovies = movies.take(4)
        val imageUrlList = limitedMovies.map { it.posterPath }
        val idList = limitedMovies.map { it.id }
        val nameMovieList = limitedMovies.map { it.title }
        val overView = limitedMovies.map { it.overview }
        val voteCountList = limitedMovies.map { it.voteCount.toString() }

        val adapterGrid = ImageAdapter(imageUrlList, idList, nameMovieList, voteCountList, overView,true, requireContext())
        {
            id -> val intent = Intent(requireContext(), DetailScreen::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }

        val adapter = ImageAdapter(imageUrlList, idList, nameMovieList, voteCountList,overView, false, requireContext())
        {
            id -> val intent = Intent(requireContext(), DetailScreen::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }

        recyclerView.adapter = if (isSwitch) adapterGrid else adapter
    }

    fun setSwitch(isChecked: Boolean) {
        isSwitch = isChecked
        if (::listMovie.isInitialized && isAdded) {
            updateRecyclerViewLayoutManagers()
            recyclerViews.forEach { recyclerView ->
                setupAdapter(listMovie, recyclerView)
            }
        }
    }
    private fun setupSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener {
            moviesViewModel.refreshMovies()
            observeViewModel()
            Handler(Looper.getMainLooper()).postDelayed({
                swipeRefreshLayout.isRefreshing = false
            }, 1000)
        }
    }
}
