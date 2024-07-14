package com.jicsoftwarestudio.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.jicsoftwarestudio.adapter.ViewPagerAdapter
import com.jicsoftwarestudio.movie_ass.R
import com.jicsoftwarestudio.ui.homeScreen.*

class HomeFragment : Fragment() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private val fragments: List<Fragment> = listOf(
        MoviesFragment(),
        PopularFragment(),
        TopRatedFragment(),
        NowPlayingFragment(),
        UpComingFragment(),
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPagerAndTabs(view)
    }

    private fun initViewPagerAndTabs(view: View) {
        tabLayout = view.findViewById(R.id.tabLayout)
        viewPager = view.findViewById(R.id.viewPager)

        val titles = listOf(
            "Movies",
            "Popular",
            "Top Rated",
            "Now Playing",
            "Upcoming"
        )

        viewPager.adapter = ViewPagerAdapter(this, fragments)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}
