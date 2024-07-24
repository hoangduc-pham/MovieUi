package com.hoangpd15.smartmovie.ui.homeScreen.allMovieScreen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hoangpd15.smartmovie.adapter.ImageAdapter
import com.hoangpd15.smartmovie.databinding.FragmentNowPlayingBinding
import com.hoangpd15.smartmovie.model.Movie
import com.hoangpd15.smartmovie.ui.UiState
import com.hoangpd15.smartmovie.ui.homeScreen.HomeFragmentDirections

class NowPlayingFragment : Fragment() {
    private var _binding: FragmentNowPlayingBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ImageAdapter
    private var listMovie: List<Movie> = emptyList()
    private var isSwitch: Boolean = false
    private val nowPlayingViewModel: NowPlayingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNowPlayingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nowPlayingViewModel.fetchMovies(1)
        initRecyclerView()
        observeViewModel()
        setupSwipeRefresh()
    }

    private fun initRecyclerView() {
        binding.recyclerNowPlaying.layoutManager = if (isSwitch) {
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        } else {
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
        binding.recyclerNowPlaying.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1) && nowPlayingViewModel.uiState.value !is UiState.LoadMore) {
                    nowPlayingViewModel.loadMoreMovies()
                }
            }
        })
    }

    private fun observeViewModel() {
//        lifecycleScope.launch {                    if use stateflow
//            nowPlayingViewModel.uiState.collect { uiState ->
        nowPlayingViewModel.uiState.observe(viewLifecycleOwner, Observer { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    binding.icLoading.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }

                is UiState.LoadMore -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is UiState.Success<*> -> {
                    binding.icLoading.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                    val movies = uiState.list as List<Movie>
                    setupAdapter(movies)
//                    listMovie = uiState.movies
//                    setupAdapter(uiState.movies)
                }

                is UiState.Error -> {
                    binding.icLoading.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }

    private fun setupAdapter(movieList: List<Movie>) {
        val imageUrlList = movieList.map { it.posterPath }
        val idList = movieList.map { it.id }
        val nameMovieList = movieList.map { it.title }
        val overView = movieList.map { it.overview }
        val voteCountList = movieList.map { it.voteCount.toString() }
        if (::adapter.isInitialized) {
            adapter.updateData(imageUrlList, idList, nameMovieList, voteCountList, overView)
        } else {
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
            binding.recyclerNowPlaying.adapter = adapter
        }
    }

    private fun setupAdapterSwitch(movieList: List<Movie>) {
        val imageUrlList = movieList.map { it.posterPath }
        val idList = movieList.map { it.id }
        val nameMovieList = movieList.map { it.title }
        val overView = movieList.map { it.overview }
        val voteCountList = movieList.map { it.voteCount.toString() }
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
        binding.recyclerNowPlaying.adapter = adapter
    }

    private fun updateRecyclerViewLayoutManagers() {
        binding.recyclerNowPlaying.layoutManager = if (isSwitch) {
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        } else {
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

    fun setSwitch(isChecked: Boolean) {
        isSwitch = isChecked
        if (isAdded) {
            setupAdapterSwitch(listMovie)
            observeViewModel()
            updateRecyclerViewLayoutManagers()
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            setupAdapterSwitch(listMovie)
            observeViewModel()
            Handler(Looper.getMainLooper()).postDelayed({
                binding.swipeRefreshLayout.isRefreshing = false
            }, 500)
        }
    }
}
