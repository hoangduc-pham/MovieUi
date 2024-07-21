@file:Suppress("DEPRECATION")

package com.hoangpd15.smartmovie.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hoangpd15.smartmovie.R
import com.hoangpd15.smartmovie.ui.GenresFragment
import com.hoangpd15.smartmovie.ui.HomeFragment
import com.hoangpd15.smartmovie.ui.SearchFragment

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
                .replace(R.id.nav_host_fragment, selectedFragment)
                .commit()
        }
        true
    }
}
