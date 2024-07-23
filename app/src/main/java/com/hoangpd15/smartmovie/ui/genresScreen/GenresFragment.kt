package com.hoangpd15.smartmovie.ui.genresScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.hoangpd15.smartmovie.R
import com.hoangpd15.smartmovie.adapter.ListGenresAdapter
import com.hoangpd15.smartmovie.databinding.FragmentGenresBinding
import com.hoangpd15.smartmovie.databinding.FragmentSearchBinding
import com.hoangpd15.smartmovie.model.Genres
import com.hoangpd15.smartmovie.ui.CarauselLayout

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
        initRecyclerView(view)
        observeViewModel()
    }
    private fun initRecyclerView(view: View) {
        binding.recyclerListGenres.layoutManager = CarauselLayout(requireContext(), RecyclerView.VERTICAL, false)
        binding.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeViewModel() {
        listGenresViewModel.listGenres.observe(viewLifecycleOwner, Observer { listGenres ->
            setupAdapter(listGenres)
        })
        listGenresViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.icLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
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