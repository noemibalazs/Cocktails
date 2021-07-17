package com.noemi.android.cocktails

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import com.nhaarman.mockito_kotlin.mock
import com.noemi.android.cocktails.room.CocktailDAO
import com.noemi.android.cocktails.room.CocktailDB
import com.noemi.android.cocktails.room.CocktailEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(RobolectricTestRunner::class)
class CocktailLocalRepositoryUnitTest {

    private lateinit var cocktailDB: CocktailDB
    private lateinit var cocktailDAO: CocktailDAO
    private val context: Context = mock()
    private val cocktailEntity: CocktailEntity = mock()

    @get:Rule
    var instantTask = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        cocktailDB = Room.inMemoryDatabaseBuilder(context, CocktailDB::class.java).build()
        cocktailDAO = cocktailDB.cocktailDAO()
    }

    @Test
    @Throws(Exception::class)
    fun testInsertCocktailShouldPass() {
        CoroutineScope(Dispatchers.IO).launch {
            val id = cocktailDAO.addCocktail(cocktailEntity)
            assertNotNull(id)
        }
    }


    @Test
    @Throws(Exception::class)
    fun testCocktailsSizeShouldPass() {
        CoroutineScope(Dispatchers.IO).launch {
            cocktailDAO.addCocktail(cocktailEntity)
            val size = cocktailDAO.getCocktails().size
            assertEquals(size, 1)
        }
    }

    @Test
    @Throws(Exception::class)
    fun testCocktailsSizeShouldFailed() {
        CoroutineScope(Dispatchers.IO).launch {
            cocktailDAO.addCocktail(cocktailEntity)
            val size = cocktailDAO.getCocktails().size
            assertEquals(size, 3)
        }
    }

    @Test
    @Throws(Exception::class)
    fun testCocktailByIdShouldPass() {
        val cocktail = CocktailEntity(
            id = 12,
            name = "Gin Tonic",
            instructions = "Enjoy it!",
            ingredient1 = "lime",
            ingredient2 = "ice",
            ingredient3 = "vodka",
            category = "ordinary drink",
            icon = "https://pixabay.com/hu/photos/gin-tonic-kokt%c3%a9lok-ny%c3%a1ri-ital-gin-4468653/"
        )
        CoroutineScope(Dispatchers.IO).launch {
            cocktailDAO.addCocktail(cocktail)
            val id = cocktailDAO.getCocktails()[0].id
            assertEquals(id, cocktail.id)
        }
    }

    @Test
    @Throws(Exception::class)
    fun testCocktailByIdShouldFailed() {
        val cocktail = CocktailEntity(
            id = 12,
            name = "Gin Tonic",
            instructions = "Enjoy it!",
            ingredient1 = "lime",
            ingredient2 = "ice",
            ingredient3 = "vodka",
            category = "ordinary drink",
            icon = "https://pixabay.com/hu/photos/gin-tonic-kokt%c3%a9lok-ny%c3%a1ri-ital-gin-4468653/"
        )
        CoroutineScope(Dispatchers.IO).launch {
            cocktailDAO.addCocktail(cocktail)
            val id = cocktailDAO.getCocktails()[0].id
            assertNotEquals(id - 1, cocktail.id)
        }
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        cocktailDB.close()
    }
}