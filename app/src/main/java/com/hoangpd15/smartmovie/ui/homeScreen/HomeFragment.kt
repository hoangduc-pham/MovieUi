package com.hoangpd15.smartmovie.ui.homeScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.hoangpd15.smartmovie.adapter.ViewPagerAdapter
import com.hoangpd15.smartmovie.databinding.FragmentHomeBinding
import com.hoangpd15.smartmovie.ui.homeScreen.allMovieScreen.NowPlayingFragment
import com.hoangpd15.smartmovie.ui.homeScreen.allMovieScreen.PopularFragment
import com.hoangpd15.smartmovie.ui.homeScreen.allMovieScreen.TopRatedFragment
import com.hoangpd15.smartmovie.ui.homeScreen.allMovieScreen.TypeFragment
import com.hoangpd15.smartmovie.ui.homeScreen.movieScreen.MoviesFragment

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val movieFragment = MoviesFragment()
    private val popularFragment = TypeFragment("popular")
    private val topRatedFragment = TypeFragment("topRate")
    private val nowPlayingFragment = TypeFragment("nowPlaying")
    private val upComingFragment = TypeFragment("upComing")
//    private val topRatedFragment = TopRatedFragment()
//    private val nowPlayingFragment = NowPlayingFragment()
//    private val upComingFragment = UpComingFragment()
    private val fragments: List<Fragment> = listOf(
        movieFragment,
        popularFragment,
        topRatedFragment,
        nowPlayingFragment,
        upComingFragment
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPagerAndTabs(view)
        initSwitch(view)
    }

    private fun initViewPagerAndTabs(view: View) {
        val titles = listOf(
            "Movies",
            "Popular",
            "Top Rated",
            "Now Playing",
            "Up Coming"
        )

        binding.viewPager.adapter = ViewPagerAdapter(this, fragments)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }
    private fun initSwitch(view: View) {
        binding.switchView.setOnCheckedChangeListener { _, isSwitch ->
               movieFragment.setSwitch(isSwitch)
               popularFragment.setSwitch(isSwitch)
               nowPlayingFragment.setSwitch(isSwitch)
               topRatedFragment.setSwitch(isSwitch)
               upComingFragment.setSwitch(isSwitch)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
}
