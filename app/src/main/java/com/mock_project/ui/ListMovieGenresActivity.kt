package com.mock_project.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.jicsoftwarestudio.movie_ass.R
import com.mock_project.adapter.ImageAdapterSearch
import com.mock_project.model.Movie
import com.mock_project.viewModel.MoviesByGenreViewModel

class ListMovieGenresActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ImageAdapterSearch
    private lateinit var icLoading: ProgressBar
    private lateinit var icLoadError: TextView
    private lateinit var topAppBar: MaterialToolbar
    private val movieByGenresViewModel: MoviesByGenreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_movie_genres)
            initRecyclerView()
            observeViewModel()
    }
    private fun initRecyclerView() {
        icLoading = findViewById(R.id.icLoading)
        topAppBar = findViewById(R.id.topAppBar)
        icLoading.visibility = View.VISIBLE

        val genreId = intent.getIntExtra("GENRE_ID", -1)
        movieByGenresViewModel.fetchMoviesByGenre(genreId)

        val genreName = intent.getStringExtra("GENRE_NAME") ?: "Movies"
        topAppBar.title = genreName
        setSupportActionBar(topAppBar)

        recyclerView = findViewById(R.id.recyclerViewGenresMovie)
        recyclerView.layoutManager = CarauselLayout(this, RecyclerView.VERTICAL, false)
    }
    private fun observeViewModel() {
        movieByGenresViewModel.moviesByGenre.observe(this, Observer { movies  ->
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
            id -> val intent = Intent(this, DetailScreen::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
    }
}