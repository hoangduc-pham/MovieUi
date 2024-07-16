package com.mock_project.ui.homeScreen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jicsoftwarestudio.movie_ass.R
import com.mock_project.adapter.ImageAdapter
import com.mock_project.model.Movie
import com.mock_project.ui.CarauselLayout
import com.mock_project.ui.DetailScreen
import com.mock_project.viewModel.homeViewModel.NowPlayingViewModel

class NowPlayingFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private var isSwitch: Boolean = false
    private lateinit var listMovie: List<Movie>

    private val nowPlayingViewModel: NowPlayingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_now_playing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)
        observeViewModel()
    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recyclerNowPlaying)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL,false)
        recyclerView.layoutManager = CarauselLayout(requireContext(), RecyclerView.VERTICAL, false)
        updateRecyclerViewLayoutManagers()
    }

    private fun observeViewModel() {
        nowPlayingViewModel.nowPlayingMovies.observe(viewLifecycleOwner, Observer { nowPlayingMovies ->
            listMovie = nowPlayingMovies ?: emptyList()
            setupAdapter(nowPlayingMovies)
        })
    }

    private fun setupAdapter(movieList: List<Movie>) {
        val imageUrlList = movieList.map { it.posterPath }
        val idList = movieList.map {it.id}
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
}
