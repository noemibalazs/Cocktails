package com.noemi.android.cocktails

import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.noemi.android.cocktails.api.localDataSource.CocktailLocalService
import com.noemi.android.cocktails.api.model.Cocktail
import com.noemi.android.cocktails.api.model.CocktailResult
import com.noemi.android.cocktails.api.remoteDataSource.CocktailRemoteService
import com.noemi.android.cocktails.viewModel.CocktailViewModel
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class CocktailViewModelUnitTest {

    private lateinit var cocktailViewModel: CocktailViewModel
    private lateinit var failure: Throwable
    private val cocktailRemoteService: CocktailRemoteService = mock()
    private val cocktailLocalService: CocktailLocalService = mock()
    private val progressObserver: Observer<Boolean> = mock()
    private val errorObserver: Observer<String> = mock()
    private val defaultCocktailObserver: Observer<MutableList<Cocktail>> = mock()
    private val searchedCocktailObserver: Observer<MutableList<Cocktail>> = mock()
    private val cocktailObserver: Observer<Cocktail> = mock()

    @Before
    fun setUp() {
        cocktailViewModel = CocktailViewModel(cocktailRemoteService, cocktailLocalService)
        failure = Throwable("An error has occurred, try it again!")
    }

    @Test(expected = NullPointerException::class)
    fun testLoadDefaultCocktails_Success() {
        val result = mock<CocktailResult>()
        whenever(cocktailRemoteService.getCocktails()).thenReturn(Single.just(result))
        cocktailViewModel.loadDefaultCocktails()
        cocktailViewModel.defaultCocktails.observeForever(defaultCocktailObserver)

        val cocktails = result.drinks

        verify(progressObserver).onChanged(true)
        verify(defaultCocktailObserver).onChanged(cocktails)
        verify(progressObserver).onChanged(false)
    }

    @Test(expected = NullPointerException::class)
    fun testLoadDefaultCocktails_Error() {
        whenever(cocktailRemoteService.getCocktails()).thenReturn(Single.error(failure))
        cocktailViewModel.loadDefaultCocktails()
        cocktailViewModel.defaultCocktails.observeForever(defaultCocktailObserver)

        val captor = argumentCaptor<String>()

        verify(progressObserver).onChanged(true)
        verify(defaultCocktailObserver).onChanged(null)
        verify(errorObserver).onChanged(captor.capture())
        verify(progressObserver).onChanged(false)
    }

    @Test(expected = NullPointerException::class)
    fun testLoadSearchedCocktails_Success() {
        val result = mock<CocktailResult>()
        val name = "gin"
        whenever(cocktailRemoteService.getSearchedCocktails(name)).thenReturn(Single.just(result))
        cocktailViewModel.loadSearchedCocktails(name)
        cocktailViewModel.searchedCocktails.observeForever(searchedCocktailObserver)

        val cocktails = result.drinks

        verify(progressObserver).onChanged(true)
        verify(defaultCocktailObserver).onChanged(cocktails)
        verify(progressObserver).onChanged(false)
    }

    @Test(expected = NullPointerException::class)
    fun testLoadSearchedCocktails_Error() {
        val name = "gin"
        whenever(cocktailRemoteService.getSearchedCocktails(name)).thenReturn(Single.error(failure))
        cocktailViewModel.loadSearchedCocktails(name)
        cocktailViewModel.searchedCocktails.observeForever(searchedCocktailObserver)

        val captor = argumentCaptor<String>()

        verify(progressObserver).onChanged(true)
        verify(defaultCocktailObserver).onChanged(null)
        verify(progressObserver).onChanged(false)
        verify(errorObserver).onChanged(captor.capture())
    }

    @Test(expected = NullPointerException::class)
    fun testLoadCocktailDetails_Success() {
        val result = mock<CocktailResult>()
        whenever(cocktailRemoteService.getCocktailDetails(12)).thenReturn(Single.just(result))
        cocktailViewModel.getCocktail(12)
        cocktailViewModel.cocktailObserver.observeForever(cocktailObserver)

        verify(progressObserver).onChanged(true)
        verify(cocktailObserver).onChanged(result.drinks[0])
        verify(progressObserver).onChanged(false)
    }

    @Test(expected = NullPointerException::class)
    fun testLoadCocktailDetails_Error() {
        whenever(cocktailRemoteService.getCocktailDetails(12)).thenReturn(Single.error(failure))
        cocktailViewModel.getCocktail(12)
        cocktailViewModel.cocktailObserver.observeForever(cocktailObserver)

        val captor = argumentCaptor<String>()

        verify(progressObserver).onChanged(true)
        verify(cocktailObserver).onChanged(null)
        verify(progressObserver).onChanged(false)
        verify(errorObserver).onChanged(captor.capture())
    }
}