package com.mock_project.ui.homeScreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jicsoftwarestudio.movie_ass.R
import com.mock_project.adapter.ImageAdapter
import com.mock_project.model.Movie
import com.mock_project.ui.CarauselLayout
import com.mock_project.ui.detailScreen.DetailScreen
import com.mock_project.viewModel.homeViewModel.UpComingViewModel

class UpComingFragment: Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var adapter: ImageAdapter
    private lateinit var listMovie: List<Movie>
    private var isSwitch: Boolean = false
    private val upComingViewModel: UpComingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_up_coming, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)
        observeViewModel()
        setupSwipeRefresh()
    }

    private fun initRecyclerView(view: View) {
        swipeRefreshLayout  = view.findViewById(R.id.swipeRefreshLayout)
        recyclerView = view.findViewById(R.id.recyclerUpComing)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL,false)
        recyclerView.layoutManager = CarauselLayout(requireContext(), RecyclerView.VERTICAL, false)
        updateRecyclerViewLayoutManagers()
    }

    private fun observeViewModel() {
        upComingViewModel.upComingMovies.observe(viewLifecycleOwner, Observer { upComingMovies ->
            listMovie = upComingMovies ?: emptyList()
            setupAdapter(upComingMovies)
        })
    }

    private fun setupAdapter(movieList: List<Movie>) {
        val imageUrlList = movieList.map { it.posterPath }
        val idList = movieList.map { it.id } //Để lưu favotite
        val nameMovieList = movieList.map { it.title }
        val overView = movieList.map { it.overview }
        val voteCountList = movieList.map { it.voteCount.toString() }

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
    private fun updateRecyclerViewLayoutManagers() {
        recyclerView.layoutManager = if (isSwitch) {
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL,false)
        } else {
            CarauselLayout(requireContext(), RecyclerView.VERTICAL, false)
        }
    }
    fun setSwitch(isChecked: Boolean) {
        isSwitch = isChecked
        if (::listMovie.isInitialized && isAdded) {
            setupAdapter(listMovie)
            updateRecyclerViewLayoutManagers()
        }
    }
    private fun setupSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener {
            upComingViewModel.fetchUpComingMovies()
            observeViewModel()
            Handler(Looper.getMainLooper()).postDelayed({
                swipeRefreshLayout.isRefreshing = false
            }, 1000)
        }
    }
}