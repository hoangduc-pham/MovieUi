package com.hoangpd15.smartmovie.ui.homeScreen.typeMovieScreen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entities.Movie
import com.hoangpd15.smartmovie.adapter.ImageAdapter
import com.hoangpd15.smartmovie.databinding.FragmentUpComingBinding
import com.hoangpd15.smartmovie.ui.UiState
import com.hoangpd15.smartmovie.ui.homeScreen.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TypeFragment(type: String) : Fragment() {
    private var _binding: FragmentUpComingBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ImageAdapter
    private var listMovie: List<Movie> = emptyList()
    private var typeMovie: String = type
    private var isSwitch: Boolean = false

    private val popularViewModel: PopularViewModel by viewModels()
    private val topRatedViewModel: TopRatedViewModel by viewModels()
    private val nowPlayingViewModel: NowPlayingViewModel by viewModels()
    private val upComingViewModel: UpComingViewModel by viewModels()

    private val typeViewModel by lazy {
        when (typeMovie) {
            "popular" -> popularViewModel
            "topRate" -> topRatedViewModel
            "nowPlaying" -> nowPlayingViewModel
            "upComing" -> upComingViewModel
            else -> popularViewModel
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpComingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        typeViewModel.fetchMovies(1)
        initRecyclerView()
        observeViewModel()
        setupSwipeRefresh()
    }

    private fun initRecyclerView() {
        binding.recyclerUpComing.layoutManager = if (isSwitch) {
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        } else {
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
        binding.recyclerUpComing.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1) && typeViewModel.uiState.value !is UiState.LoadMore) {
                    typeViewModel.loadMoreMovies()
                }
            }
        })
    }

    private fun observeViewModel() {
        typeViewModel.uiState.observe(viewLifecycleOwner, Observer { uiState ->
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
                    val movies = uiState.list as? List<Movie> ?: emptyList()
                    setupAdapter(movies)
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
                { id -> typeViewModel.deleteFavoriteMovie(id) },
                { movie -> typeViewModel.insertFavoriteMovie(movie) },
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
            binding.recyclerUpComing.adapter = adapter
        }
    }

    private fun setupAdapterSwitch(movieList: List<Movie>) {
        val imageUrlList = movieList.map { it.posterPath }
        val idList = movieList.map { it.id }
        val nameMovieList = movieList.map { it.title }
        val overView = movieList.map { it.overview }
        val voteCountList = movieList.map { it.voteCount.toString() }
        adapter = ImageAdapter(
            { id -> typeViewModel.deleteFavoriteMovie(id) },
            { movie -> typeViewModel.insertFavoriteMovie(movie) },
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
        binding.recyclerUpComing.adapter = adapter
    }

    private fun updateRecyclerViewLayoutManagers() {
        binding.recyclerUpComing.layoutManager = if (isSwitch) {
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
