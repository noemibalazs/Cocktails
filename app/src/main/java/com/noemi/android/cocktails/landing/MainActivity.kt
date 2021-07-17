package com.noemi.android.cocktails.landing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.noemi.android.cocktails.cocktails.CocktailsFragment
import com.noemi.android.cocktails.favorite.FavoriteFragment
import com.noemi.android.cocktails.R
import com.noemi.android.cocktails.app.App
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.component.inject(this)
        setContentView(R.layout.activity_main)

        setUpNavigation()
    }

    private fun setUpNavigation() {

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.cocktails -> {
                    setCurrentFragment(CocktailsFragment.newInstance())
                    true
                }

                R.id.favorite -> {
                    setCurrentFragment(FavoriteFragment.newInstance())
                    true
                }
                else -> false
            }
        }

        bottom_navigation.selectedItemId = R.id.cocktails
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit()
    }
}