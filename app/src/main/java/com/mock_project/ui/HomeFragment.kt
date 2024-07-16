package com.mock_project.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.jicsoftwarestudio.movie_ass.R
import com.mock_project.adapter.ViewPagerAdapter
import com.mock_project.ui.homeScreen.*

class HomeFragment : Fragment() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var switchView: SwitchMaterial
    private val MovieFragment = MoviesFragment()
    private val PopularFragment = PopularFragment()
    private val TopRatedFragment = TopRatedFragment()
    private val NowPlayingFragment = NowPlayingFragment()
    private val UpComingFragment = UpComingFragment()
    private val fragments: List<Fragment> = listOf(
        MovieFragment,
        PopularFragment,
        TopRatedFragment,
        NowPlayingFragment,
        UpComingFragment,
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPagerAndTabs(view)
        initSwitch(view)
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
    private fun initSwitch(view: View) {
        switchView = view.findViewById(R.id.switchView)
        switchView.setOnCheckedChangeListener { _, isSwitch ->
               MovieFragment.setSwitch(isSwitch)
               PopularFragment.setSwitch(isSwitch)
               NowPlayingFragment.setSwitch(isSwitch)
               TopRatedFragment.setSwitch(isSwitch)
               UpComingFragment.setSwitch(isSwitch)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}
