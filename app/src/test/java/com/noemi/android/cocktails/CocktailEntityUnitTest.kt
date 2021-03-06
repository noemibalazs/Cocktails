package com.noemi.android.cocktails

import android.os.Build
import com.noemi.android.cocktails.room.CocktailEntity
import org.junit.Assert
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(RobolectricTestRunner::class)
class CocktailEntityUnitTest {

    private val cocktail = CocktailEntity(
        id = 12,
        name = "Gin Tonic",
        instructions = "Enjoy it!",
        ingredient1 = "lime",
        ingredient2 = "ice",
        ingredient3 = "vodka",
        category = "ordinary drink",
        icon = "https://pixabay.com/hu/photos/gin-tonic-kokt%c3%a9lok-ny%c3%a1ri-ital-gin-4468653/"
    )

    @Test
    fun testShouldPass() {
        val expected = CocktailEntity(
            id = 12,
            name = "Gin Tonic",
            instructions = "Enjoy it!",
            ingredient1 = "lime",
            ingredient2 = "ice",
            ingredient3 = "vodka",
            category = "ordinary drink",
            icon = "https://pixabay.com/hu/photos/gin-tonic-kokt%c3%a9lok-ny%c3%a1ri-ital-gin-4468653/"
        )

        Assert.assertEquals(expected, cocktail)
    }

    @Test
    fun testShouldFailed(){
        val expected = CocktailEntity(
            id = 9,
            name = "Gin Tonic",
            instructions = "Enjoy it!",
            ingredient1 = "lime",
            ingredient2 = "",
            ingredient3 = "vodka",
            category = "ordinary drink",
            icon = "https://pixabay.com/hu/photos/gin-tonic-kokt%c3%a9lok-ny%c3%a1ri-ital-gin-4468653/"
        )

        assertNotEquals(expected, cocktail)
    }
}