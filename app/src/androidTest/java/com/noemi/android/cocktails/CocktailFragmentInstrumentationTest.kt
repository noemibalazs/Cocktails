package com.noemi.android.cocktails

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.noemi.android.cocktails.adapter.*
import com.noemi.android.cocktails.api.model.Cocktail
import com.noemi.android.cocktails.landing.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class CocktailFragmentInstrumentationTest {

    @get:Rule
    val rule = activityScenarioRule<MainActivity>()

    @Test
    fun testBusinessLogic() {
        val cocktail1 = Cocktail(
            id = 12,
            name = "Gin Tonic",
            instruction = "Enjoy it!",
            ingredient1 = "lime",
            ingredient2 = "ice",
            ingredient3 = "vodka",
            category = "ordinary drink",
            icon = "https://pixabay.com/hu/photos/gin-tonic-kokt%c3%a9lok-ny%c3%a1ri-ital-gin-4468653/"
        )

        val cocktail2 = Cocktail(
            id = 12,
            name = "Margarita",
            instruction = "Enjoy it!",
            ingredient1 = "tequila",
            ingredient2 = "lime",
            ingredient3 = "curacao",
            category = "ordinary drink",
            icon = "https://pixabay.com/hu/photos/margarita-kokt%c3%a9l-hideg-ital-b%c3%a1r-1839361/"
        )

        val cocktails = mutableListOf(cocktail1, cocktail2)

        val cocktailListener: CocktailListener = {}

        val cocktailLaunchListener: CocktailLaunchListener = {}

        val cocktailsAdapter = CocktailsAdapter(cocktailListener, cocktailLaunchListener)
        cocktailsAdapter.submitList(cocktails)

        rule.scenario.onActivity {
            it.findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId =
                R.id.cocktails
            it.findViewById<RecyclerView>(R.id.rvCocktails).adapter = cocktailsAdapter

        }

        onView(withId(R.id.searchForCocktails)).check(matches(isDisplayed()))
        onView(withId(R.id.searchDone)).check(matches(isDisplayed()))

        onView(ViewMatchers.isRoot()).perform(EspressoHelper.waitFor(2100L))

        onView(withId(R.id.rvCocktails)).check(matches(isDisplayed())).perform(
            actionOnItemAtPosition<CocktailVH>(
                0,
                click()
            )
        )
    }
}