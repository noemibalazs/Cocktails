package com.noemi.android.cocktails

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.noemi.android.cocktails.api.dataSource.CocktailAPI
import com.noemi.android.cocktails.api.model.CocktailResult
import com.noemi.android.cocktails.api.remoteDataSource.CocktailRemoteRepository
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import java.lang.NullPointerException

class CocktailRemoteRepositoryUnitTest {

    private lateinit var failure: Throwable
    private lateinit var cocktailRemoteRepository: CocktailRemoteRepository
    private val cocktailAPI: CocktailAPI = mock()

    @Before
    fun setUp() {
        cocktailRemoteRepository = CocktailRemoteRepository(cocktailAPI)
        failure = Throwable("Try it again, an error has occurred.")
    }

    @Test
    fun testGetCocktails_Complete() {
        val response = mock<CocktailResult>()
        whenever(cocktailRemoteRepository.getCocktails()).thenReturn(Single.just(response))
        cocktailRemoteRepository.getCocktails().test().assertComplete()
        verify(cocktailAPI).getCocktails()
    }

    @Test
    fun testGetCocktails_Error() {
        whenever(cocktailRemoteRepository.getCocktails()).thenReturn(Single.error(failure))
        cocktailRemoteRepository.getCocktails().test().assertError(failure)
        verify(cocktailAPI).getCocktails()
    }

    @Test(expected = NullPointerException::class)
    fun testGetSearchedCocktails_Complete(){
        val response = mock<CocktailResult>()
        val name = "gin"
        whenever(cocktailRemoteRepository.getSearchedCocktails(name)).thenReturn(Single.just(response))
        cocktailRemoteRepository.getCocktails().test().assertComplete()
        verify(cocktailAPI).getSearchedCocktails(name)
    }

    @Test(expected = NullPointerException::class)
    fun testGetSearchedCocktails_Error(){
        val name = "gin"
        whenever(cocktailRemoteRepository.getSearchedCocktails(name)).thenReturn(Single.error(failure))
        cocktailRemoteRepository.getCocktails().test().assertError(failure)
        verify(cocktailAPI).getSearchedCocktails(name)
    }

    @Test
    fun testGetCocktailDetails_Complete(){
        val response = mock<CocktailResult>()
        whenever(cocktailRemoteRepository.getCocktailDetails(12)).thenReturn(Single.just(response))
        cocktailRemoteRepository.getCocktailDetails(12).test().assertComplete()
        verify(cocktailAPI).getCocktailDetails(12)
    }

    @Test
    fun testGetCocktailDetails_Error(){
        whenever(cocktailRemoteRepository.getCocktailDetails(12)).thenReturn(Single.error(failure))
        cocktailRemoteRepository.getCocktailDetails(12).test().assertError(failure)
        verify(cocktailAPI).getCocktailDetails(12)
    }
}