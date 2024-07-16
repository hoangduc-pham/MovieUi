@file:Suppress("DEPRECATION")

package com.mock_project.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jicsoftwarestudio.movie_ass.R
import com.mock_project.ui.GenresFragment
import com.mock_project.ui.HomeFragment
import com.mock_project.ui.SearchFragment

fun FragmentActivity.initNavigationBottom(bottomNavigation: BottomNavigationView) {
    bottomNavigation.setOnNavigationItemSelectedListener { item ->
        var selectedFragment: Fragment? = null
        when (item.itemId) {
            R.id.nav_home -> selectedFragment = HomeFragment()
            R.id.nav_search -> selectedFragment = SearchFragment()
            R.id.nav_genres -> selectedFragment = GenresFragment()
        }
        if (selectedFragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, selectedFragment)
                .commit()
        }
        true
    }
}
