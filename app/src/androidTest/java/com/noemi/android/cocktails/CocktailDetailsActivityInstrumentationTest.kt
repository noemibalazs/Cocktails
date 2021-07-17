package com.noemi.android.cocktails

import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.noemi.android.cocktails.api.model.Cocktail
import com.noemi.android.cocktails.details.CocktailDetailsActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.bumptech.glide.Glide

@RunWith(AndroidJUnit4ClassRunner::class)
class CocktailDetailsActivityInstrumentationTest {

    @get:Rule
    val rule = activityScenarioRule<CocktailDetailsActivity>()

    @Test
    fun testBusinessLogic() {
        val cocktail = Cocktail(
            id = 12,
            name = "Gin Tonic",
            instruction = "Chill out",
            ingredient1 = "lime",
            ingredient2 = "ice",
            ingredient3 = "vodka",
            category = "ordinary drink",
            icon = "https://pixabay.com/hu/photos/gin-gin-tonic-kokt%c3%a9l-csapos-4551294/"
        )

        rule.scenario.onActivity {

            Glide.with(it).load(cocktail.icon).into(it.findViewById<AppCompatImageView>(R.id.ivCocktailDetails))

            it.findViewById<AppCompatTextView>(R.id.tvCocktailName).text = cocktail.name
            it.findViewById<AppCompatTextView>(R.id.tvCocktailDescription).text =
                cocktail.instruction

            it.findViewById<AppCompatTextView>(R.id.tvCocktailCategory).text =
                String.format(it.getString(R.string.txt_category), cocktail.category)

            it.findViewById<AppCompatTextView>(R.id.tvCocktailIngredients).text = String.format(
                it.getString(R.string.txt_ingredients),
                cocktail.ingredient1,
                cocktail.ingredient2,
                cocktail.ingredient3
            )
        }

        onView(isRoot()).perform(EspressoHelper.waitFor(2100L))

        onView(withId(R.id.ivCocktailDetails)).check(matches(isDisplayed()))
        onView(withId(R.id.tvCocktailName)).check(matches(isDisplayed()))
            .check(matches(withText(cocktail.name)))
        onView(withId(R.id.tvCocktailCategory)).check(matches(isDisplayed()))
        onView(withId(R.id.tvCocktailIngredients)).check(matches(isDisplayed()))
        onView(withId(R.id.tvCocktailDescription)).check(matches(isDisplayed())).check(
            matches(
                withText(cocktail.instruction)
            )
        )
    }
}