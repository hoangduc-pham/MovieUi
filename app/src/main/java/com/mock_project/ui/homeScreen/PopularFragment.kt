package com.mock_project.ui.homeScreen

import PopularViewModel
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
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


class PopularFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var adapter: ImageAdapter
    private lateinit var listMovie: List<Movie>
    private var isSwitch: Boolean = false
    private lateinit var progressBar: ProgressBar
    private val popularViewModel: PopularViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_popular, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)
        observeViewModel()
        setupSwipeRefresh()
    }

    private fun initRecyclerView(view: View) {
        swipeRefreshLayout  = view.findViewById(R.id.swipeRefreshLayout)
        recyclerView = view.findViewById(R.id.recyclerViewPopular)
        progressBar = view.findViewById(R.id.progressBar)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = CarauselLayout(requireContext(), RecyclerView.VERTICAL, false)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    if (popularViewModel.isLoadMore.value == false) {
                        popularViewModel.loadMoreMovies()
                    }
                }
            }})
    }

    private fun observeViewModel() {
        popularViewModel.popularMovies.observe(viewLifecycleOwner, Observer { popularMovies ->
//            listMovie = popularMovies ?: emptyList()
            listMovie = popularMovies.toMutableList()
            setupAdapter(popularMovies)
        })
        popularViewModel.isLoadMore.observe(viewLifecycleOwner, Observer { isLoadMore ->
            if (isLoadMore) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
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
            adapter = ImageAdapter(imageUrlList, idList, nameMovieList, voteCountList, overView, isSwitch, requireContext()) { id ->
                val intent = Intent(requireContext(), DetailScreen::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
            }
            recyclerView.adapter = adapter
        }
    }
    private fun setupAdapter2(movieList: List<Movie>) {
        val imageUrlList = movieList.map { it.posterPath }
        val idList = movieList.map { it.id }
        val nameMovieList = movieList.map { it.title }
        val overView = movieList.map { it.overview }
        val voteCountList = movieList.map { it.voteCount.toString() }
        adapter = ImageAdapter(imageUrlList, idList, nameMovieList, voteCountList, overView, isSwitch, requireContext()) { id ->
                val intent = Intent(requireContext(), DetailScreen::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
            }
            recyclerView.adapter = adapter
        }

    private fun updateRecyclerViewLayoutManagers() {
        recyclerView.layoutManager = if (isSwitch) {
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        } else {
            CarauselLayout(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

    fun setSwitch(isChecked: Boolean) {
        isSwitch = isChecked
        if (::listMovie.isInitialized && isAdded) {
            setupAdapter2(listMovie)
            updateRecyclerViewLayoutManagers()
        }
    }
    private fun setupSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener {
            popularViewModel.fetchPopularMovies(1)
            observeViewModel()
            Handler(Looper.getMainLooper()).postDelayed({
                swipeRefreshLayout.isRefreshing = false
            }, 500)
        }
    }
}