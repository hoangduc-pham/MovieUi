package com.hoangpd15.smartmovie.ui.homeScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hoangpd15.smartmovie.R
import com.hoangpd15.smartmovie.adapter.ViewPagerAdapter
import com.hoangpd15.smartmovie.databinding.FragmentHomeBinding
import com.hoangpd15.smartmovie.databinding.FragmentMoviesBinding
import com.hoangpd15.smartmovie.databinding.FragmentUpComingBinding
import com.hoangpd15.smartmovie.ui.homeScreen.movieScreen.MoviesFragment
import com.hoangpd15.smartmovie.ui.homeScreen.nowPlayingScreen.NowPlayingFragment
import com.hoangpd15.smartmovie.ui.homeScreen.popularScreen.PopularFragment
import com.hoangpd15.smartmovie.ui.homeScreen.topRatedScreen.TopRatedFragment
import com.hoangpd15.smartmovie.ui.homeScreen.upComingScreen.UpComingFragment

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val movieFragment = MoviesFragment()
    private val popularFragment = PopularFragment()
    private val topRatedFragment = TopRatedFragment()
    private val nowPlayingFragment = NowPlayingFragment()
    private val upComingFragment = UpComingFragment()
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
