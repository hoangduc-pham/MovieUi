package com.hoangpd15.smartmovie.ui.homeScreen

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hoangpd15.smartmovie.R
import com.hoangpd15.smartmovie.adapter.ImageAdapter
import com.hoangpd15.smartmovie.model.Movie
import com.hoangpd15.smartmovie.ui.CarauselLayout
import com.hoangpd15.smartmovie.ui.HomeFragmentDirections
import com.hoangpd15.smartmovie.ui.detailScreen.DetailFragment
import com.hoangpd15.smartmovie.viewModel.homeViewModel.UpComingViewModel

class UpComingFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var adapter: ImageAdapter
    private lateinit var listMovie: List<Movie>
    private var isSwitch: Boolean = false
    private lateinit var progressBar: ProgressBar
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
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        recyclerView = view.findViewById(R.id.recyclerUpComing)
        progressBar = view.findViewById(R.id.progressBar)
        recyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = CarauselLayout(requireContext(), RecyclerView.VERTICAL, false)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1) && upComingViewModel.isLoadMore.value == false) {
                    upComingViewModel.loadMoreMovies()
                }
            }
        })
    }

    private fun observeViewModel() {
        upComingViewModel.upComingMovies.observe(viewLifecycleOwner, Observer { upComingMovies ->
            listMovie = upComingMovies ?: emptyList()
            setupAdapter(upComingMovies)
        })
        upComingViewModel.isLoadMore.observe(viewLifecycleOwner, Observer { isLoadMore ->
            progressBar.visibility = if (isLoadMore) View.VISIBLE else View.GONE
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
            recyclerView.adapter = adapter
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
//            val fragment = DetailFragment.newInstance(id)
//            requireActivity().supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, fragment)
//                .addToBackStack(null)
//                .commit()
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
            setupAdapterSwitch(listMovie)
            updateRecyclerViewLayoutManagers()
        }
    }

    private fun setupSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener {
            setupAdapterSwitch(listMovie)
            observeViewModel()
            Handler(Looper.getMainLooper()).postDelayed({
                swipeRefreshLayout.isRefreshing = false
            }, 500)
        }
    }
}
