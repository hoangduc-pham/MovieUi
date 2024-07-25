package com.hoangpd15.smartmovie.ui.homeScreen.movieScreen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hoangpd15.smartmovie.R
import com.hoangpd15.smartmovie.adapter.ImageAdapter
import com.hoangpd15.smartmovie.databinding.FragmentGenresBinding
import com.hoangpd15.smartmovie.databinding.FragmentListMovieGenresBinding
import com.hoangpd15.smartmovie.databinding.FragmentMoviesBinding
import com.hoangpd15.smartmovie.model.Movie
import com.hoangpd15.smartmovie.ui.UiState
import com.hoangpd15.smartmovie.ui.UiStateAllMovie
import com.hoangpd15.smartmovie.ui.homeScreen.HomeFragmentDirections


class MoviesFragment : Fragment() {
    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerViews: List<RecyclerView>
    private lateinit var gridLayoutManagers: List<GridLayoutManager>
    private lateinit var linearLayoutManagers: List<LinearLayoutManager>
    private lateinit var textTitles: List<TextView>
    private lateinit var adapter: ImageAdapter
    private var isSwitch: Boolean = false
    private var listMovie: List<Movie> = emptyList()

    private val moviesViewModel: MoviesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerViewLayoutManagers()
        observeViewModel()
        setupSwipeRefresh()
    }

    private fun initRecyclerViewLayoutManagers() {
        textTitles = listOf(
            binding.textPopular,
            binding.textTopRated,
            binding.textNowPlaying,
            binding.textUpComing
        )
        recyclerViews = listOf(
            binding.recyclerViewPopular,
            binding.recyclerViewTopRated,
            binding.recyclerViewNowPlaying,
            binding.recyclerViewUpComing
        )

        gridLayoutManagers = List(recyclerViews.size) {
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        }

        linearLayoutManagers = List(recyclerViews.size) {
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
        recyclerViews.forEachIndexed { index, recyclerView ->
            recyclerView.layoutManager = if (isSwitch) {
                gridLayoutManagers[index]
            } else {
                linearLayoutManagers[index]
            }
        }
    }

    private fun observeViewModel() {
        moviesViewModel.uiStatePopular.observe(viewLifecycleOwner, Observer { uiState ->
            handleUiState(uiState, binding.recyclerViewPopular, binding.icLoading)
        })

        moviesViewModel.uiStateTopRated.observe(viewLifecycleOwner, Observer { uiState ->
            handleUiState(uiState, binding.recyclerViewTopRated, binding.icLoading)
        })

        moviesViewModel.uiStateNowPlaying.observe(viewLifecycleOwner, Observer { uiState ->
            handleUiState(uiState, binding.recyclerViewNowPlaying, binding.icLoading)
        })

        moviesViewModel.uiStateUpComing.observe(viewLifecycleOwner, Observer { uiState ->
            handleUiState(uiState, binding.recyclerViewUpComing, binding.icLoading)
        })
    }

    private fun handleUiState(
        uiState: UiStateAllMovie,
        recyclerView: RecyclerView,
        loadingIndicator: View
    ) {
        when (uiState) {
            is UiStateAllMovie.Loading -> {
                loadingIndicator.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            }

            is UiStateAllMovie.Success -> {
                loadingIndicator.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                setupAdapter(uiState.list, recyclerView)
            }

            is UiStateAllMovie.Error -> {
                loadingIndicator.visibility = View.GONE
                recyclerView.visibility = View.GONE
                // Handle the error
                // Toast.makeText(context, uiState.message, Toast.LENGTH_SHORT).show()
            }
        }

        val titleObservers = listOf(
            moviesViewModel.textPopular to textTitles[0],
            moviesViewModel.textTopRated to textTitles[1],
            moviesViewModel.textNowPlaying to textTitles[2],
            moviesViewModel.textUpComing to textTitles[3]
        )
        titleObservers.forEach { (titleLiveData, title) ->
            titleLiveData.observe(viewLifecycleOwner, Observer { isVisible ->
                title.visibility = if (isVisible) View.VISIBLE else View.GONE
            })
        }
    }

    private fun setupAdapter(movies: List<Movie>, recyclerView: RecyclerView) {
        val limitedMovies = movies.take(4)
        val imageUrlList = limitedMovies.map { it.posterPath }
        val idList = limitedMovies.map { it.id }
        val nameMovieList = limitedMovies.map { it.title }
        val overView = limitedMovies.map { it.overview }
        val voteCountList = limitedMovies.map { it.voteCount.toString() }

        adapter = ImageAdapter(
            imageUrlList,
            idList,
            nameMovieList,
            voteCountList,
            overView,
            isSwitch,
            requireContext()
        ) { id ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(id)
            findNavController().navigate(action)
        }
        recyclerView.adapter = adapter
    }

    fun setSwitch(isChecked: Boolean) {
        isSwitch = isChecked
        if (isAdded) {
            moviesViewModel.refreshMovies()
            observeViewModel()
            initRecyclerViewLayoutManagers()
//            recyclerViews.forEach { recyclerView ->
//                setupAdapter(listMovie, recyclerView)
//            }
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            moviesViewModel.refreshMovies()
            observeViewModel()
            Handler(Looper.getMainLooper()).postDelayed({
                binding.swipeRefreshLayout.isRefreshing = false
            }, 1000)
        }
    }
}
