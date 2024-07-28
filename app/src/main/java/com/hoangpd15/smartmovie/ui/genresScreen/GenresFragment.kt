package com.hoangpd15.smartmovie.ui.genresScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entities.Genres
import com.hoangpd15.smartmovie.adapter.ListGenresAdapter
import com.hoangpd15.smartmovie.databinding.FragmentGenresBinding
import com.hoangpd15.smartmovie.ui.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenresFragment : Fragment() {
    private lateinit var adapter: ListGenresAdapter
    private var _binding: FragmentGenresBinding? = null
    private val binding get() = _binding!!

    private val listGenresViewModel: GenresViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGenresBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeViewModel()
    }
    private fun initRecyclerView() {
        binding.recyclerListGenres.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeViewModel() {
        listGenresViewModel.uiState.observe(viewLifecycleOwner, Observer { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    binding.icLoading.visibility = View.VISIBLE
                }

                is UiState.Success<*> -> {
                    binding.icLoading.visibility = View.GONE
                    setupAdapter(uiState.list as List<Genres>)
                }

                is UiState.Error -> {
                    binding.icLoading.visibility = View.GONE
                }
                is UiState.LoadMore -> {
                    TODO()
                }
            }
        })
    }
    private fun setupAdapter(listGenres: List<Genres>) {
        val nameGenres = listGenres.map { it.name }
        val idGenres = listGenres.map { it.id }
        adapter = ListGenresAdapter(nameGenres, idGenres) { (idGenre, nameGenres) ->
            val action = GenresFragmentDirections.actionNavGenresToListMovieGenresFragment(idGenre, nameGenres)
            findNavController().navigate(action)
        }
        binding.recyclerListGenres.adapter = adapter
    }
}