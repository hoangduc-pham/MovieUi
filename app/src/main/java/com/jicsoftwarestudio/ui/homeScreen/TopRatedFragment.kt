package com.jicsoftwarestudio.ui.homeScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jicsoftwarestudio.adapter.ImageAdapter
import com.jicsoftwarestudio.model.Movie
import com.jicsoftwarestudio.movie_ass.R
import com.jicsoftwarestudio.viewModel.TopRatedViewModel

class TopRatedFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ImageAdapter
    private val topRatedViewModel: TopRatedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_rated, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)
        observeViewModel()
    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recyclerTopRate)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.HORIZONTAL,false)
    }
    private fun observeViewModel() {
        topRatedViewModel.topRatedMovies.observe(viewLifecycleOwner, Observer { topRatedMovies ->
            setupAdapter(topRatedMovies)
        })
    }


    private fun setupAdapter(movieList: List<Movie>) {
        val imageUrlList = movieList.map { it.poster_path }
        val idList = movieList.map { it.id }
        val nameMovieList = movieList.map { it.title }
        val voteCountList = movieList.map { it.vote_count.toString() }

        adapter = ImageAdapter(imageUrlList, idList, nameMovieList, voteCountList, false, requireContext())
        recyclerView.adapter = adapter
    }
}
